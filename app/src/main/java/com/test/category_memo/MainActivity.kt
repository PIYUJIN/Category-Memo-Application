package com.test.category_memo

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.category_memo.database.CategoryClass
import com.test.category_memo.database.CategoryDAO
import com.test.category_memo.database.MemoClass.Companion.categoryList
import com.test.category_memo.database.MemoClass.Companion.memoList
import com.test.category_memo.database.MemoDAO
import com.test.category_memo.database.MemoInfo
import com.test.category_memo.databinding.ActivityMainBinding
import com.test.category_memo.databinding.DialogBinding
import com.test.category_memo.databinding.RowBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.run {
            toolbarCategoryList.run {
                title = "카테고리 목록"
                setTitleTextColor(Color.WHITE)

                inflateMenu(R.menu.add_menu)

                setOnMenuItemClickListener {
                    val dialogBinding = DialogBinding.inflate(layoutInflater)

                    val builder = AlertDialog.Builder(this@MainActivity)

                    builder.setView(dialogBinding.root)
                    builder.setNegativeButton("취소", null)
                    builder.setPositiveButton("추가", ) { dialogInterface: DialogInterface, i: Int ->
                        var inputCategory = dialogBinding.editTextInputCategory.text.toString()
                        CategoryDAO.insertData(this@MainActivity, CategoryClass(categoryList.size,inputCategory))

                        categoryList.clear()
                        categoryList = CategoryDAO.selectAllData(this@MainActivity)

                        for (i in 0 until categoryList.size) {
                            Log.d("lion","idx : ${categoryList[i].idx}")
                            Log.d("lion","category : ${categoryList[i].category}")
                        }

                        // 리사이클러뷰 갱신
                        activityMainBinding.recyclerViewCategory.adapter?.notifyDataSetChanged()

                    }

                    builder.show()

                    false
                }
            }

            recyclerViewCategory.run {
                adapter = RecyclerViewAdapter()

                layoutManager = LinearLayoutManager(this@MainActivity)

                // 구분선
                addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
            }
        }
    }

    override fun onResume() {

        categoryList.clear()

        categoryList = CategoryDAO.selectAllData(this@MainActivity)

        // 리사이클러뷰 갱신
        activityMainBinding.recyclerViewCategory.adapter?.notifyDataSetChanged()
        super.onResume()
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>() {
        inner class ViewHolderClass(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root) {
            var textViewCategory : TextView

            init {
                textViewCategory = rowBinding.textViewCategoryName
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            var rowBinding = RowBinding.inflate(layoutInflater)
            var viewHolder = ViewHolderClass(rowBinding)

            var params = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            rowBinding.root.layoutParams = params

            return viewHolder
        }

        override fun getItemCount(): Int {
            return categoryList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.textViewCategory.text = categoryList[position].category
        }
    }
}