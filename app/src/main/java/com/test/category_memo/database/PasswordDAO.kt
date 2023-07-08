package com.test.category_memo.database

import android.content.ContentValues
import android.content.Context

class PasswordDAO {
    companion object {
        // Create : 저장
        fun insertData(context: Context, data: PasswordClass) {
            // 컬럼이름과 데이터 설정 객체
            val contentValues = ContentValues()
            // 컬럼 이름, 값 지정
            contentValues.put("idx", data.idx)
            contentValues.put("passwordData", data.pw)

            val dbHelper = DBHelper(context)
            // 저장할 데이터를 가지고 있는 객체
            dbHelper.writableDatabase.insert("PasswordTable", null, contentValues)
            dbHelper.close()
        }

        // Read Condition : 조건에 맞는 행 하나를 가져온다.
        fun selectData(context: Context, idx: Int): PasswordClass {

            val dbHelper = DBHelper(context)
            val selection = "idx = ?"
            val args = arrayOf("$idx")
            val cursor = dbHelper.writableDatabase.query(
                "PasswordTable",
                null,
                selection,
                args,
                null,
                null,
                null
            )

            cursor.moveToNext()

            // 컬럼의 이름을 지정하여 컬럼의 순서값을 가져온다.
            val idx1 = cursor.getColumnIndex("idx")
            val idx2 = cursor.getColumnIndex("passwordData")

            // 데이터를 가져온다.
            val idx = cursor.getInt(idx1)
            val passwordData = cursor.getString(idx2)

            val passwordClass = PasswordClass(idx, passwordData)

            dbHelper.close()

            return passwordClass
        }
    }
}