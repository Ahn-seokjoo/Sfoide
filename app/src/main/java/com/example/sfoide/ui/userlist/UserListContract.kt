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
        fun submitList(userItemList: List<UserData.Result>)
    }

    interface Presenter {
        // 처음 어플리케이션 실행시에 데이터 요청
        fun loadFirstDataList()

        // 무한 스크롤 구현 시 끝에 도착했을 때 다음 데이터 요청
        fun loadNextDataFromApi(offset: Int)

        // 다시 뷰로 데이터 보내줌
        fun fetchUserListData(userItemList: List<UserData.Result>)

        // 데이터 제거
        fun clearUserListData()
    }
}
