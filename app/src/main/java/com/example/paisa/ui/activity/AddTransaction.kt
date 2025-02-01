package com.example.paisa.ui.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.paisa.R
import com.example.paisa.databinding.ActivityAddTransactionBinding
import com.example.paisa.models.TransactionModel
import com.example.paisa.repo.TransactionRepoImpl
import com.example.paisa.viewModel.TransactionViewModel
import java.util.Calendar


class AddTransaction : AppCompatActivity() {
    lateinit var binding: ActivityAddTransactionBinding
    lateinit var transactionViewModel: TransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transactionViewModel = TransactionViewModel(TransactionRepoImpl())
        val transactionTypes = listOf("Income", "Expenses", "Transfer")
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            transactionTypes
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Set the adapter to the Spinner
        binding.spinner.adapter = adapter

        val calendar = Calendar.getInstance()
        val tYear = calendar.get(Calendar.YEAR)
        val tMonth = calendar.get(Calendar.MONTH)
        val tDay = calendar.get(Calendar.DAY_OF_MONTH)
        var transactionDate: String = "${tMonth + 1}/$tDay/$tYear"
        print("${tMonth + 1}/$tDay/$tYear")
        binding.datePickerButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                this@AddTransaction,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Update button text with selected date
                    binding.datePickerButton.text =
                        "${selectedMonth + 1} /$selectedDay/$selectedYear"

                    transactionDate = "${selectedMonth + 1}/$selectedDay/$selectedYear"

                },
                year,
                month,
                day
            )
            datePicker.show()
        }

        val tHour = calendar.get(Calendar.HOUR_OF_DAY)
        val tMin: Int = calendar.get(Calendar.MINUTE)
        var transactionTime: String = "$tHour : $tMin"
        binding.timePickerButton.setOnClickListener {
            val hours = Calendar.HOUR
            val minutes = Calendar.MINUTE
            val timePicker = TimePickerDialog(this@AddTransaction, { _, hour, minute ->
                binding.timePickerButton.text = "$hours : $minutes"
                transactionTime = "$hours : $minutes"
            }, hours, minutes, false)
            timePicker.show()
        }

        binding.addTransactionButton.setOnClickListener {


            if (binding.title.text?.toString().orEmpty().isEmpty() ||
                binding.description.text?.toString().orEmpty().isEmpty() ||
                binding.amount.text?.toString().orEmpty().isEmpty()
            ) {
                Toast.makeText(this@AddTransaction, "No field cannot be empty", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val title = binding.title.text.toString()
                val description = binding.description.text.toString()
                val amount = binding.amount.text.toString().toInt()
                val transactionType = binding.spinner.selectedItem.toString()
                val transactionModel = TransactionModel(
                    "",
                    title,
                    description,
                    amount,
                    transactionType,
                    false,
                    "",
                    transactionDate,
                    transactionTime,
                    transactionDate,
                    transactionTime,
                )


                transactionViewModel.addTransaction(transactionModel) { success, message ->
                    if (success) {
                        Toast.makeText(this@AddTransaction, message, Toast.LENGTH_SHORT).show()
                        binding.title.text?.clear()
                        binding.description.text?.clear()
                        binding.amount.text?.clear()


                    } else {
                        Toast.makeText(this@AddTransaction, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}