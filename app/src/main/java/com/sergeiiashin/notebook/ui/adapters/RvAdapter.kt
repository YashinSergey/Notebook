package com.sergeiiashin.notebook.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergeiiashin.notebook.R
import com.sergeiiashin.notebook.data.entity.Note
import kotlinx.android.synthetic.main.note.view.*

class RvAdapter(val onItemClick: ((Note) -> Unit)? = null): RecyclerView.Adapter<RvAdapter.MyViewHolder>() {

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


    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(note: Note) = with(itemView) {
            tv_note_title.text = note.title
            tv_note_body.text = note.text
            //TODO Сделать backgroundTint в соответствии с текущей темой

            itemView.setOnClickListener{
                onItemClick?.invoke(note)
            }
        }
    }
}
