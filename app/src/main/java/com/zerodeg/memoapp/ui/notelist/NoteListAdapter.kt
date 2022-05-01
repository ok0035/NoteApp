package com.zerodeg.memoapp.ui.notelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.zerodeg.memoapp.App
import com.zerodeg.memoapp.R
import com.zerodeg.memoapp.databinding.NoteItemBinding
import com.zerodeg.memoapp.room.Note

class NoteListAdapter(private val noteInterface: NoteInterface): RecyclerView.Adapter<NoteListAdapter.NoteListViewHolder>() {

    private var _binding:NoteItemBinding? = null
    private val binding get() = _binding
    private var noteList:List<Note> = mutableListOf()
    val holderList:MutableList<NoteListViewHolder> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListViewHolder {
        val inflater = LayoutInflater.from(App.applicationContext())
        _binding = NoteItemBinding.inflate(inflater, parent, false)
        return NoteListViewHolder(binding!!, noteInterface)
    }

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) {
        if(noteList.size > position)
            holder.bind(noteList[position])
        holderList.add(holder)
    }

    override fun getItemId(position: Int): Long {
        return noteList[position].id.toLong()
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun submitList(list:List<Note>) {
        noteList = list
        holderList.clear()
        notifyDataSetChanged()
    }

    fun getNote(position: Int):Note {
        return noteList[position]
    }

    class NoteListViewHolder(binding: NoteItemBinding, private val noteInterface: NoteInterface): RecyclerView.ViewHolder(binding.root),
    View.OnClickListener {

        val checkBoxView: CheckBox = binding.select
        private val titleTextView: TextView = binding.title
        private val contentTextView: TextView = binding.content
        private var password: String? = null
        var id:Int? = null

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(note:Note) {

            if(!note.password.isNullOrEmpty()) {
                id = note.id
                titleTextView.text = note.title
                contentTextView.text = "***"
                checkBoxView.isChecked = false
                password = note.password
            } else {

                id = note.id
                titleTextView.text = note.title
                contentTextView.text = note.content
                checkBoxView.isChecked = false
                password = note.password
            }
        }

        override fun onClick(p0: View?) {
            noteInterface.clickNote(adapterPosition)
        }

    }

}