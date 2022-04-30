package com.zerodeg.memoapp.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.zerodeg.memoapp.App
import com.zerodeg.memoapp.R
import com.zerodeg.memoapp.databinding.MainFragmentBinding
import com.zerodeg.memoapp.ui.note.NoteFragment
import com.zerodeg.memoapp.ui.notelist.NoteInterface
import com.zerodeg.memoapp.ui.notelist.NoteListAdapter
import com.zerodeg.memoapp.vm.NoteListViewModel

class MainFragment : Fragment(), NoteInterface {

    private var _binding:MainFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var navController: NavController
    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var noteListViewModel: NoteListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        noteListViewModel = ViewModelProvider(this)[NoteListViewModel::class.java]

        // TODO: Use the ViewModel
        navController = Navigation.findNavController(view)
        val noteAdapter = NoteListAdapter(this)
        binding.rvNoteList.adapter = noteAdapter

        noteListViewModel.noteListLiveData.observe(viewLifecycleOwner) {
            App.log("NoteListViewModel", "changed data -> $it")
            noteAdapter.submitList(it)
        }
        noteListViewModel.update()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun clickNote(pos: Int) {
        App.log("clickNote", "Click")
        val note = (binding.rvNoteList.adapter as NoteListAdapter).getNote(pos)
        App.log("clickNote", "note -> $note")
        noteListViewModel.loadNote(note)
        navController.navigate(R.id.action_mainFragment_to_noteFragment)
    }
}