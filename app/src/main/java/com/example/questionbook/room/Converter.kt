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

    /**
     * ■文字列からローカルデータタイム型に変換します。
     *  このメッソドは、ルームでの保存されている値を出力する際に、
     *  LocalDateTimeへ暗黙的に変換します。エンティティで指定
     *  されているタイプとこのメソッドの戻り値が一致する場合に
     *  使用されます。
     */
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