package com.example.sfoide.ui.userdetail

import com.example.sfoide.entities.UserData

interface UserDetailContract {
    interface View {
        // data set
        fun setUserData(item: UserData.Result?)

        // email & phone intent
        fun setViewIntentClickListener(item: UserData.Result?)
    }
}
