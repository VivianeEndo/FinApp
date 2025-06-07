package com.example.finapp.db

import android.content.Context
import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.finapp.dao.OperacaoDao
import com.example.finapp.model.Operacao

@Database(entities = [Operacao::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun operacaoDao(): OperacaoDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "operacoes_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}