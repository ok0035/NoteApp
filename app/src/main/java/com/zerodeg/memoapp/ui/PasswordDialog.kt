package com.zerodeg.memoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.zerodeg.memoapp.App
import com.zerodeg.memoapp.R
import com.zerodeg.memoapp.databinding.PasswordDialogBinding
import com.zerodeg.memoapp.ui.note.NoteFragment
import com.zerodeg.memoapp.vm.NoteListViewModel


class PasswordDialog() : DialogFragment() {

    private var _binding : PasswordDialogBinding? = null
    private val binding get() = _binding
    private val noteListViewModel : NoteListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PasswordDialogBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.confirmPassword.setOnClickListener {
            when {
                noteListViewModel.currentNote == null -> {
                    App.log("password", "null")
                    dismiss()
                }
                binding!!.editPassword.text.toString() == noteListViewModel.currentNote?.password -> {
                    replace(NoteFragment.getInstance())
                    noteListViewModel.loadNote(noteListViewModel.currentNote!!)
                    App.log("password", "correct")
                    dismiss()
                }
                else -> {
                    Toast.makeText(requireActivity(), "비밀번호가 맞지 않습니다.", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private fun replace(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
}