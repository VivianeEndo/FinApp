package com.example.finapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finapp.db.OperacoesRepository
import com.example.finapp.model.Operacao
import com.example.finapp.model.TipoOperacao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class OperacaoViewModel(private val repository: OperacoesRepository): ViewModel() {
    private val _filterType = MutableStateFlow(FilterType.ALL)
    val filterType: StateFlow<FilterType> = _filterType
    
    val operacoes: Flow<List<Operacao>> = repository.getAllItemsStream()
        .flowOn(Dispatchers.IO)

    fun setFilter(filterType: FilterType) {
        _filterType.value = filterType
    }
    
    fun getFilteredOperacoes(): Flow<List<Operacao>> {
        return when (_filterType.value) {
            FilterType.ALL -> repository.getAllItemsStream()
            FilterType.ENTRADA -> repository.getItemsByTipoStream(TipoOperacao.ENTRADA)
            FilterType.SAIDA -> repository.getItemsByTipoStream(TipoOperacao.SAIDA)
        }.flowOn(Dispatchers.IO)
    }

    fun insert(operacao: Operacao) = viewModelScope.launch {
        repository.insertItem(operacao)
    }
}

enum class FilterType {
    ALL, ENTRADA, SAIDA
}