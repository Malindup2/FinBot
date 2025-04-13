package com.example.finbot.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finbot.R
import com.example.finbot.adapter.ExpenseAdapter
import com.example.finbot.model.Expense

class homeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.home, container, false)

        // Initialize RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.expensesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Sample data
        val expenses = listOf(
            Expense(R.drawable.food, "Food", "05-02-2023", "19:22", "100.52", 1),
            Expense(R.drawable.shopping, "Shopping", "05-02-2023", "20:06", "400.50", 2),
            Expense(R.drawable.transport, "Trips", "05-02-2023", "18:43", "190.90", 3),
            Expense(R.drawable.transport, "Transport", "05-02-2023", "20:39", "200.53", 3),
            Expense(R.drawable.health, "Health & Beauty", "05-02-2023", "15:59", "140.53", 4),
        )

        // Set up adapter
        val adapter = ExpenseAdapter(requireContext(), expenses)
        recyclerView.adapter = adapter

        return view
    }
}