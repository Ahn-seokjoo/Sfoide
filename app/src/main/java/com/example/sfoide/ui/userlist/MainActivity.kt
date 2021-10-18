package com.example.sfoide.ui.userlist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sfoide.databinding.ActivityMainBinding
import com.example.sfoide.entities.UserData
import com.example.sfoide.ext.EndlessRecyclerViewScrollListener
import com.example.sfoide.ui.userdetail.UserDetailActivity
import com.example.sfoide.ui.userlist.adapter.UserListRecyclerViewAdapter

class MainActivity : AppCompatActivity(), UserListContract.View {
    private lateinit var binding: ActivityMainBinding
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private val userRecyclerViewAdapter = UserListRecyclerViewAdapter(::showUserDetail)
    private val linearLayoutManager = LinearLayoutManager(this)
    private var backPressedTime: Long = 0
    private val presenter = UserListPresenter()
//    private val viewModel by viewModels<UserPagingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.takeView(this)

        presenter.loadFirstDataList()
        setScrollListener()
        setRecyclerView()
        doRefresh()
    }

    override fun setScrollListener() {
        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                presenter.loadNextDataFromApi(page)
            }
        }
    }

    override fun setRecyclerView() {
        with(binding.recyclerView) {
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
                presenter.clearUserListData()
                presenter.loadFirstDataList()
                recyclerView.smoothScrollToPosition(0)
                swipeLayout.isRefreshing = false
            }
        }
    }

    override fun showUserDetail(item: UserData.Result) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra("userData", item)
        startActivity(intent)
    }

    override fun submitList(userItemList: List<UserData.Result>) {
        userRecyclerViewAdapter.submitList(userItemList.toList())
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
