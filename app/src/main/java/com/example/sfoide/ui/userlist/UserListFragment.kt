package com.example.sfoide.ui.userlist

import EndlessRecyclerViewScrollListener
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.RecyclerView
import com.example.sfoide.R
import com.example.sfoide.databinding.FragmentUserlistBinding
import com.example.sfoide.entities.UserData
import com.example.sfoide.ui.userdetail.UserDetailFragment
import com.example.sfoide.ui.userlist.adapter.UserListRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class UserListFragment : Fragment(R.layout.fragment_userlist) {
    private lateinit var _binding: FragmentUserlistBinding
    private val binding get() = _binding

    private val userListViewModel: UserListViewModel by activityViewModels()
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private val userRecyclerViewAdapter = UserListRecyclerViewAdapter(::showUserDetail)
    private var seed: Int = Random.nextInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firstDataSubmit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DataBindingUtil.bind(view) ?: throw IllegalStateException("fail to bind")
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = userListViewModel

        doRefresh()
        setScrollListener()
        setRecyclerView()
    }

    private fun firstDataSubmit() {
        userListViewModel.loadDataList(seed, FIRST_PAGE)
    }

    private fun setScrollListener() {
        scrollListener =
            object : EndlessRecyclerViewScrollListener(binding.recyclerView.layoutManager ?: throw IllegalStateException("fail to bind")) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    userListViewModel.loadDataList(seed, page)
                }
            }
    }

    private fun setRecyclerView() {
        (binding.recyclerView).apply {
            addOnScrollListener(scrollListener)
            adapter = userRecyclerViewAdapter
        }
    }

    private fun doRefresh() {
        binding.swipeLayout.apply {
            setOnRefreshListener {
                isRefreshing = true
                scrollListener.resetState()
                seed = Random.nextInt()

                binding.viewModel?.loadDataList(seed, FIRST_PAGE)
                isRefreshing = false
            }
        }
    }

    private fun showUserDetail(item: UserData.Result) {
        parentFragmentManager.commit {
            arguments = bundleOf("userList" to item)
            replace<UserDetailFragment>(R.id.fc, args = arguments)
            addToBackStack("UserList")
            setReorderingAllowed(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }

    companion object {
        const val FIRST_PAGE = 1
    }
}
