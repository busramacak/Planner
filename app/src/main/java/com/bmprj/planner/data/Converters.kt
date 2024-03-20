package com.bmprj.planner.data
import androidx.room.TypeConverter
import com.bmprj.planner.model.Category
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converters {
    private val gson=Gson()

    @TypeConverter
    fun fromList(list: List<Category>):String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toList(categoryListJson:String):List<Category>{
        val listType =object: TypeToken<List<Category?>?>() {}.type
        return gson.fromJson(categoryListJson, listType)
    }

}