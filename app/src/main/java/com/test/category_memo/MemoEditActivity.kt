package com.test.category_memo

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.category_memo.database.MemoClass
import com.test.category_memo.database.MemoDAO
import com.test.category_memo.database.MemoInfo
import com.test.category_memo.databinding.ActivityMemoEditBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MemoEditActivity : AppCompatActivity() {

    lateinit var activityMemoEditBinding: ActivityMemoEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMemoEditBinding = ActivityMemoEditBinding.inflate(layoutInflater)
        setContentView(activityMemoEditBinding.root)

        var position = intent.getIntExtra("position",0)
        var memo = MemoDAO.selectData(this,position)

        activityMemoEditBinding.run {

            editTextEditMemoName.setText(memo.memoName)
            editTextEditMemoContent.setText(memo.memoContent)

            toolbarMemoEdit.run {
                title = "메모 수정"
                setTitleTextColor(Color.WHITE)

                inflateMenu(R.menu.save_menu)

                setOnMenuItemClickListener {
                    // 현재 시간을 년-월-일 양식으로 문자열 생성
                    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val now = sdf.format(Date())

                    var memoName = editTextEditMemoName.text.toString()
                    var memoContent = editTextEditMemoContent.text.toString()

                    memo.memoName = memoName
                    memo.memoContent = memoContent

                    MemoDAO.updateData(this@MemoEditActivity,memo)

                    finish()
                    false
                }

                // back button 세팅
                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                    navigationIcon?.colorFilter = BlendModeColorFilter(Color.WHITE, BlendMode.SRC_ATOP)
                } else {
                    navigationIcon?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                }

                setNavigationOnClickListener {
                    finish()
                }
            }
        }
    }
}