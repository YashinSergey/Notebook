package com.iashinsergei.notebook.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iashinsergei.notebook.R
import com.iashinsergei.notebook.data.entity.Note
import kotlinx.android.synthetic.main.note.view.*

class RvAdapter: RecyclerView.Adapter<RvAdapter.MyViewHolder>() {

    var notes: List<Note> = listOf()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note, parent, false))

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(notes[position])
    }


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(note: Note) = with(itemView) {
            tv_note_title.text = note.title
            tv_note_body.text = note.text
            setBackgroundColor(note.color)
        }
    }
}
