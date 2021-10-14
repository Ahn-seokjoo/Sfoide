package com.example.sfoide.ui.userlist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sfoide.databinding.ActivityMainBinding
import com.example.sfoide.entities.UserData
import com.example.sfoide.remote.NetworkManager
import com.example.sfoide.ui.userdetail.UserDetailActivity
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private val userRecyclerViewAdapter = UserListRecyclerViewAdapter(::showUserDetail)
    private val resultUserDataList = mutableListOf<UserData.Result>()
    private val linearLayoutManager = LinearLayoutManager(this)
    private var seedData = Random().nextInt()
    private var backPressedTime: Long = 0
//    private val viewModel by viewModels<UserPagingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setScrollListener()
        setRecyclerView()
        doEnqueue()
        doRefresh()
    }

    private fun setScrollListener() {
        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                loadNextDataFromApi(page)
            }
        }
    }

    private fun setRecyclerView() {
        with(binding.recyclerView) {
            layoutManager = linearLayoutManager
            addOnScrollListener(scrollListener)
            adapter = userRecyclerViewAdapter
        }
    }

    private fun loadNextDataFromApi(offset: Int) {
        submitUserDataList(offset)
    }

    private fun doEnqueue() {
        submitUserDataList()
    }

    private fun submitUserDataList(offset: Int? = null) {
        lifecycleScope.launch {
            when (offset) {
                null -> {
                    val userListData = NetworkManager.UserApi.getUserList(FIRST_PAGE, USER_COUNT, seedData)
                    userListData.body()?.results?.forEach { resultUserDataList.add(it) }
                    userRecyclerViewAdapter.submitList(resultUserDataList.toList())
                }
                else -> {
                    val userListData = NetworkManager.UserApi.getUserList(offset, USER_COUNT, seedData)
                    userListData.body()?.results?.forEach { resultUserDataList.add(it) }
                    userRecyclerViewAdapter.submitList(resultUserDataList.toList())
                }
            }
        }
    }

    private fun showUserDetail(item: UserData.Result) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra("userData", item)
        startActivity(intent)
    }

    private fun doRefresh() {
        with(binding) {
            swipeLayout.setOnRefreshListener {
                swipeLayout.isRefreshing = true
                scrollListener.resetState()
                lifecycleScope.launch {
                    resultUserDataList.clear()
                    seedData = Random().nextInt()
                    submitUserDataList()
                    recyclerView.smoothScrollToPosition(0)
                    swipeLayout.isRefreshing = false
                }
            }
        }
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - backPressedTime < 2000) {
            finish()
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
