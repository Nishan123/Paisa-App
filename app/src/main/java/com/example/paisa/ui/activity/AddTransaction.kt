package com.example.paisa.ui.activity

import android.os.Bundle
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
import java.time.LocalDateTime
import kotlin.time.Duration.Companion.milliseconds

class AddTransaction : AppCompatActivity() {
    lateinit var binding: ActivityAddTransactionBinding
    lateinit var transactionViewModel: TransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transactionViewModel = TransactionViewModel(TransactionRepoImpl())

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
                val transactionModel = TransactionModel(
                    "",
                    title,
                    description,
                    amount,
                    false,
                    "",
                    LocalDateTime.now(),
                    LocalDateTime.now(),
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