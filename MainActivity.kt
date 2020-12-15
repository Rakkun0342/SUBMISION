package com.example.submission_github

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Adapter
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission_github.adapter.DetailAdapter
import com.example.submission_github.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter : DetailAdapter
    private lateinit var viewModel : MainView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainView::class.java)
        viewModel.getData().observe(this, Observer { items ->
            if (items != null){
                adapter.setData(items)
                iconLoading(false)
            }
        })
        showRecyList()
        onSearchView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorit -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return true
        }
    }

    private fun onSearchView(){
        val searchView = SV as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.masukan_nama)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                iconLoading(true)
                viewModel.setData(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }
    private fun showRecyList() {
        adapter = DetailAdapter()
        adapter.notifyDataSetChanged()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        adapter.setOnItemClickCallback(object : DetailAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Data) {
                showSelectedHero(data)
                val parcel = Data(
                    data.photo,
                    data.nama,
                    data.id,
                    data.company,
                    data.location,
                    data.following,
                    data.followers
                )
                val pindahActivity = Intent(this@MainActivity, DetailActivity::class.java)
                pindahActivity.putExtra(DetailActivity.KEY_DATA, parcel)
                startActivity(pindahActivity)
            }
        })
    }
    private fun iconLoading(state: Boolean){
        if (state){
            ProgresBar.visibility = View.VISIBLE
        }else{
            ProgresBar.visibility = View.GONE
        }
    }
    private fun showSelectedHero(hero: Data) {
        Toast.makeText(this, "Kamu memilih ${hero.nama}", Toast.LENGTH_SHORT).show()
    }

}