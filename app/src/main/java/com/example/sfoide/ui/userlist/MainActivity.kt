package com.example.sfoide.ui.userlist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.sfoide.R
import com.example.sfoide.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.BehaviorSubject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val subject = BehaviorSubject.createDefault(0L)
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        addUserListFragment()
        setEndDoubleClick()
    }

    private fun addUserListFragment() {
        supportFragmentManager.commit {
            add<UserListFragment>(R.id.fc, tag = "UserListFragment")
            setReorderingAllowed(true)
        }
    }

    private fun setEndDoubleClick() {
        subject.buffer(2, 1)
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

    private fun getVisibleFragmentIsUserList(): Boolean {
        for (fragment in supportFragmentManager.fragments) {
            if (fragment.isVisible && fragment == supportFragmentManager.findFragmentByTag("UserListFragment")) {
                return true
            }
        }
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun onBackPressed() {
        if (getVisibleFragmentIsUserList()) {
            subject.onNext(System.currentTimeMillis())
        } else {
            super.onBackPressed()
        }
    }

}
