package com.example.sfoide.ui.userlist

import com.example.sfoide.entities.UserData

interface UserListContract {
    interface View {
        // 리사이클러뷰 세팅
        fun setRecyclerView()

        // presenter 로 데이터 요청
        fun loadRemoteDataList(seed: Int, page: Int)

        // presenter 로 받아온 데이터 화면에 보여주기
        fun showUserList(list: List<UserData.Result>)

        // Intent 통해 UserDetail 에서 보여줄 데이터 전달
        fun showUserDetail(item: UserData.Result)

        // 화면 Swipe 하여 Refresh
        fun doRefresh()

        // 뒤로가기 2번 클릭시 종료
        fun clickBackButtonTwice()

        // 뒤로가기 2번 클릭시 종료
        fun onBackPressed()

        // 앱 첫 실행시 데이터 얻어오기
        fun firstDataSubmit()

        // scrollListener 세팅
        fun setScrollListener()
    }

    interface Presenter {
        // 데이터를 가져오기
        fun loadDataList(seed: Int, page: Int)
    }
}
