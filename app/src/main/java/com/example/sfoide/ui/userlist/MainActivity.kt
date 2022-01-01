package com.example.sfoide.ui.userlist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sfoide.R
import com.example.sfoide.databinding.ActivityMainBinding
import com.example.sfoide.entities.UserData
import com.example.sfoide.ext.EndlessRecyclerViewScrollListener
import com.example.sfoide.ui.userdetail.UserDetailActivity
import com.example.sfoide.ui.userlist.adapter.UserListRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private val userRecyclerViewAdapter = UserListRecyclerViewAdapter(::showUserDetail)
    private val linearLayoutManager = LinearLayoutManager(this)
    private var backPressedTime: Long = 0
    private var seed: Int = Random.nextInt()
    private val viewModel: UserListViewModel by viewModels()

    //    private val viewModel by viewModels<UserPagingViewModel>()
    // 여기서 리스트를 들고 있고, 서버를 통신해서 가져온 리스트는 여기서 가지고 있음!  -> -> <- <- 말그대로 서버와 통신만 !!! + 어떤 구조가 더 좋을지 생각을 해봅시다
    private var userItemList: MutableList<UserData.Result> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        firstDataSubmit()
        setScrollListener()
        setRecyclerView()
        doRefresh()
    }

    private fun firstDataSubmit() {
        lifecycleScope.launch {
            viewModel.loadDataList(seed, FIRST_PAGE)
            submitList()
        }
    }

    private fun setScrollListener() {
        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                lifecycleScope.launch {
                    viewModel.loadDataList(seed, page)
                    submitList()
                }
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
        with(binding) {
            swipeLayout.apply {
                setOnRefreshListener {
                    isRefreshing = true
                    scrollListener.resetState()
                    userItemList.clear()
                    seed = Random.nextInt()

                    lifecycleScope.launch {
                        viewModel.loadDataList(seed, FIRST_PAGE)
                        submitList()
                        isRefreshing = false
                        recyclerView.smoothScrollToPosition(0)
                    }
                }
            }
        }
    }

    private fun showUserDetail(item: UserData.Result) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra("userData", item)
        startActivity(intent)
    }

    private fun submitList() {
        userRecyclerViewAdapter.submitList(viewModel.allUserList.toMutableList())
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
