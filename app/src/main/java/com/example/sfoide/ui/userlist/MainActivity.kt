package com.example.sfoide.ui.userlist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.sfoide.R
import com.example.sfoide.databinding.ActivityMainBinding
import com.example.sfoide.entities.UserData
import com.example.sfoide.ext.EndlessRecyclerViewScrollListener
import com.example.sfoide.ui.userdetail.UserDetailActivity
import com.example.sfoide.ui.userlist.adapter.UserListRecyclerViewAdapter
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlin.random.Random

class MainActivity : AppCompatActivity(), UserListContract.View {
    private lateinit var binding: ActivityMainBinding
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private val userRecyclerViewAdapter = UserListRecyclerViewAdapter(::showUserDetail)
    private val presenter = UserListPresenter(this)
    private var seed: Int = Random.nextInt()

    private val compositeDisposable = CompositeDisposable()
    private val behaviorSubject = BehaviorSubject.createDefault(0L)

    private val allUserData = mutableListOf<UserData.Result>()
    private val _userItemList = MutableLiveData<MutableList<UserData.Result>>()
    val userItemList: LiveData<MutableList<UserData.Result>> = _userItemList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.main = this
        binding.lifecycleOwner = this

        firstDataSubmit()
        setScrollListener()
        setRecyclerView()
        doRefresh()
        clickBackButtonTwice()
    }

    override fun firstDataSubmit() {
        loadRemoteDataList(seed, FIRST_PAGE)
    }

    override fun setScrollListener() {
        scrollListener =
            object : EndlessRecyclerViewScrollListener(binding.recyclerView.layoutManager ?: throw NullPointerException("set layoutmanager")) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    loadRemoteDataList(seed, page)
                }
            }
    }

    override fun setRecyclerView() {
        binding.recyclerView.apply {
            adapter = userRecyclerViewAdapter
            addOnScrollListener(scrollListener)
        }
    }

    override fun doRefresh() {
        with(binding) {
            swipeLayout.apply {
                setOnRefreshListener {
                    isRefreshing = true
                    scrollListener.resetState()
                    allUserData.clear()
                    seed = Random.nextInt()

                    loadRemoteDataList(seed, FIRST_PAGE)
                    isRefreshing = false
                    recyclerView.smoothScrollToPosition(0)
                }
            }
        }
    }

    override fun loadRemoteDataList(seed: Int, page: Int) {
        presenter.loadDataList(seed, page)
    }

    override fun showUserList(list: List<UserData.Result>) {
        allUserData.addAll(list)
        _userItemList.value = allUserData
    }

    override fun showUserDetail(item: UserData.Result) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra("userData", item)
        startActivity(intent)
    }

    override fun clickBackButtonTwice() {
        behaviorSubject.buffer(2, 1)
            .map {
                it[0] to it[1]
            }.subscribe {
                if (it.second - it.first < 2000L) {
                    super.onBackPressed()
                    finish()
                } else {
                    Toast.makeText(this, "뒤로 버튼을 한번 더 누르면 종료", Toast.LENGTH_SHORT).show()
                }
            }.addTo(compositeDisposable)
    }

    override fun onBackPressed() {
        behaviorSubject.onNext(System.currentTimeMillis())
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    companion object {
        const val FIRST_PAGE = 1
        const val USER_COUNT = 40
    }
}
