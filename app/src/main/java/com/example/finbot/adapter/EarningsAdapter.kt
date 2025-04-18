package com.example.finbot.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finbot.R
import com.example.finbot.model.Earning
import com.example.finbot.util.SharedPreferencesManager

class EarningsAdapter(
    private val context: Context,
    private val earningsList: List<Earning>,
    private val onEditClick: (Earning) -> Unit,
    private val onDeleteClick: (Earning) -> Unit
) : RecyclerView.Adapter<EarningsAdapter.EarningViewHolder>() {

    private val sharedPrefsManager = SharedPreferencesManager.getInstance(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarningViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_earning, parent, false)
        return EarningViewHolder(view)
    }

    override fun onBindViewHolder(holder: EarningViewHolder, position: Int) {
        val earning = earningsList[position]
        holder.bind(earning)
    }

    override fun getItemCount(): Int = earningsList.size

    inner class EarningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val earningTextView: TextView = itemView.findViewById(R.id.earningTextView)
        private val editIcon: ImageView = itemView.findViewById(R.id.editIcon)
        private val deleteIcon: ImageView = itemView.findViewById(R.id.deleteIcon)

        fun bind(earning: Earning) {
            val currency = sharedPrefsManager.getCurrency()
            earningTextView.text = "$currency ${String.format("%.2f", earning.amount)} (${earning.category}) - ${earning.date}"

            editIcon.setOnClickListener { onEditClick(earning) }
            deleteIcon.setOnClickListener { onDeleteClick(earning) }
        }
    }
}