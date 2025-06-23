package com.example.finapp.db

import com.example.finapp.model.Operacao
import com.example.finapp.model.TipoOperacao
import kotlinx.coroutines.flow.Flow

interface OperacoesRepository {
    fun getAllItemsStream(): Flow<List<Operacao>>
    fun getItemsByTipoStream(tipo: TipoOperacao): Flow<List<Operacao>>
    suspend fun insertItem(operacao: Operacao)
}