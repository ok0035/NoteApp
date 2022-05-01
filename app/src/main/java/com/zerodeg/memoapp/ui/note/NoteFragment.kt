package com.zerodeg.memoapp.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.zerodeg.memoapp.App
import com.zerodeg.memoapp.databinding.NoteFragmentBinding
import com.zerodeg.memoapp.room.Note
import com.zerodeg.memoapp.room.NoteDatabase
import com.zerodeg.memoapp.ui.BaseFragment
import com.zerodeg.memoapp.ui.main.MainFragment
import com.zerodeg.memoapp.vm.NoteListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class NoteFragment : BaseFragment() {
    //recycler view에서 item click시 여기서 메모 보여주기
    private var _binding: NoteFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        private var instance: NoteFragment? = null
        fun getInstance(): NoteFragment {
            if (instance == null) instance = NoteFragment()
            return instance!!
        }
    }

    private val noteListViewModel: NoteListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NoteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        clearView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        noteListViewModel = ViewModelProvider(this)[NoteListViewModel::class.java]

        clearView()

        // TODO: Use the ViewModel
        noteListViewModel.noteLiveData.observe(viewLifecycleOwner) {
            App.log("NoteViewModel", "Note -> ${it.title} ${it.content}")

            CoroutineScope(Dispatchers.Main).launch {
                binding.title.setText(it.title)
                binding.content.setText(it.content)
                binding.isLock.isChecked = !it.password.isNullOrEmpty()
                noteListViewModel.currentNote = it

                //비밀번호가 있다면 입력해놓음 - 이미 비밀번호를 치고 들어왔기에 다시 칠 필요가 없으므로
                if (binding.title.text.toString().isNotEmpty() &&
                    binding.content.text.toString().isNotEmpty()
                ) {
                    CoroutineScope(Dispatchers.Main).launch {
                        binding.password.setText(noteListViewModel.currentNote!!.password)
                    }
                    App.log("noteListViewModel", "password...")
                }

//            if (it.title == "" && it.content == "" && it.password == null) {
//                //new note
//                if(it == null)
//                    noteListViewModel.insertNote("", "", null)
//                else noteListViewModel.insertNote(it.title, it.content, it.password)
//            }

                if (binding.isLock.isChecked) {
                    binding.password.visibility = View.VISIBLE
                } else binding.password.visibility = View.INVISIBLE
            }

        }

        binding.saveBtn.setOnClickListener {
            if (binding.title.text.toString().isEmpty() &&
                binding.content.text.toString().isEmpty()
            ) {
                replaceNoBackStack(MainFragment.getInstance())
                clearView()
            }

            //DB에 수정내용 저장
            CoroutineScope(Dispatchers.IO).launch {
                val db = NoteDatabase.getInstance(App.applicationContext())!!

                if (noteListViewModel.currentNote != null &&
                    db.noteDao().containsPrimaryKey(noteListViewModel.currentNote!!.id)) {
                    //update

                    db.noteDao().update(
                        noteListViewModel.currentNote!!.id,
                        binding.title.text.toString(),
                        binding.content.text.toString(),
                        binding.password.text.toString()
                    )

                    App.log(
                        "saveBtn",
                        "update id -> ${noteListViewModel.currentNote!!.id}  ${noteListViewModel.currentNote!!.title} ${noteListViewModel.currentNote!!.content}"
                    )
                } else {

                    //insert
                    val note = Note(
                        binding.title.text.toString(),
                        binding.content.text.toString(),
                        binding.password.text.toString()
                    )
                    noteListViewModel.insertNote(note)

                    App.log("saveBtn", "insert")
                }

                replace(MainFragment.getInstance())
            }
        }

        binding.isLock.setOnCheckedChangeListener { _, b ->
            if (b) {
                binding.password.visibility = View.VISIBLE
            } else binding.password.visibility = View.INVISIBLE
        }
        binding.content.addTextChangedListener {
            binding.characterSize.text = it!!.length.toString()
        }


    }



    fun clearView() {

        CoroutineScope(Dispatchers.Main).launch {
            binding.content.setText("")
            binding.title.setText("")
            binding.password.setText("")
            binding.isLock.isChecked = false
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}