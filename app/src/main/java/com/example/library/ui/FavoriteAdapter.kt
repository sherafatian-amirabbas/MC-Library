package com.example.library.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.library.R
import com.example.library.dataAccess.entities.Favorite
import kotlinx.android.synthetic.main.item_favorite.view.*

class FavoriteAdapter(var favorites: List<Favorite>, val onClick: (favoriteId: String) -> Unit) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favorites.get(position))
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(favorite: Favorite) {
            itemView.txtTitleItemFavorite.text = favorite.Title
            itemView.txtDescItemFavorite.text = favorite.Description
            itemView.txtAuthorItemFavorite.text = favorite.Author
            itemView.txtISBNItemFavorite.text = "ISBN : " + favorite.ISBN

            itemView.setOnClickListener {
                onClick(favorite.Id)
            }
        }
    }
}