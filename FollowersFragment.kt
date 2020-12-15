package com.example.submission_github.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission_github.MainView
import com.example.submission_github.R
import com.example.submission_github.adapter.DetailAdapter
import kotlinx.android.synthetic.main.fragment_followers.*

class FollowersFragment : Fragment() {
    private lateinit var data: DetailAdapter
    private lateinit var viewModel : MainView
    companion object{
        private val ARG_USERNAME = "username"

        fun newInstance(username: String?): FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainView::class.java)
        viewModel.getData().observe(viewLifecycleOwner, Observer { items ->
            if(items != null){
                data.setData(items)
                iconLoading(false)
            }
        })
        val username = arguments?.getString(ARG_USERNAME)
        iconLoading(true)
        viewModel.setDataFollowers(username)
        showRecyList()
    }
    private fun showRecyList() {
        data = DetailAdapter()
        data.notifyDataSetChanged()

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = data
    }
    private fun iconLoading(state: Boolean){
        if (state){
            ProgresBar.visibility = View.VISIBLE
        }else{
            ProgresBar.visibility = View.GONE
        }
    }
}