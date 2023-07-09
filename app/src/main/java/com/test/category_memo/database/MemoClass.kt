package com.test.category_memo.database

class MemoClass {

    companion object {
        var categoryList = mutableListOf<CategoryClass>()
        var memoList = mutableListOf<MemoInfo>()
        var categoryMemoList = mutableListOf<MemoInfo>()
        var category = ""
    }
}

data class PasswordClass(var idx:Int, var pw:String)

data class CategoryClass(var idx:Int, var category:String)

data class MemoInfo(var idx:Int, var category:String, var memoName:String, var memoContent:String, var date:String)