package com.example.finapp.db

import com.example.finapp.model.Operacao
import kotlinx.coroutines.flow.Flow

interface OperacoesRepository {
    fun getAllItemsStream(): Flow<List<Operacao>>
    suspend fun insertItem(operacao: Operacao)
}