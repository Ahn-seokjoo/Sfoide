package com.example.sfoide.ui.userlist

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sfoide.databinding.ActivityMainBinding
import com.example.sfoide.entities.UserData
import com.example.sfoide.ext.EndlessRecyclerViewScrollListener
import com.example.sfoide.ui.userdetail.UserDetailActivity
import com.example.sfoide.ui.userlist.adapter.UserListRecyclerViewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MainActivity : AppCompatActivity(), UserListContract.View {
    private lateinit var binding: ActivityMainBinding
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private val userRecyclerViewAdapter = UserListRecyclerViewAdapter(::showUserDetail)
    private val linearLayoutManager = LinearLayoutManager(this)
    private var backPressedTime: Long = 0
    private val presenter = UserListPresenter(this)
    private var seed: Int = Random.nextInt()

    //    private val viewModel by viewModels<UserPagingViewModel>()
    // 여기서 리스트를 들고 있고, 서버를 통신해서 가져온 리스트는ㅇ ㅕ기서 가지고 있음!  -> -> <- <- 말그대로 서버와 통신만 !!! + 어떤 구조가 더 좋을지 생각을 해봅시다
    private var userItemList: MutableList<UserData.Result> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firstDataSubmit()
        setScrollListener()
        setRecyclerView()
        doRefresh()
    }

    private fun firstDataSubmit() {
        CoroutineScope(Dispatchers.IO).launch {
            userItemList.addAll(presenter.loadDataList(seed, FIRST_PAGE))
            withContext(Dispatchers.Main) {
                submitList()
            }
        }
    }

    override fun setScrollListener() {
        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                Log.d(TAG, "onLoadMore: 끝에 도착 page=$page")
                CoroutineScope(Dispatchers.IO).launch {
                    userItemList.addAll(presenter.loadDataList(seed, page))
                    withContext(Dispatchers.Main) {
                        submitList()
                    }
                }
            }
        }
    }

    override fun setRecyclerView() {
        (binding.recyclerView).apply {
            layoutManager = linearLayoutManager
            addOnScrollListener(scrollListener)
            adapter = userRecyclerViewAdapter
        }
    }

    override fun doRefresh() {
        with(binding) {
            swipeLayout.setOnRefreshListener {
                swipeLayout.isRefreshing = true
                scrollListener.resetState()

                seed = Random.nextInt()
                userItemList.clear()
                CoroutineScope(Dispatchers.IO).launch {
                    userItemList.addAll(presenter.loadDataList(seed, FIRST_PAGE))
                    withContext(Dispatchers.Main) {
                        submitList()
                        swipeLayout.isRefreshing = false
                        recyclerView.smoothScrollToPosition(0)
                    }
                }
            }
        }
    }

    override fun showUserDetail(item: UserData.Result) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra("userData", item)
        startActivity(intent)
    }

    override fun submitList() {
        userRecyclerViewAdapter.submitList(userItemList.toMutableList())
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
