package com.example.submission_github.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission_github.Data
import com.example.submission_github.R
import kotlinx.android.synthetic.main.list_favorite.view.*

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoritViewHolder>() {

    var listData = ArrayList<Data>()
        set(listData){
            if (listData.size > 0){
                this.listData.clear()
            }
            this.listData.addAll(listData)

            notifyDataSetChanged()
        }

    fun addItem(data: Data) {
        this.listData.add(data)
        notifyItemInserted(this.listData.size - 1)
    }
    fun updateItem(position: Int, data: Data) {
        this.listData[position] = data
        notifyItemChanged(position, data)
    }
    fun removeItem(position: Int) {
        this.listData.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listData.size)
    }

    inner class FavoritViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(data: Data){
            with(itemView){
                Glide.with(itemView.context)
                    .load(data.photo)
                    .apply(RequestOptions().override(55,55))
                    .into(ImageView)

                tex_name.text = data.nama
                tex_id.text = data.company
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_favorite, parent, false)
        return FavoritViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = this.listData.size
}