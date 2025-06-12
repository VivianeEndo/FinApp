package com.example.finapp.db

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val operacoesRepository: OperacoesRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineOperacoesRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [OperacoesRepository]
     */
    override val operacoesRepository: OperacoesRepository by lazy {
        OfflineOperacoesRepository(AppDatabase.getDatabase(context).operacaoDao())
    }
}
