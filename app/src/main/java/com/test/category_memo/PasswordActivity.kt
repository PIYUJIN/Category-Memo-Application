package com.test.category_memo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.category_memo.databinding.ActivityPasswordBinding

class PasswordActivity : AppCompatActivity() {

    lateinit var activityPasswordBinding: ActivityPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityPasswordBinding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(activityPasswordBinding.root)

        activityPasswordBinding.run {
            toolbarPassword.run {
                title = "비밀번호 설정"
                setTitleTextColor(Color.WHITE)
            }



        }
    }
}