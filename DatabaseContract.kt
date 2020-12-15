package com.example.submission_github.database

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class FavoriteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "Favorit_Table"
            const val _ID = "_id"
            const val USERNAME = "username"
            const val COMPANY = "company"
            const val AVATAR = "avatar"
        }
    }
}