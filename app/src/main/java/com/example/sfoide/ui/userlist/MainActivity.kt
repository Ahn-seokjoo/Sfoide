package com.example.sfoide.ui.userlist

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.sfoide.R
import com.example.sfoide.entities.UserData
import com.example.sfoide.remote.NetworkManager
import com.example.sfoide.ui.userdetail.UserDetailActivity
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val adapter = RecyclerViewAdapter(::showUserDetail)
    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val swipe = findViewById<SwipeRefreshLayout>(R.id.swipeLayout)
        val result = NetworkManager.UserApi.getUserList(3)
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            doEnqueue(result)
        }
        doRefresh(swipe, result)

    }

    private fun showUserDetail(item: UserData.Result) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra("userData", item)
        startActivity(intent)
    }

    private fun doEnqueue(result: Call<UserData>) {
        result.clone().enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                adapter.submitList(response.body()!!.results)
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                Log.d(TAG, "실패 이유는 ${t.message}입니다.")
            }
        })
    }

    private fun doRefresh(swipe: SwipeRefreshLayout, result: Call<UserData>) {
        swipe.setOnRefreshListener {
            doEnqueue(result)
            swipe.isRefreshing = false // 새로 고침 아이콘 제거
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
