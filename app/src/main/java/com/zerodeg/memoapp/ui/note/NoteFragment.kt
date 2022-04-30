package com.zerodeg.memoapp.ui.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.zerodeg.memoapp.App
import com.zerodeg.memoapp.databinding.NoteFragmentBinding
import com.zerodeg.memoapp.room.Note
import com.zerodeg.memoapp.room.NoteDatabase
import com.zerodeg.memoapp.vm.NoteListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class NoteFragment : Fragment() {
    //recycler view에서 item click시 여기서 메모 보여주기
    private var _binding: NoteFragmentBinding? = null
    private val binding get() = _binding!!
    private var currentNote:Note? = null

    companion object {
        fun newInstance() = NoteFragment()
    }

    lateinit var noteListViewModel: NoteListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NoteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteListViewModel = ViewModelProvider(this)[NoteListViewModel::class.java]

        // TODO: Use the ViewModel
        noteListViewModel.noteLiveData.observe(viewLifecycleOwner) {

            App.log("NoteViewModel", "Note -> ${it.title}")
            binding.title.setText(it.title)
            binding.content.setText(it.content)
            binding.isLock.isChecked = it.password.isNullOrEmpty()
            currentNote = it

        }

        binding.saveBtn.setOnClickListener {

            //DB에 수정내용 저장
            CoroutineScope(Dispatchers.IO).launch {

                when (currentNote) {
                    null -> {
                        //새로운 내용 저장
                        val note = Note(
                            binding.title.text.toString(),
                            binding.content.text.toString(),
                            binding.password.text.toString()
                        )
                        val db = NoteDatabase.getInstance(App.applicationContext())!!
                        noteListViewModel.insertNote(note)
                        db.close()
                    }

                    else -> {
                        //기존 내용 수정
                        val db = NoteDatabase.getInstance(App.applicationContext())!!
                        db.noteDao().update(currentNote!!)
                        noteListViewModel.update()
                        db.close()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}