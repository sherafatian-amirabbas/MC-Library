package com.example.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_book.view.*


class BookAdapter : ListAdapter<Book, BookAdapter.BookViewHolder>(BookDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(book: Book) {
            itemView.txtTitleItemBook.text = book.title
            itemView.txtDescItemBook.text = book.description
            itemView.txtAuthorItemBook.text = book.author
            itemView.txtISBNItemBook.text = book.ISBN
        }

    }
}

class BookDiffCallback : DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
        return newItem == oldItem
    }
}