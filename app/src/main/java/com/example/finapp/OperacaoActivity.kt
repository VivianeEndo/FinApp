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
import com.google.android.material.chip.Chip

class OperacaoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_operacao)

        val inputTipoEntrada = findViewById<Chip>(R.id.chipEntrada)
        val inputTipoSaida = findViewById<Chip>(R.id.chipSaida)
        val inputValor = findViewById<EditText>(R.id.editTextValor)
        val inputDescricao = findViewById<EditText>(R.id.textInputDescricaoOperacao)
        val btnSalvarOperacao = findViewById<Button>(R.id.btnAddOperacao)

        val valorOperacao = findViewById<EditText>(R.id.editTextValor)
        valorOperacao.addTextChangedListener(object : TextWatcher {
            private var currentString = ""

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() != currentString) {
                    valorOperacao.removeTextChangedListener(this)
                    val clean = s.toString().replace("[^\\d]".toRegex(), "")
                    val parsed = clean.toDoubleOrNull() ?: 0.0
                    val formatted = java.text.NumberFormat.getNumberInstance(java.util.Locale("pt", "BR"))
                        .format(parsed/100)
                    currentString = formatted
                    valorOperacao.setText(formatted)
                    valorOperacao.setSelection(formatted.length)
                    valorOperacao.addTextChangedListener(this)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        btnSalvarOperacao.setOnClickListener {
            val valorString = inputValor.text.toString()
                .replace(".", "")
                .replace(",", ".")
            val valor = valorString.toDoubleOrNull() ?: 0.0

            val descricao = inputDescricao.text.toString().trim()

            val tipo = when {
                inputTipoEntrada.isChecked -> "ENTRADA"
                inputTipoSaida.isChecked -> "SAIDA"
                else -> "DESCONHECIDO"
            }

            Log.d("debug", "Tipo: $tipo")
            Log.d("debug", "Valor: $valor")
            Log.d("debug", "Descrição: $descricao")

            if (descricao.isEmpty() || tipo == "DESCONHECIDO") {
                Toast.makeText(this, "Preencha todos os campos para registrar uma operação!", Toast.LENGTH_SHORT).show()
            } else {
                //save to Room
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}