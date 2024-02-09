package com.bmprj.planner.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true) val noteId:Int=0,
    val title:String,
    val content:String,
    val date:String
)