package com.example.finapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finapp.model.Operacao
import kotlinx.coroutines.flow.Flow

@Dao
interface OperacaoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(operacao: Operacao)

    @Query("SELECT * FROM operacoes ORDER BY id DESC")
    fun getAllOperacoes(): Flow<List<Operacao>>
}