package com.test.category_memo

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.test.category_memo.database.PasswordDAO
import com.test.category_memo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var activityLoginBinding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)

        activityLoginBinding.run {
            toolbarLogin.run {
                title = "로그인"
                setTitleTextColor(Color.WHITE)
            }

            buttonLogin.setOnClickListener {

                val pref = getSharedPreferences("password", MODE_PRIVATE)

                var password = pref.getString("password","")

                if(editTextTextPasswordLogin.text.toString() == password) {
                    var categoryIntent = Intent(this@LoginActivity,MainActivity::class.java)
                    startActivity(categoryIntent)
                }
                else {
                    var t1 = Toast.makeText(this@LoginActivity,"비밀번호가 잘못되었습니다.",Toast.LENGTH_SHORT)
                    t1.show()
                }
            }
        }
    }
}