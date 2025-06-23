package com.example.finapp.db

import com.example.finapp.dao.OperacaoDao
import com.example.finapp.model.Operacao
import com.example.finapp.model.TipoOperacao
import kotlinx.coroutines.flow.Flow

class OfflineOperacoesRepository(private val operacaoDao: OperacaoDao) : OperacoesRepository {
    override fun getAllItemsStream(): Flow<List<Operacao>> = operacaoDao.getAllOperacoes()
    override fun getItemsByTipoStream(tipo: TipoOperacao): Flow<List<Operacao>> = operacaoDao.getOperacoesByTipo(tipo)
    override suspend fun insertItem(operacao: Operacao) = operacaoDao.insert(operacao)
}