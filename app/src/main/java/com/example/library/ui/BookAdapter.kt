package com.example.library.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.library.R
import com.example.library.common.service.entities.Book
import kotlinx.android.synthetic.main.item_book.view.*


class BookAdapter(var books: ArrayList<Book>, val onClick: (bookId: String) -> Unit) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books.get(position))
    }

    override fun getItemCount(): Int {
        return books.size
    }

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(book: Book) {

            itemView.txtTitleItemBook.text = book.Title
            itemView.txtDescItemBook.text = book.Description
            itemView.txtAuthorItemBook.text = book.Author
            itemView.txtISBNItemBook.text = "ISBN : " + book.ISBN

            itemView.imageView_favorite.visibility = if(book.isAddedAsFavorites) View.VISIBLE else View.INVISIBLE

            itemView.setOnClickListener {

                if(onClick != null)
                    onClick(book.Id)
            }
        }
    }

    fun updateBooks(books: List<Book>) {
        this.books.clear()
        this.books.addAll(books)
        notifyDataSetChanged()
    }
}