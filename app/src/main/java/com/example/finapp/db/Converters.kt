package com.example.finapp.db

import androidx.room.TypeConverter
import com.example.finapp.model.TipoOperacao
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTipoOperacao(value: TipoOperacao): String = value.name

    @TypeConverter
    fun toType(value: String): TipoOperacao = TipoOperacao.valueOf(value)

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }}
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
    return date?.time
}