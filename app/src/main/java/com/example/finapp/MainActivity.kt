package com.example.finapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnVerExtrato = findViewById<Button>(R.id.btnExtrato)
        btnVerExtrato.setOnClickListener {
            val intent = Intent(this, ExtratoActivity::class.java)
            startActivity(intent)
        }

        val btnAddOperacao = findViewById<Button>(R.id.btnOperacao)
        btnAddOperacao.setOnClickListener {
            val intent = Intent(this, OperacaoActivity::class.java)
            startActivity(intent)
        }

        val btnSair = findViewById<Button>(R.id.btnSair)
        btnSair.setOnClickListener {
            finishAffinity()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}