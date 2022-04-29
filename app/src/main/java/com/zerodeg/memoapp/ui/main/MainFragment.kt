package com.zerodeg.memoapp.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zerodeg.memoapp.databinding.MainFragmentBinding
import com.zerodeg.memoapp.ui.notelist.NoteListAdapter
import com.zerodeg.memoapp.ui.notelist.NoteListViewModel

class MainFragment : Fragment() {
    //메모 리스트를 뿌려준다.

    private var _binding:MainFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var noteListViewModel: NoteListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        noteListViewModel = ViewModelProvider(this)[NoteListViewModel::class.java]
        // TODO: Use the ViewModel
        val noteAdapter = NoteListAdapter()
        binding.rvNoteList.adapter = noteAdapter

        noteListViewModel.noteLiveData.observe(viewLifecycleOwner) {
            noteAdapter.submitList(it)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}