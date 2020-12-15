package com.example.submission_github.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission_github.Data
import com.example.submission_github.R
import kotlinx.android.synthetic.main.item_list.view.*

class DetailAdapter: RecyclerView.Adapter<DetailAdapter.ListViewHolder>()  {

    private var listData = ArrayList<Data>()

    fun setData(items: ArrayList<Data>){
        listData.clear()
        listData.addAll(items)
        notifyDataSetChanged()
    }
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_list, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: Data){
            with(itemView){
                Glide.with(itemView.context)
                    .load(data.photo)
                    .apply(RequestOptions().override(55,55))
                    .into(ImageView)

                txt_name.text = data.nama
                txt_des.text = data.id

                itemView.setOnClickListener{onItemClickCallback?.onItemClicked(data)}

            }
        }
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: Data)
    }
}