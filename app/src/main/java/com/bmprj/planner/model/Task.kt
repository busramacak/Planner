package com.bmprj.planner.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task (
    @PrimaryKey(autoGenerate = true) val taskId:Int=0,
    val title:String,
    val description:String,
    val taskDate:String,
    val taskTime:String,
    ){
}