package com.test.category_memo

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.test.category_memo.database.DBHelper
import com.test.category_memo.database.PasswordClass
import com.test.category_memo.database.PasswordDAO
import com.test.category_memo.databinding.ActivityPasswordBinding
import java.io.File

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

            val databasePath = this@PasswordActivity.getDatabasePath("Memo.db").path

            val databaseFile = File(databasePath)
            val databaseExists = databaseFile.exists()

            if(databaseExists) {
                var mainIntent = Intent(this@PasswordActivity,LoginActivity::class.java)
                startActivity(mainIntent)
            }
            else {
                buttonComplete.setOnClickListener {
                    if(editTextTextPassword.text.toString() == editTextTextPasswordCheck.text.toString()) {
                        PasswordDAO.insertData(this@PasswordActivity, PasswordClass(1,editTextTextPassword.text.toString()))
                        var loginIntent = Intent(this@PasswordActivity,LoginActivity::class.java)
                        startActivity(loginIntent)
                    }
                    else {
                        val t1 = Toast.makeText(this@PasswordActivity,"비밀번호가 일치하지 않습니다.",Toast.LENGTH_LONG)
                        t1.show()
                    }
                }
            }
        }
    }
}