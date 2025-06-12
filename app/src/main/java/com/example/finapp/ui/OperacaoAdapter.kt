package com.example.finapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finapp.R
import com.example.finapp.model.Operacao
import com.example.finapp.model.TipoOperacao

class OperacaoAdapter: RecyclerView.Adapter<OperacaoAdapter.OperacaoViewHolder>() {
    private var operacoes: List<Operacao> = emptyList()

    fun submitList(newList: List<Operacao>) {
        operacoes = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OperacaoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_operacao, parent, false)
        return OperacaoViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: OperacaoViewHolder,
        position: Int
    ) {
        holder.bind(operacoes[position])
    }

    override fun getItemCount(): Int {
        return operacoes.size
    }

    class OperacaoViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        private val valor: TextView = itemView.findViewById(R.id.textViewValor)
        private val descricao: TextView = itemView.findViewById(R.id.textViewDescricao)
        private val icon: ImageView = itemView.findViewById(R.id.iconTipoOperacao)

        fun bind(operacao: Operacao) {
            descricao.text = operacao.descricao
            valor.text = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("pt", "BR")).format(operacao.valor)
            val iconRes = when (operacao.tipo) {
                TipoOperacao.ENTRADA -> R.drawable.baseline_north_east_24
                TipoOperacao.SAIDA -> R.drawable.baseline_arrow_back_24
                else -> R.drawable.baseline_north_east_24
            }
            when (operacao.tipo) {
                TipoOperacao.ENTRADA -> {
                    itemView.setBackgroundResource(R.color.rifleGreen)
                    descricao.setTextColor(itemView.context.getColor(R.color.cream))
                    valor.setTextColor(itemView.context.getColor(R.color.cream))
                    icon.setColorFilter(itemView.context.getColor(R.color.cream))
                    icon.setImageResource(R.drawable.baseline_arrow_back_24)
                }
                TipoOperacao.SAIDA -> {
                    itemView.setBackgroundResource(R.color.khakiGreen)
                    descricao.setTextColor(itemView.context.getColor(R.color.cream))
                    valor.setTextColor(itemView.context.getColor(R.color.cream))
                    icon.setColorFilter(itemView.context.getColor(R.color.cream))
                    icon.setImageResource(R.drawable.baseline_north_east_24)
                }
                else -> {
                    itemView.setBackgroundResource(R.color.rifleGreen)
                    descricao.setTextColor(itemView.context.getColor(R.color.cream))
                    valor.setTextColor(itemView.context.getColor(R.color.cream))
                }

            }
            icon.setImageResource(iconRes)
        }
    }
}