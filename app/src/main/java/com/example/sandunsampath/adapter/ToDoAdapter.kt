package com.example.sandunsampath.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sandunsampath.R
import com.example.sandunsampath.model.ToDoModel

class ToDoAdapter(private val todoList : ArrayList<ToDoModel>) : RecyclerView.Adapter<ToDoAdapter.MyViewHolder>() {

    private lateinit var myListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener : onItemClickListener){
        myListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        TODO("Not yet implemented")
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.single_todo,parent,false)
        return MyViewHolder(itemView,myListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        TODO("Not yet implemented")
        val currentitem = todoList[position]

        holder.todoTitle.text = currentitem.title
        holder.todoDescription.text = currentitem.description
    }

    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
        return todoList.size
    }

    class MyViewHolder(itemView : View, listener : onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val todoTitle : TextView = itemView.findViewById(R.id.title)
        val todoDescription : TextView = itemView.findViewById(R.id.description)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }


    }
}