package com.jgeron.quicknotes.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jgeron.quicknotes.R
import com.jgeron.quicknotes.data.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NotesRecyclerViewAdapter(
    notesList: List<Note>,
    private val onItemClick: (Int, Note) -> Unit) :
    RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder>() {

    private var notesMutableList: MutableList<Note> = notesList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_item,
        parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = notesMutableList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = notesMutableList.size

    fun submitList(newNotes: List<Note>){
        notesMutableList = newNotes.toMutableList()
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        notesMutableList.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder constructor(private val view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener{

        private val itemTitleView = view.noteItem_tv_title

        init {
            itemTitleView.setOnClickListener(this)
        }

        fun bind(note: Note){
            itemTitleView.text = note.title
            if ( adapterPosition % 2 == 0){
                itemTitleView.setTextColor(view.context.getColor(R.color.list_item_test_first))
            }else{
                itemTitleView.setTextColor(view.context.getColor(R.color.list_item_test_second))
            }
        }

        override fun onClick(v: View?) {
            onItemClick(adapterPosition, notesMutableList[adapterPosition])
        }
    }

}