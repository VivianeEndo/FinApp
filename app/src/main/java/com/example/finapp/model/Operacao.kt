package com.example.finapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "operacoes")
data class Operacao(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tipo: TipoOperacao,
    val valor: Double,
    val descricao: String
)
