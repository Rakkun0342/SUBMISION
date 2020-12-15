package com.example.submission_github.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.submission_github.database.DatabaseContract.FavoriteColumns.Companion.AVATAR
import com.example.submission_github.database.DatabaseContract.FavoriteColumns.Companion.COMPANY
import com.example.submission_github.database.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME
import com.example.submission_github.database.DatabaseContract.FavoriteColumns.Companion.USERNAME
import com.example.submission_github.database.DatabaseContract.FavoriteColumns.Companion._ID

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbnoteapp"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_NOTE = "CREATE TABLE $TABLE_NAME" +
                " (${_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${USERNAME} TEXT NOT NULL," +
                " ${COMPANY} TEXT NOT NULL," +
                " ${AVATAR} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_NOTE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}