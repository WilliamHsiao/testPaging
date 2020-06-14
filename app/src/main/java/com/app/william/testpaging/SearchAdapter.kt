package com.app.william.testpaging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.william.testpaging.databinding.RowSearchBinding

class SearchAdapter(val viewModel: SearchViewModel) :
    PagedListAdapter<Message, SearchAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.message == newItem.message
        }


    }) {


    inner class ViewHolder(private val row: RowSearchBinding) : RecyclerView.ViewHolder(row.root) {

        fun set(message: Message) {
            row.message = message
            row.viewModel = viewModel
            row.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        return ViewHolder(
            RowSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.set(it)
        }
    }
}