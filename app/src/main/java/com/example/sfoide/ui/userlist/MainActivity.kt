package com.example.sfoide.ui.userlist

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.example.sfoide.databinding.ActivityMainBinding
import com.example.sfoide.entities.UserData
import com.example.sfoide.ui.userdetail.UserDetailActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = UserListRecyclerViewAdapter(::showUserDetail)
    private var backPressedTime: Long = 0

    private val viewModel by viewModels<UserPagingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = adapter

        doEnqueue()
        doRefresh()
    }


    private fun doEnqueue() {
        lifecycleScope.launch {
            viewModel.flow.collectLatest { value: PagingData<UserData.Result> ->
                adapter.submitData(value)
            }
        }
    }

    private fun showUserDetail(item: UserData.Result) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra("userData", item)
        startActivity(intent)
    }


    private fun doRefresh() {
        binding.swipeLayout.setOnRefreshListener {
            viewModel.doRefresh()
            adapter.refresh()
            binding.swipeLayout.isRefreshing = false // 새로 고침 아이콘 제거
            Log.d(TAG, "doRefresh: 스와이프됨")
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

}
