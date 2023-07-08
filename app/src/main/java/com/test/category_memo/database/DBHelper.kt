package com.test.category_memo.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context,"Memo.db",null,1) {
    override fun onCreate(sqlistDatabase: SQLiteDatabase?) {
        val passwordsql = """create table PasswordTable
            (idx integer primary key autoincrement,
            passwordData text not null)
        """.trimIndent()

        // 쿼리문 수행
        sqlistDatabase?.execSQL(passwordsql)

        val categorysql = """create table CategoryTable
            (idx integer primary key not null,
            categoryData text not null)
        """.trimIndent()

        // 쿼리문 수행
        sqlistDatabase?.execSQL(categorysql)

        val memosql = """create table MemoTable
            (idx integer primary key not null,
            categoryData text not null,
            memoNameData text not null,
            memoContentData text not null,
            dateData date not null)
        """.trimIndent()

        // 쿼리문 수행
        sqlistDatabase?.execSQL(memosql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}