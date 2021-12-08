package com.example.sfoide.ui.userlist

import com.example.sfoide.entities.UserData

interface UserListContract {
    interface View {
        // 스크롤 리스너 세팅
        fun setScrollListener()

        // 리사이클러뷰 세팅
        fun setRecyclerView()

        // Intent 통해 UserDetail 에서 보여줄 데이터 전달
        fun showUserDetail(item: UserData.Result)

        // 화면 Swipe 하여 Refresh
        fun doRefresh()

        // 백 버튼 두번 눌러 종료
        fun onBackPressed()

        // Data Adapter 로 Submit
        fun submitList()
    }

    interface Presenter {
        // 데이터를 가져오기
        suspend fun loadDataList(seed: Int, page: Int): List<UserData.Result>
    }
}
