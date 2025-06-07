package com.example.finapp.db

import androidx.room.TypeConverter
import com.example.finapp.model.TipoOperacao

class Converters {
    @TypeConverter
    fun fromTipoOperacao(value: TipoOperacao): String = value.name

    @TypeConverter
    fun toType(value: String): TipoOperacao = TipoOperacao.valueOf(value)
}