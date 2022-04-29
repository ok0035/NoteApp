package com.zerodeg.memoapp.ui.notelist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zerodeg.memoapp.App
import com.zerodeg.memoapp.R
import com.zerodeg.memoapp.databinding.NoteItemBinding
import com.zerodeg.memoapp.room.Note

class NoteListAdapter: RecyclerView.Adapter<NoteListAdapter.NoteListViewHolder>() {

    private var _binding:NoteItemBinding? = null
    private val binding get() = _binding
    private val noteList:MutableList<Note> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListViewHolder {
        val inflater = LayoutInflater.from(App.applicationContext())
        _binding = NoteItemBinding.inflate(inflater, parent, false)
        return NoteListViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) {
        
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    class NoteListViewHolder(binding: NoteItemBinding): RecyclerView.ViewHolder(binding.root) {

        val select: CheckBox = binding.select
        val title: TextView = binding.title
        val content: TextView = binding.content

    }
}