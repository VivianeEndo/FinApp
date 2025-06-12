package com.example.finapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finapp.db.AppDatabase
import com.example.finapp.db.OfflineOperacoesRepository
import com.example.finapp.ui.OperacaoAdapter
import com.example.finapp.ui.OperacaoViewModel
import com.example.finapp.ui.OperacaoViewModelFactory
import kotlinx.coroutines.launch

class ExtratoActivity : AppCompatActivity() {
    private lateinit var adapter: OperacaoAdapter
    private lateinit var viewModel: OperacaoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_extrato)

        adapter = OperacaoAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewOperacoes)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dao = AppDatabase.getDatabase(applicationContext).operacaoDao()
        val repository = OfflineOperacoesRepository(dao)
        val factory = OperacaoViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)
            .get(OperacaoViewModel::class.java)

        lifecycleScope.launch {
            viewModel.operacoes.collect { operacoes ->
                adapter.submitList(operacoes)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}