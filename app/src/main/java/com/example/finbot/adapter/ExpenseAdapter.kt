package com.example.finbot.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.finbot.R
import com.example.finbot.model.Expense

class ExpenseAdapter(private val context: Context, private val expenses: List<Expense>) :
    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]

        // Set icon, category, date/time, and amount
        holder.icon.setImageResource(expense.iconResId)
        holder.category.text = expense.category
        holder.date_time.text = "${expense.date} ${expense.time}"
        holder.amount.text = "LKR ${expense.amount}"

        // Dynamically set the background color of the CardView based on categoryId
        when (expense.categoryId) {
            1 -> holder.iconCardView.setCardBackgroundColor(context.getColor(R.color.food)) // Food
            2 -> holder.iconCardView.setCardBackgroundColor(context.getColor(R.color.shopping)) // Shopping
            3 -> holder.iconCardView.setCardBackgroundColor(context.getColor(R.color.transport)) // Transport
            4 -> holder.iconCardView.setCardBackgroundColor(context.getColor(R.color.health)) // Health
            5 -> holder.iconCardView.setCardBackgroundColor(context.getColor(R.color.Blue)) // Utility
            else -> holder.iconCardView.setCardBackgroundColor(context.getColor(R.color.white)) // Default
        }
    }

    override fun getItemCount(): Int {
        return expenses.size
    }

    inner class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconCardView: CardView = itemView.findViewById(R.id.cardViewId)
        val icon: ImageView = itemView.findViewById(R.id.iconResId)
        val category: TextView = itemView.findViewById(R.id.category)
        val date_time: TextView = itemView.findViewById(R.id.date_time)
        val amount: TextView = itemView.findViewById(R.id.amount)
    }
}