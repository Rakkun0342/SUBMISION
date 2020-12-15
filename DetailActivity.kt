package com.example.submission_github.detail

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission_github.Data
import com.example.submission_github.R
import com.example.submission_github.SectionPagerAdapter
import com.example.submission_github.database.DatabaseContract.FavoriteColumns.Companion.AVATAR
import com.example.submission_github.database.DatabaseContract.FavoriteColumns.Companion.COMPANY
import com.example.submission_github.database.DatabaseContract.FavoriteColumns.Companion.USERNAME
import com.example.submission_github.database.FavoriteHelper
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_detail.*
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {

    private lateinit var favoriteHelper: FavoriteHelper
    private var data: Data? = null

    companion object{
        const val KEY_DATA = "key"
        const val RESULT_ADD = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val dataDetail = intent.getParcelableExtra<Data>(KEY_DATA) as Data
        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        sectionPagerAdapter.username = dataDetail.nama
        view_pager.adapter = sectionPagerAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f
        detailData(dataDetail.nama)

        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()

        var status = false
        setFavoorit(status)
        floating.setOnClickListener{
            status = !status
            val values = ContentValues()
            values.put(USERNAME, dataDetail.nama)
            values.put(AVATAR, dataDetail.photo)
            val result = favoriteHelper.insert(values)
            if (result > 0) {
                setResult(RESULT_ADD)
            }
            setFavoorit(status)
        }
    }
    private fun detailData(nama : String?){
        ProgresBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$nama"
        client.addHeader("Authorization","token 7c9c872526439f91c0e8ab022601d26fbc8c2d9d")
        client.addHeader("User-Agent","request")
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                ProgresBar.visibility = View.INVISIBLE
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)

                    val name = responseObject.getString("name")
                    val photo = responseObject.getString("avatar_url")
                    val company = responseObject.getString("company")
                    val location = responseObject.getString("location")

                    val user = Data()
                    user.nama = name
                    user.photo = photo
                    user.company = company
                    user.location = location

                    Glide.with(this@DetailActivity)
                        .load(user.photo)
                        .apply(RequestOptions().override(100,100))
                        .into(img_photo)
                    txt_name.text = user.nama
                    txt_company.text = user.company
                    txt_location.text = user.location

                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                ProgresBar.visibility = View.VISIBLE
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    private fun setFavoorit(status: Boolean){
        if (status){
            floating.setImageResource(R.drawable.baseline_favorite_black_18dp)
        }else{
            floating.setImageResource(R.drawable.baseline_favorite_border_black_18dp)
        }
    }
}