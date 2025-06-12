package com.example.finapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finapp.db.OperacoesRepository
import com.example.finapp.model.Operacao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class OperacaoViewModel(private val repository: OperacoesRepository): ViewModel() {
    val operacoes: Flow<List<Operacao>> = repository.getAllItemsStream()
        .flowOn(Dispatchers.IO)

    fun insert(operacao: Operacao) = viewModelScope.launch {
        repository.insertItem(operacao)

    }
}