package com.example.finapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finapp.db.OperacoesRepository

class OperacaoViewModelFactory(
    private val repository: OperacoesRepository)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OperacaoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OperacaoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}