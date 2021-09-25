package com.example.sfoide.ui.userlist

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.sfoide.R
import com.example.sfoide.entities.UserData
import com.example.sfoide.remote.NetworkManager
import com.example.sfoide.ui.userdetail.UserDetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val adapter = RecyclerViewAdapter(::showUserDetail)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val result = NetworkManager.UserApi.getUserList(3)
        recyclerView.adapter = adapter

        result.enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                adapter.submitList(response.body()!!.results)
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                Log.d(TAG, "실패 이유는 ${t.message}입니다.")
            }
        })
    }

    private fun showUserDetail(item: UserData.Result) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra("userData", item)
        startActivity(intent)
    }
}
