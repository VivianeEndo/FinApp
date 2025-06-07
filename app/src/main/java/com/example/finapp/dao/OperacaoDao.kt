package com.example.finapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.finapp.model.Operacao

@Dao
interface OperacaoDao {
    @Insert
    suspend fun inserir(operacao: Operacao)

    @Query("SELECT * FROM operacoes ORDER BY dataCadastro DESC")
    suspend fun getOperacoes(): List<Operacao>
}