package com.example.questionbook.room

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converter {
    companion object{
        const val pattern = "yyyy-MM-dd HH:mm"
    }
    @TypeConverter
    fun fromTimestamp(dateStr:String?): LocalDateTime? {
        return dateStr?.let {
            LocalDateTime.parse(
                dateStr, DateTimeFormatter.ofPattern(pattern)
            )
        }
    }
    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.let {
            it.format(DateTimeFormatter.ofPattern(pattern))
        }
    }
}