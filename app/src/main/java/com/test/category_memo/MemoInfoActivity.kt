package com.test.category_memo

import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.test.category_memo.database.MemoDAO
import com.test.category_memo.database.MemoInfo
import com.test.category_memo.databinding.ActivityMemoInfoBinding

class MemoInfoActivity : AppCompatActivity() {

    lateinit var activityMemoInfoBinding: ActivityMemoInfoBinding

    var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMemoInfoBinding = ActivityMemoInfoBinding.inflate(layoutInflater)
        setContentView(activityMemoInfoBinding.root)

        activityMemoInfoBinding.run {

            position = intent.getIntExtra("position",0)
            Log.d("lion","position : $position")

            toolbarMemoInfo.run {
                title = "메모 보기"
                setTitleTextColor(Color.WHITE)

                inflateMenu(R.menu.info_menu)

                setOnMenuItemClickListener {
                    when(it.itemId) {
                        R.id.itemEdit -> {
                            var memoAddIntent = Intent(this@MemoInfoActivity,MemoEditActivity::class.java)
                            memoAddIntent.putExtra("position",position)
                            startActivity(memoAddIntent)
                        }
                        R.id.itemDelete -> {
                            MemoDAO.deleteData(this@MemoInfoActivity,position)
                            finish()
                        }
                    }
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

    override fun onResume() {
        activityMemoInfoBinding.run {
            var memo = MemoDAO.selectData(this@MemoInfoActivity, position)
            textViewMemoInfoName.text = memo.memoName
            textViewMemoInfoContent.text = memo.memoContent
            textViewMemoInfoDate.text = memo.date
        }

        super.onResume()
    }
}