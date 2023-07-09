package com.test.category_memo

import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.category_memo.database.MemoClass.Companion.category
import com.test.category_memo.database.MemoClass.Companion.categoryMemoList
import com.test.category_memo.database.MemoClass.Companion.memoList
import com.test.category_memo.database.MemoDAO
import com.test.category_memo.database.MemoInfo
import com.test.category_memo.databinding.ActivityMemoAddBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MemoAddActivity : AppCompatActivity() {

    lateinit var activityMemoAddBinding: ActivityMemoAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMemoAddBinding = ActivityMemoAddBinding.inflate(layoutInflater)
        setContentView(activityMemoAddBinding.root)

        memoList = MemoDAO.selectAllData(this)

        activityMemoAddBinding.run {
            toolbarMemoAdd.run {
                title = "메모 추가"
                setTitleTextColor(Color.WHITE)

                inflateMenu(R.menu.save_menu)

                setOnMenuItemClickListener {
                    // 현재 시간을 년-월-일 양식으로 문자열 생성
                    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val now = sdf.format(Date())

                    var memoName = editTextMemoName.text.toString()
                    var memoContent = editTextMemoContent.text.toString()

                    var memo = MemoInfo(memoList.size, category,memoName,memoContent,now)
                    MemoDAO.insertData(this@MemoAddActivity,memo)
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