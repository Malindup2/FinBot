package com.example.finbot.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finbot.R
import com.example.finbot.adapter.ExpenseAdapter
import com.example.finbot.model.Expense
import com.example.finbot.util.NotificationHelper
import com.example.finbot.util.SharedPreferencesManager
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.snackbar.Snackbar

class homeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyStateTextView: TextView
    private lateinit var totalExpenseTextView: TextView
    private lateinit var budgetTextView: TextView
    private lateinit var progressBar: LinearProgressIndicator
    private lateinit var progressPercentage: TextView
    private lateinit var spentPercentText: TextView
    private lateinit var limitText: TextView
    private lateinit var sharedPrefsManager: SharedPreferencesManager
    private lateinit var notificationHelper: NotificationHelper
    private lateinit var adapter: ExpenseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.home, container, false)

        // Initialize managers
        sharedPrefsManager = SharedPreferencesManager.getInstance(requireContext())
        notificationHelper = NotificationHelper.getInstance(requireContext())

        // Initialize views
        recyclerView = view.findViewById(R.id.expensesRecyclerView)
        emptyStateTextView = view.findViewById(R.id.emptyStateText)
        totalExpenseTextView = view.findViewById(R.id.totalExpenseText)
        budgetTextView = view.findViewById(R.id.budgetText)
        progressBar = view.findViewById(R.id.progressBar)
        progressPercentage = view.findViewById(R.id.progressPercentage)
        spentPercentText = view.findViewById(R.id.spentPercentText)
        limitText = view.findViewById(R.id.limitText)
        
        // Set up RecyclerView with fixed height issue
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.isNestedScrollingEnabled = true

        return view
    }

    override fun onResume() {
        super.onResume()
        loadExpenses()
        updateBudgetInfo()
    }

    private fun loadExpenses() {
        // Get expenses from SharedPreferencesManager
        val expenses = sharedPrefsManager.getExpenses()
        
        // Show empty state if no expenses
        if (expenses.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyStateTextView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyStateTextView.visibility = View.GONE
            
            // Set up adapter with click listener for edit/delete
            adapter = ExpenseAdapter(requireContext(), expenses) { expense ->
                showExpenseOptionsDialog(expense)
            }
            recyclerView.adapter = adapter
        }
    }
    
    private fun updateBudgetInfo() {
        val currency = sharedPrefsManager.getCurrency()
        val totalExpenses = sharedPrefsManager.getCurrentMonthExpenses()
        val budget = sharedPrefsManager.getMonthlyBudget()
        val percentUsed = sharedPrefsManager.getCurrentBudgetUsagePercent()
        
        // Update text views
        totalExpenseTextView.text = "$currency $totalExpenses"
        budgetTextView.text = "Budget: $currency $budget ($percentUsed% used)"
        
        // Update progress bar and related text
        progressBar.progress = percentUsed
        progressPercentage.text = "$percentUsed%"
        spentPercentText.text = "Spent: $percentUsed%"
        limitText.text = "of $currency $budget limit"
        
        // Change colors based on budget status
        if (percentUsed > 90) {
            budgetTextView.setTextColor(requireContext().getColor(R.color.shopping)) // Red
            progressBar.setIndicatorColor(requireContext().getColor(R.color.shopping))
        } else if (percentUsed > 75) {
            budgetTextView.setTextColor(requireContext().getColor(R.color.transport)) // Orange
            progressBar.setIndicatorColor(requireContext().getColor(R.color.transport))
        } else {
            budgetTextView.setTextColor(requireContext().getColor(R.color.food)) // Green
            progressBar.setIndicatorColor(requireContext().getColor(R.color.Blue))
        }
    }
    
    private fun showExpenseOptionsDialog(expense: Expense) {
        val options = arrayOf("Edit", "Delete")
        
        AlertDialog.Builder(requireContext())
            .setTitle("Expense Options")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> editExpense(expense)
                    1 -> deleteExpense(expense)
                }
            }
            .show()
    }
    
    private fun editExpense(expense: Expense) {
        // For now, we'll just implement deletion
        // Full edit functionality would require a separate dialog or fragment
        Snackbar.make(requireView(), "Edit functionality coming soon", Snackbar.LENGTH_SHORT).show()
    }
    
    private fun deleteExpense(expense: Expense) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Expense")
            .setMessage("Are you sure you want to delete this expense?")
            .setPositiveButton("Delete") { _, _ ->
                sharedPrefsManager.deleteExpense(expense)
                Snackbar.make(requireView(), "Expense deleted", Snackbar.LENGTH_SHORT).show()
                loadExpenses()
                updateBudgetInfo()
                
                // Check budget status after deletion
                notificationHelper.checkAndShowBudgetAlertIfNeeded()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}