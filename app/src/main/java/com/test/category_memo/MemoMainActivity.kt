package com.test.category_memo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.category_memo.database.MemoClass.Companion.category
import com.test.category_memo.databinding.ActivityMemoMainBinding

class MemoMainActivity : AppCompatActivity() {

    lateinit var activityMemoMainBinding: ActivityMemoMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMemoMainBinding = ActivityMemoMainBinding.inflate(layoutInflater)
        setContentView(activityMemoMainBinding.root)

        activityMemoMainBinding.run {
            toolbarMemoMain.run {
                title = "$category"
                setTitleTextColor(Color.WHITE)
            }
        }
    }
}