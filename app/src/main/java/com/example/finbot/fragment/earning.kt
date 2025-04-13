import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finbot.R
import com.example.finbot.adapter.EarningsAdapter
import com.example.finbot.model.Earning
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class earningFragment : Fragment() {

    private lateinit var earningsRecyclerView: RecyclerView
    private lateinit var totalEarningsText: TextView
    private lateinit var totalSavingsText: TextView

    private val earningsList = mutableListOf<Earning>()
    private lateinit var earningsAdapter: EarningsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.earning, container, false)

        // Initialize views
        earningsRecyclerView = view.findViewById(R.id.earningsRecyclerView)
        totalEarningsText = view.findViewById(R.id.totalEarningsText)
        totalSavingsText = view.findViewById(R.id.totalSavingsText)

        // Set up RecyclerView
        earningsAdapter = EarningsAdapter(earningsList, ::onEditEarning, ::onDeleteEarning)
        earningsRecyclerView.adapter = earningsAdapter
        earningsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Add Earning Button
        view.findViewById<Button>(R.id.addEarningButton).setOnClickListener {
            showAddEarningDialog()
        }

        return view
    }

    private fun showAddEarningDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_earning, null)
        val categoryInput = dialogView.findViewById<EditText>(R.id.categoryInput)
        val amountInput = dialogView.findViewById<EditText>(R.id.amountInput)
        val dateInput = dialogView.findViewById<TextView>(R.id.dateInput)


        // Set default date
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        dateInput.text = currentDate

        // Show dialog
        AlertDialog.Builder(requireContext(),R.style.CustomDialogTheme)
            .setTitle("Add Earning")
            .setView(dialogView)
            .setPositiveButton("Submit") { _, _ ->
                val category = categoryInput.text.toString().trim()
                val amount = amountInput.text.toString().toDoubleOrNull()
                val date = dateInput.text.toString()

                if (category.isNotEmpty() && amount != null) {
                    addEarning(category, amount, date)
                    Toast.makeText(requireContext(), "Earning added successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Please enter valid details.", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun addEarning(category: String, amount: Double, date: String) {
        val newEarning = Earning(earningsList.size + 1, category, amount, date)
        earningsList.add(newEarning)

        // Update total earnings and savings
        updateTotals()

        // Notify adapter
        earningsAdapter.notifyItemInserted(earningsList.size - 1)
    }

    private fun updateTotals() {
        val totalEarnings = earningsList.sumOf { it.amount }
        totalEarningsText.text = "LKR ${String.format("%.2f", totalEarnings)}"

        // Assuming savings is 20% of earnings
        val totalSavings = totalEarnings * 0.2
        totalSavingsText.text = "LKR ${String.format("%.2f", totalSavings)}"
    }

    private fun onEditEarning(position: Int) {
        // Implement edit functionality
    }

    private fun onDeleteEarning(position: Int) {
        earningsList.removeAt(position)
        earningsAdapter.notifyItemRemoved(position)
        updateTotals()
    }
}