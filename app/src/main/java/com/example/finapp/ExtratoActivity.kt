package com.example.finapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
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
import com.example.finapp.ui.FilterType
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class ExtratoActivity : AppCompatActivity() {
    private lateinit var adapter: OperacaoAdapter
    private lateinit var viewModel: OperacaoViewModel
    private lateinit var btnFilterAll: MaterialButton
    private lateinit var btnFilterEntrada: MaterialButton
    private lateinit var btnFilterSaida: MaterialButton
    private lateinit var btnVoltar: Button
    private lateinit var textViewSaldo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_extrato)

        setupViews()
        setupDatabase()
        setupFilterButtons()
        observeData()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupViews() {
        adapter = OperacaoAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewOperacoes)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        btnFilterAll = findViewById(R.id.btnFilterAll)
        btnFilterEntrada = findViewById(R.id.btnFilterEntrada)
        btnFilterSaida = findViewById(R.id.btnFilterSaida)
        btnVoltar = findViewById(R.id.btnVoltarHome)
        textViewSaldo = findViewById(R.id.textViewSaldo)
        btnVoltar.setOnClickListener {
            finish()
        }
    }

    private fun setupDatabase() {
        val dao = AppDatabase.getDatabase(applicationContext).operacaoDao()
        val repository = OfflineOperacoesRepository(dao)
        val factory = OperacaoViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(OperacaoViewModel::class.java)
    }

    private fun setupFilterButtons() {
        btnFilterAll.setOnClickListener {
            viewModel.setFilter(FilterType.ALL)
        }

        btnFilterEntrada.setOnClickListener {
            viewModel.setFilter(FilterType.ENTRADA)
        }

        btnFilterSaida.setOnClickListener {
            viewModel.setFilter(FilterType.SAIDA)
        }
    }

    private fun updateFilterButtonStates(selectedFilter: FilterType) {
        // Reset all buttons to default state
        btnFilterAll.setBackgroundColor(getColor(R.color.rifleGreen))
        btnFilterEntrada.setBackgroundColor(getColor(R.color.khakiGreen))
        btnFilterSaida.setBackgroundColor(getColor(R.color.khakiGreen))

        // Highlight selected button
        when (selectedFilter) {
            FilterType.ALL -> {
                btnFilterAll.setBackgroundColor(getColor(R.color.rifleGreen))
                btnFilterEntrada.setBackgroundColor(getColor(R.color.sage))
                btnFilterSaida.setBackgroundColor(getColor(R.color.sage))
            }
            FilterType.ENTRADA -> {
                btnFilterEntrada.setBackgroundColor(getColor(R.color.rifleGreen))
                btnFilterAll.setBackgroundColor(getColor(R.color.sage))
                btnFilterSaida.setBackgroundColor(getColor(R.color.sage))
            }
            FilterType.SAIDA -> {
                btnFilterSaida.setBackgroundColor(getColor(R.color.rifleGreen))
                btnFilterAll.setBackgroundColor(getColor(R.color.sage))
                btnFilterEntrada.setBackgroundColor(getColor(R.color.sage))
            }
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.operacoes.collect { operacoes ->
                adapter.submitList(operacoes)
            }
        }
        lifecycleScope.launch {
            viewModel.filterType.collect { filter ->
                updateFilterButtonStates(filter)
            }
        }
        // sempre observar todas as operações para o saldo
        lifecycleScope.launch {
            viewModel.allOperacoes.collect { allOperacoes ->
                val saldo = allOperacoes.sumOf {
                    when (it.tipo) {
                        com.example.finapp.model.TipoOperacao.ENTRADA -> it.valor
                        com.example.finapp.model.TipoOperacao.SAIDA -> -it.valor
                        else -> 0.0
                    }
                }
                textViewSaldo.text = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("pt", "BR")).format(saldo)
            }
        }
    }
}