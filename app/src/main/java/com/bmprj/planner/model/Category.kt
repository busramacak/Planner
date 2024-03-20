package com.bmprj.planner.model

import androidx.room.PrimaryKey


data class Category(
    @PrimaryKey(autoGenerate = false) val categoryId:Int,
    val categoryName:String
) {
}