package com.example.finapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.finapp.db.AppDatabase
import com.example.finapp.db.OfflineOperacoesRepository
import com.example.finapp.model.Operacao
import com.example.finapp.model.TipoOperacao
import com.example.finapp.ui.OperacaoViewModel
import com.example.finapp.ui.OperacaoViewModelFactory
import com.google.android.material.chip.Chip
import java.util.Date

class OperacaoActivity : AppCompatActivity() {

    private lateinit var operacaoViewModel: OperacaoViewModel
    private lateinit var inputTipoEntrada: Chip
    private lateinit var inputTipoSaida: Chip
    private lateinit var inputValor: EditText
    private lateinit var inputDescricao: EditText
    private lateinit var btnSalvarOperacao: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_operacao)

        val dao = AppDatabase.getDatabase(applicationContext).operacaoDao()
        val repository = OfflineOperacoesRepository(dao)
        val factory = OperacaoViewModelFactory(repository)
        operacaoViewModel = ViewModelProvider(this, factory)
            .get(OperacaoViewModel::class.java)

        inputTipoEntrada = findViewById<Chip>(R.id.chipEntrada)
        inputTipoSaida = findViewById<Chip>(R.id.chipSaida)
        inputValor = findViewById<EditText>(R.id.editTextValor)
        inputDescricao = findViewById<EditText>(R.id.textInputDescricaoOperacao)
        btnSalvarOperacao = findViewById<Button>(R.id.btnAddOperacao)

        btnSalvarOperacao.setOnClickListener {
            salvarOperacao()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun salvarOperacao() {
        val valorString = inputValor.text.toString()
            .replace(".", "")
            .replace(",", ".")
        if (valorString.isEmpty()) {
            inputValor.error = "Valor inválido"
            inputValor.requestFocus()
            return
        }

        val tipo = when {
            inputTipoEntrada.isChecked -> TipoOperacao.ENTRADA
            inputTipoSaida.isChecked -> TipoOperacao.SAIDA
            else -> TipoOperacao.DESCONHECIDO
        }

        val descricao = inputDescricao.text.toString().trim()

        val valor = try {
            valorString.toDouble()
        } catch (e: NumberFormatException) {
            inputValor.error = "Valor inválido"
            inputValor.requestFocus()
            return
        }

        Log.d("debug", "Tipo: $tipo")
        Log.d("debug", "Valor: $valor")
        Log.d("debug", "Descrição: $descricao")

        if (descricao.isEmpty() || tipo == TipoOperacao.DESCONHECIDO) {
            Toast.makeText(this, "Preencha todos os campos para registrar uma operação!", Toast.LENGTH_SHORT).show()
        } else {
            //save to Room
            val novaOperacao = Operacao(
                tipo = tipo,
                valor = valor,
                descricao = descricao
            )
            operacaoViewModel.insert(novaOperacao)
            Toast.makeText(this, "Operação salva!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}