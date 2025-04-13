package com.example.finbot.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import com.example.finbot.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*

class statFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout
        val view = inflater.inflate(R.layout.stat, container, false)

        // Initialize Pie Chart
        val pieChart = view.findViewById<PieChart>(R.id.pieChart)
        setupPieChart(pieChart)

        // Initialize Line Chart
        val lineChart = view.findViewById<LineChart>(R.id.lineChart)
        setupLineChart(lineChart)

        return view
    }

    private fun setupPieChart(pieChart: PieChart) {
        // Create sample data entries
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(35f, "Food"))
        entries.add(PieEntry(25f, "Shopping"))
        entries.add(PieEntry(20f, "Transport"))
        entries.add(PieEntry(10f, "Health"))
        entries.add(PieEntry(10f, "Utility"))

        // Define colors for each category using resources
        val colors = listOf(
            getColor(requireContext(), R.color.food),
            getColor(requireContext(), R.color.shopping),
            getColor(requireContext(), R.color.transport),
            getColor(requireContext(), R.color.health),
            getColor(requireContext(), R.color.Blue)
        )

        // Create a dataset
        val dataSet = PieDataSet(entries, "Expense Distribution")
        dataSet.colors = colors
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueTextSize = 12f

        // Create a PieData object
        val pieData = PieData(dataSet)

        // Customize the Pie Chart
        pieChart.data = pieData
        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = false // Disable default legend
        pieChart.animateY(1000) // Add animation
        pieChart.invalidate() // Refresh the chart

        // Dynamically generate legend items
        val legendContainer = view?.findViewById<LinearLayout>(R.id.legendContainer)
        legendContainer?.removeAllViews() // Clear existing views
        legendContainer?.orientation = LinearLayout.HORIZONTAL // Set horizontal orientation

        entries.forEachIndexed { index, entry ->
            val legendItem = createLegendItem(colors[index], entry.label, "${entry.value.toInt()}%")

            // Add margins for better spacing
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(0, 0, 16, 0) // Add right margin
            legendItem.layoutParams = layoutParams

            legendContainer?.addView(legendItem)
        }
    }

    private fun createLegendItem(color: Int, label: String, percentage: String): View {
        // Inflate a layout for the legend item
        val inflater = LayoutInflater.from(requireContext())
        val legendItem = inflater.inflate(R.layout.legend_item, null) as LinearLayout

        // Set the color indicator
        val colorView = legendItem.findViewById<View>(R.id.legendColor)
        colorView.setBackgroundColor(color)

        // Set the label
        val labelView = legendItem.findViewById<TextView>(R.id.legendLabel)
        labelView.text = label

        // Set the percentage
        val percentageView = legendItem.findViewById<TextView>(R.id.legendPercentage)
        percentageView.text = percentage

        return legendItem
    }

    private fun setupLineChart(lineChart: LineChart) {
        // Create sample data entries
        val entries = ArrayList<Entry>()
        entries.add(Entry(0f, 200f)) // Day 0
        entries.add(Entry(1f, 400f)) // Day 1
        entries.add(Entry(2f, 300f)) // Day 2
        entries.add(Entry(3f, 520f)) // Day 3
        entries.add(Entry(4f, 600f)) // Day 4
        entries.add(Entry(5f, 300f)) // Day 5
        entries.add(Entry(6f, 870f)) // Day 6
        entries.add(Entry(7f, 100f)) // Day 7
        entries.add(Entry(8f, 10230f)) // Day 8
        entries.add(Entry(9f, 1102f)) // Day 9
        entries.add(Entry(10f, 1220f)) // Day 10
        entries.add(Entry(11f, 1320f)) // Day 11
        entries.add(Entry(12f, 2400f)) // Day 12
        entries.add(Entry(13f, 6500f)) // Day 13
        entries.add(Entry(14f, 1230f)) // Day 14
        entries.add(Entry(15f, 1240f)) // Day 15

        // Create a dataset
        val dataSet = LineDataSet(entries, "Spending Trends")
        dataSet.color = getColor(requireContext(), R.color.Blue)
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueTextSize = 12f
        dataSet.setCircleColor(getColor(requireContext(), R.color.Blue))
        dataSet.circleRadius = 5f

        // Create a LineData object
        val lineData = LineData(dataSet)

        // Customize the Line Chart
        lineChart.data = lineData
        lineChart.description.isEnabled = false
        lineChart.axisLeft.axisMinimum = 0f
        lineChart.xAxis.granularity = 1f
        lineChart.xAxis.labelCount = 7
        lineChart.legend.isEnabled = true
        lineChart.animateX(1000) // Add animation
        lineChart.invalidate() // Refresh the chart
    }
}