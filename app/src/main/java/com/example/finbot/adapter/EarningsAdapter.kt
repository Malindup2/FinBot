package com.example.finbot.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finbot.R
import com.example.finbot.model.Earning

class EarningsAdapter(
    private val earningsList: List<Earning>,
    private val onEditClick: (Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<EarningsAdapter.EarningViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarningViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_earning, parent, false)
        return EarningViewHolder(view)
    }

    override fun onBindViewHolder(holder: EarningViewHolder, position: Int) {
        val earning = earningsList[position]
        holder.bind(earning, position)
    }

    override fun getItemCount(): Int = earningsList.size

    inner class EarningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val earningTextView: TextView = itemView.findViewById(R.id.earningTextView)
        private val editIcon: ImageView = itemView.findViewById(R.id.editIcon)
        private val deleteIcon: ImageView = itemView.findViewById(R.id.deleteIcon)

        fun bind(earning: Earning, position: Int) {
            earningTextView.text = "LKR ${String.format("%.2f", earning.amount)} (${earning.category})"

            editIcon.setOnClickListener { onEditClick(position) }
            deleteIcon.setOnClickListener { onDeleteClick(position) }
        }
    }
}