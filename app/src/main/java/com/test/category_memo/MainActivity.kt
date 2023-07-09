package com.test.category_memo

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.category_memo.database.CategoryClass
import com.test.category_memo.database.CategoryDAO
import com.test.category_memo.database.DBHelper
import com.test.category_memo.database.MemoClass.Companion.category
import com.test.category_memo.database.MemoClass.Companion.categoryList
import com.test.category_memo.databinding.ActivityMainBinding
import com.test.category_memo.databinding.CategoryDialogBinding
import com.test.category_memo.databinding.RowBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.run {

            registerForContextMenu(recyclerViewCategory)

            toolbarCategoryList.run {
                title = "카테고리 목록"
                setTitleTextColor(Color.WHITE)

                inflateMenu(R.menu.add_menu)

                setOnMenuItemClickListener {
                    val categoryDialogBinding = CategoryDialogBinding.inflate(layoutInflater)

                    val builder = AlertDialog.Builder(this@MainActivity)

                    builder.setView(categoryDialogBinding.root)
                    builder.setNegativeButton("취소", null)
                    builder.setPositiveButton("추가", ) { dialogInterface: DialogInterface, i: Int ->
                        var inputCategory = categoryDialogBinding.editTextInputCategory.text.toString()
                        CategoryDAO.insertData(this@MainActivity, CategoryClass(categoryList.size,inputCategory))

                        categoryList.clear()
                        categoryList = CategoryDAO.selectAllData(this@MainActivity)

//                        for (i in 0 until categoryList.size) {
//                            Log.d("lion","idx : ${categoryList[i].idx}")
//                            Log.d("lion","category : ${categoryList[i].category}")
//                        }

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

                rowBinding.root.setOnCreateContextMenuListener { menu, v, menuInfo ->
                    menuInflater.inflate(R.menu.category_menu, menu)

                    var selectData = CategoryDAO.selectData(this@MainActivity, categoryList.size-adapterPosition-1)
                    category = selectData.category

                            // '수정' 메뉴 클릭
                    menu[0].setOnMenuItemClickListener {
                        val categoryDialogBinding = CategoryDialogBinding.inflate(layoutInflater)

                        categoryDialogBinding.editTextInputCategory.setText(category)

                        val builder = AlertDialog.Builder(this@MainActivity)

                        builder.setView(categoryDialogBinding.root)
                        builder.setNegativeButton("취소", null)
                        builder.setPositiveButton("추가", ) { dialogInterface: DialogInterface, i: Int ->
                            var editCategory = categoryDialogBinding.editTextInputCategory.text.toString()
                            CategoryDAO.updateData(this@MainActivity,CategoryClass(categoryList.size-adapterPosition-1,editCategory))

                            categoryList.clear()
                            categoryList = CategoryDAO.selectAllData(this@MainActivity)

                            // 리사이클러뷰 갱신
                            activityMainBinding.recyclerViewCategory.adapter?.notifyDataSetChanged()
                        }

                        builder.show()
                        false
                    }
                    // '삭제' 메뉴 클릭
                    menu[1].setOnMenuItemClickListener {
                        CategoryDAO.deleteData(this@MainActivity,categoryList.size-adapterPosition-1)
                        // 해당 카테고리 메모 모두 삭제

                        for (idx in categoryList.size-1 downTo 0) {
                            if(categoryList[idx].idx > categoryList.size-adapterPosition-1) {
                                var deleteIndex = categoryList[idx].idx
                                var obj = CategoryDAO.selectData(this@MainActivity,deleteIndex)
                                var index = obj.idx
                                obj.idx = index-1
                                CategoryDAO.deleteUpdateData(this@MainActivity,deleteIndex,obj)
                            }
                        }

                        categoryList.clear()
                        categoryList = CategoryDAO.selectAllData(this@MainActivity)

                        // 리사이클러뷰 갱신
                        activityMainBinding.recyclerViewCategory.adapter?.notifyDataSetChanged()

                        false
                    }
                }
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