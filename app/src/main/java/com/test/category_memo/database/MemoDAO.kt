package com.test.category_memo.database

import android.content.ContentValues
import android.content.Context
import com.test.category_memo.database.MemoClass.Companion.memoList

class MemoDAO {
    companion object {
        // Create : 저장
        fun insertData(context: Context, data:MemoInfo){
            // 컬럼이름과 데이터 설정 객체
            val contentValues = ContentValues()
            // 컬럼 이름, 값 지정
            contentValues.put("idx", data.idx)
            contentValues.put("categoryData", data.category)
            contentValues.put("memoNameData", data.memoName)
            contentValues.put("memoContentData", data.memoContent)
            contentValues.put("dateData", data.date)

            val dbHelper = DBHelper(context)
            // 저장할 데이터를 가지고 있는 객체
            dbHelper.writableDatabase.insert("MemoTable", null, contentValues)
            dbHelper.close()
        }

        // Read Condition : 조건에 맞는 행 하나를 가져온다.
        fun selectData(context: Context, idx:Int):MemoInfo{

            val dbHelper = DBHelper(context)
            val selection = "idx = ?"
            val args = arrayOf("$idx")
            val cursor = dbHelper.writableDatabase.query("MemoTable", null, selection, args, null, null, null)

            cursor.moveToNext()

            // 컬럼의 이름을 지정하여 컬럼의 순서값을 가져온다.
            val idx1 = cursor.getColumnIndex("idx")
            val idx2 = cursor.getColumnIndex("categoryData")
            val idx3 = cursor.getColumnIndex("memoNameData")
            val idx4 = cursor.getColumnIndex("memoContentData")
            val idx5 = cursor.getColumnIndex("dateData")

            // 데이터를 가져온다.
            val idx = cursor.getInt(idx1)
            val categoryData = cursor.getString(idx2)
            val memoNameData = cursor.getString(idx3)
            val memoContentData = cursor.getString(idx4)
            val dateData = cursor.getString(idx5)


            val memoInfoClass = MemoInfo(idx, categoryData, memoNameData, memoContentData, dateData)

            dbHelper.close()

            return memoInfoClass
        }

        // Read All : 모든 행을 가져온다
        fun selectAllData(context: Context):MutableList<MemoInfo>{

            val dbHelper = DBHelper(context)

            val cursor = dbHelper.writableDatabase.query("MemoTable", null, null, null, null, null, null)

            while(cursor.moveToNext()){
                // 컬럼의 이름을 지정하여 컬럼의 순서값을 가져온다.
                val idx1 = cursor.getColumnIndex("idx")
                val idx2 = cursor.getColumnIndex("categoryData")
                val idx3 = cursor.getColumnIndex("memoNameData")
                val idx4 = cursor.getColumnIndex("memoContentData")
                val idx5 = cursor.getColumnIndex("dateData")

                // 데이터를 가져온다.
                val idx = cursor.getInt(idx1)
                val categoryData = cursor.getString(idx2)
                val memoNameData = cursor.getString(idx3)
                val memoContentData = cursor.getString(idx4)
                val dateData = cursor.getString(idx5)

                val memoInfoClass = MemoInfo(idx, categoryData, memoNameData, memoContentData, dateData)
                memoList.add(memoInfoClass)
            }

            dbHelper.close()

            return memoList
        }

        // Update : 조건에 맞는 행의 컬럼의 값 수정
        fun updateData(context: Context, data:MemoInfo){
            // 컬럼과 값을 지정하는 ContentValues 생성한다.
            val contentValues = ContentValues()
            contentValues.put("idx",data.idx)
            contentValues.put("categoryData", data.category)
            contentValues.put("memoNameData", data.memoName)
            contentValues.put("memoContentData", data.memoContent)
            contentValues.put("dateData", data.date)
            // 조건절
            val condition = "idx = ?"
            // ?에 들어갈 값
            val args = arrayOf("${data.idx}")
            // 수정
            val dbHelper = DBHelper(context)
            // 테이블명, content values, 조건절, ?에 들어갈 값
            dbHelper.writableDatabase.update("MemoTable", contentValues, condition, args)
            dbHelper.close()
        }

        // deleteUpdate : 항목 삭제시 조건에 맞는 행의 컬럼의 값 수정
        fun deleteUpdateData(context: Context, idx: Int, data:MemoInfo){
            // 컬럼과 값을 지정하는 ContentValues 생성한다.
            val contentValues = ContentValues()
            contentValues.put("idx",data.idx)
            contentValues.put("categoryData", data.category)
            contentValues.put("memoNameData", data.memoName)
            contentValues.put("memoContentData", data.memoContent)
            contentValues.put("dateData", data.date)
            // 조건절
            val condition = "idx = ?"
            // ?에 들어갈 값
            val args = arrayOf("${idx}")
            // 수정
            val dbHelper = DBHelper(context)
            // 테이블명, content values, 조건절, ?에 들어갈 값
            dbHelper.writableDatabase.update("MemoTable", contentValues, condition, args)
            dbHelper.close()
        }


        // Delete : 조건 맞는 행 삭제
        fun deleteData(context: Context, idx:Int){
            // 조건절
            val condition = "idx = ?"
            // ?에 들어갈 값
            val args = arrayOf("$idx")

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.delete("MemoTable", condition, args)
            dbHelper.close()
        }
    }
}