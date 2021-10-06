package com.example.sfoide.ui.userlist

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.sfoide.databinding.ActivityMainBinding
import com.example.sfoide.entities.UserData
import com.example.sfoide.remote.NetworkManager
import com.example.sfoide.ui.userdetail.UserDetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = UserListRecyclerViewAdapter(::showUserDetail)
    private var backPressedTime: Long = 0
    private val result = NetworkManager.UserApi.getUserList(6)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = adapter

        doEnqueue()
        doRefresh()
        initScrollListener()
    }

    private fun initScrollListener() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView.canScrollVertically(1)) {
                    doEnqueue()
                }
            }
        }
        )
    }

    private fun showUserDetail(item: UserData.Result) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra("userData", item)
        startActivity(intent)
    }

    private fun doEnqueue() {
        result.clone().enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                adapter.submitList(response.body()!!.results)
                Log.d(TAG, "onResponse: 데이터")
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                Log.d(TAG, "실패 이유는 ${t.cause}입니다.")
            }
        })
    }

    private fun doRefresh() {
        binding.swipeLayout.setOnRefreshListener {
            doEnqueue()
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
