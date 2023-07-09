package com.test.category_memo

import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.category_memo.database.CategoryDAO
import com.test.category_memo.database.MemoClass
import com.test.category_memo.database.MemoClass.Companion.category
import com.test.category_memo.database.MemoClass.Companion.categoryMemoList
import com.test.category_memo.database.MemoDAO
import com.test.category_memo.database.MemoInfo
import com.test.category_memo.databinding.ActivityMemoMainBinding
import com.test.category_memo.databinding.MemoBinding
import com.test.category_memo.databinding.RowBinding

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

                inflateMenu(R.menu.add_menu)

                setOnMenuItemClickListener {
                    var memoAddIntent = Intent(this@MemoMainActivity,MemoAddActivity::class.java)
                    startActivity(memoAddIntent)
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

            recyclerViewMemo.run {
                adapter = RecyclerViewAdapter()

                layoutManager = LinearLayoutManager(this@MemoMainActivity)

                // 구분선
                addItemDecoration(DividerItemDecoration(this@MemoMainActivity, DividerItemDecoration.VERTICAL))
            }
        }
    }

    override fun onResume() {
        categoryMemoList.clear()

        categoryMemoList = MemoDAO.selectCategoryData(this@MemoMainActivity, category)

        // 리사이클러뷰 갱신
        activityMemoMainBinding.recyclerViewMemo.adapter?.notifyDataSetChanged()

        super.onResume()
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>() {
        inner class ViewHolderClass(memoBinding: MemoBinding) : RecyclerView.ViewHolder(memoBinding.root) {

            var textViewMemoName : TextView

            init {
                textViewMemoName = memoBinding.textViewMemoName

                memoBinding.root.setOnClickListener {
                    var memoInfoIntent = Intent(this@MemoMainActivity,MemoInfoActivity::class.java)
                    memoInfoIntent.putExtra("position", categoryMemoList[adapterPosition].idx)
                    startActivity(memoInfoIntent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            var memoBinding = MemoBinding.inflate(layoutInflater)
            var viewHolder = ViewHolderClass(memoBinding)

            var params = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            memoBinding.root.layoutParams = params

            return viewHolder
        }

        override fun getItemCount(): Int {
            return categoryMemoList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.textViewMemoName.text = categoryMemoList[position].memoName
        }
    }
}