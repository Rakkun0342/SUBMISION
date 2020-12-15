package com.example.submission_github

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission_github.adapter.FavoriteAdapter
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    private lateinit var adapter : FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        showRecycler()

    }
    private fun showRecycler(){
        adapter = FavoriteAdapter()
        adapter.notifyDataSetChanged()

        favorit_rv.layoutManager = LinearLayoutManager(this)
        favorit_rv.adapter = adapter
    }
}