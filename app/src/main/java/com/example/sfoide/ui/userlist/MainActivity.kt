package com.example.sfoide.ui.userlist

import EndlessRecyclerViewScrollListener
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sfoide.R
import com.example.sfoide.databinding.ActivityMainBinding
import com.example.sfoide.entities.UserData
import com.example.sfoide.ui.userdetail.UserDetailActivity
import com.example.sfoide.ui.userlist.adapter.UserListRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private val userRecyclerViewAdapter = UserListRecyclerViewAdapter(::showUserDetail)
    private val linearLayoutManager = LinearLayoutManager(this)
    private var backPressedTime: Long = 0
    private var seed: Int = Random.nextInt()
    private val userListViewModel: UserListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = userListViewModel

        firstDataSubmit()
        setScrollListener()
        setRecyclerView()
        doRefresh()
    }

    private fun firstDataSubmit() {
        userListViewModel.loadDataList(seed, FIRST_PAGE)
    }

    private fun setScrollListener() {
        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                userListViewModel.loadDataList(seed, page)
            }
        }
    }

    private fun setRecyclerView() {
        (binding.recyclerView).apply {
            layoutManager = linearLayoutManager
            addOnScrollListener(scrollListener)
            adapter = userRecyclerViewAdapter
        }
    }

    private fun doRefresh() {
        binding.swipeLayout.apply {
            setOnRefreshListener {
                isRefreshing = true
                scrollListener.resetState()
                seed = Random.nextInt()

                binding.viewModel?.loadDataList(seed, FIRST_PAGE)
                isRefreshing = false
            }
        }

    }

    private fun showUserDetail(item: UserData.Result) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra("userData", item)
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - backPressedTime < 2000) {
            finish()
            super.onBackPressed()
            return
        }
        Toast.makeText(this, "뒤로 버튼을 한번 더 누르면 종료", Toast.LENGTH_SHORT).show()
        backPressedTime = System.currentTimeMillis()
    }


    companion object {
        const val FIRST_PAGE = 1
        const val USER_COUNT = 40
    }
}
