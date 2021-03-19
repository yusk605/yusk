package com.example.questionbook.room

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converter {
    companion object{
        const val DATETIME_PATTERN = "yyyy-MM-dd HH:mm"
        const val LOCAL_PATTERN = "yyyy-MM-dd"
    }
    @TypeConverter
    fun fromTimestamp(dateStr:String?): LocalDateTime? {
        return dateStr?.let {
            LocalDateTime.parse(
                dateStr, DateTimeFormatter.ofPattern(DATETIME_PATTERN)
            )
        }
    }
    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.format(DateTimeFormatter.ofPattern(DATETIME_PATTERN))
    }
    @TypeConverter
    fun stringToLocalDate(dateStr: String?):LocalDate? =
        LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(LOCAL_PATTERN))

    @TypeConverter
    fun localDateToString(date:LocalDate?): String? =
        date?.format(DateTimeFormatter.ofPattern(LOCAL_PATTERN))
}