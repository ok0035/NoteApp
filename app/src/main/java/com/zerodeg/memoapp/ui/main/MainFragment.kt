package com.zerodeg.memoapp.ui.main

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.zerodeg.memoapp.App
import com.zerodeg.memoapp.databinding.MainFragmentBinding
import com.zerodeg.memoapp.ui.BaseFragment
import com.zerodeg.memoapp.ui.PasswordDialog
import com.zerodeg.memoapp.ui.note.NoteFragment
import com.zerodeg.memoapp.ui.notelist.NoteInterface
import com.zerodeg.memoapp.ui.notelist.NoteListAdapter
import com.zerodeg.memoapp.vm.NoteListViewModel

class MainFragment : BaseFragment(), NoteInterface, MainInterface {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    //    lateinit var navController: NavController
    companion object {
        private var instance: MainFragment? = null

        fun getInstance(): MainFragment {
            if (instance == null) instance = MainFragment()
            return instance!!
        }
    }

    private val noteListViewModel: NoteListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.log("MainFragment", "onViewCreated")
//        noteListViewModel = ViewModelProvider(this)[NoteListViewModel::class.java]

        // TODO: Use the ViewModel
//        navController = Navigation.findNavController(view)

        binding.btnWrite.setOnClickListener { clickWrite() }
        binding.delete.setOnClickListener { clickDelete() }

        val noteAdapter = NoteListAdapter(this)
        binding.rvNoteList.adapter = noteAdapter
        noteListViewModel.noteListLiveData.observe(viewLifecycleOwner) {
            App.log("NoteListViewModel", "changed data -> $it")
            //중복되는 현상 방지 위해서 distinct 사용
            noteAdapter.submitList(it.distinct())
        }
        noteListViewModel.update()
        binding.editSearch.addTextChangedListener {
            changedSearchingTest(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun clickNote(pos: Int) {
        App.log("clickNote", "Click")
        val note = (binding.rvNoteList.adapter as NoteListAdapter).getNote(pos)
        noteListViewModel.currentNote = note
        App.log("clickNote", "note -> $note")

        if(!note.password.isNullOrEmpty()) PasswordDialog().show(parentFragmentManager, "Password Dialog")
        else {
            replace(NoteFragment.getInstance())
            noteListViewModel.loadNote(note)
//        navController.navigate(R.id.action_mainFragment_to_noteFragment)
        }
    }

    override fun clickWrite() {
        App.log("clickWrite", "Click")
        replace(NoteFragment.getInstance())
        noteListViewModel.newNote()
    }

    override fun clickDelete() {
        val adapter :NoteListAdapter = binding.rvNoteList.adapter as NoteListAdapter
        println(adapter.holderList.toList()[0].checkBoxView.isChecked)
        adapter.holderList.forEach {
            if(it.checkBoxView.isChecked) {
                noteListViewModel.deleteByID(it.id!!)
            }
        }
        noteListViewModel.update()
    }

    override fun changedSearchingTest(edit: Editable?) {
        noteListViewModel.searchNote(edit.toString())
    }

}