package com.example.submission_github

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainView: ViewModel() {
    companion object{
        private val TAG = MainView::class.java.simpleName
    }
    val listData = MutableLiveData<ArrayList<Data>>()

    fun setData(name: String?){
        val listItems = ArrayList<Data>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=$name"
        client.addHeader("Authorization","token 7c9c872526439f91c0e8ab022601d26fbc8c2d9d")
        client.addHeader("User-Agent","request")
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")

                    for (i in 0 until items.length()){
                        val item = items.getJSONObject(i)
                        val name = item.getString("login")
                        val photo = item.getString("avatar_url")
                        val id = item.getInt("id")
                        val user = Data()
                        user.nama = name
                        user.photo = photo
                        user.id = id.toString()
                        listItems.add(user)
                    }
                    listData.postValue(listItems)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }

        })
    }
    fun setDataFollowing(name: String?){
        val listItems = ArrayList<Data>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$name/following"
        client.addHeader("Authorization","token 7c9c872526439f91c0e8ab022601d26fbc8c2d9d")
        client.addHeader("User-Agent","request")
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val items = JSONArray(result)

                    for (i in 0 until items.length()){
                        val item = items.getJSONObject(i)
                        val name = item.getString("login")
                        val photo = item.getString("avatar_url")
                        val id = item.getInt("id")
                        val user = Data()
                        user.nama = name
                        user.photo = photo
                        user.id = id.toString()
                        listItems.add(user)
                    }
                    listData.postValue(listItems)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }

        })
    }
    fun setDataFollowers(name: String?){
        val listItems = ArrayList<Data>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$name/followers"
        client.addHeader("Authorization","token 7c9c872526439f91c0e8ab022601d26fbc8c2d9d")
        client.addHeader("User-Agent","request")
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val items = JSONArray(result)
                    for (i in 0 until items.length()){
                        val item = items.getJSONObject(i)
                        val name = item.getString("login")
                        val photo = item.getString("avatar_url")
                        val id = item.getInt("id")
                        val user = Data()
                        user.nama = name
                        user.photo = photo
                        user.id = id.toString()
                        listItems.add(user)
                    }
                    listData.postValue(listItems)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }

        })
    }
    fun getData(): LiveData<ArrayList<Data>> {
        return listData
    }
}
