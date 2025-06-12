package com.example.finapp.db

import android.content.Context
import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.finapp.db.Converters
import com.example.finapp.dao.OperacaoDao
import com.example.finapp.model.Operacao

@Database(entities = [Operacao::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun operacaoDao(): OperacaoDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "operacoes_db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}