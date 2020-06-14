package com.app.william.testpaging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.william.testpaging.databinding.RowMainBinding

class MainAdapter :
    PagedListAdapter<Message, MainAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.message == newItem.message
        }

    }) {

    fun getIndexById(id:Long):Int?{
        return currentList?.indexOfFirst {
            it?.id == id
        }
    }


    inner class ViewHolder(private val row: RowMainBinding) : RecyclerView.ViewHolder(row.root) {

        fun set(message: Message) {
            row.message = message
            row.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RowMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.set(it)
        }
    }

}