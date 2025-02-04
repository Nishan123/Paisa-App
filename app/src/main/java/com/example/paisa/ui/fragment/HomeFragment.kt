package com.example.paisa.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paisa.adapters.TransactionAdapter
import com.example.paisa.databinding.FragmentHomeBinding
import com.example.paisa.repo.TransactionRepo
import com.example.paisa.repo.TransactionRepoImpl
import com.example.paisa.ui.activity.AddTransaction
import com.example.paisa.viewModel.TransactionViewModel
import java.util.ArrayList
import java.util.Objects


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var transactionRepo: TransactionRepo
    lateinit var transactionViewModel: TransactionViewModel
    lateinit var adapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transactionRepo = TransactionRepoImpl()
        updateBalanceCard()

        var repo = TransactionRepoImpl()
        transactionViewModel = TransactionViewModel(repo)

        adapter = TransactionAdapter(requireContext(), ArrayList())
        transactionViewModel.getAllTransaction()
        transactionViewModel.allTransaction.observe(requireActivity()) { transactions ->
            transactions?.let {
                adapter.updateData(it)
            }

        }

        transactionViewModel.empty.observe(requireActivity()) { empty ->
            if (empty) {
                binding.noTransactionText.visibility = View.VISIBLE
            } else {
                binding.noTransactionText.visibility = View.GONE
            }
        }

        transactionViewModel.loading.observe(requireActivity()) { loading ->
            // Only show progress bar if loading and no transactions yet
            if (loading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.fab.setOnClickListener {
            var intent = Intent(requireContext(), AddTransaction::class.java)
            startActivity(intent)
        }
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var id = adapter
            }

        })

        // Setup chip group listeners
        binding.all.isChecked = true  // Default selection
        
        binding.apply {
            all.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                transactionViewModel.getAllTransaction()
            }

            income.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                transactionViewModel.getParticularTransaction("Income")
            }

            expenses.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                transactionViewModel.getParticularTransaction("Expenses")
            }

            transfer.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                transactionViewModel.getParticularTransaction("Transfer")
            }
        }
    }

    private fun updateBalanceCard() {
        transactionRepo.getCalculations { availableBalance, totalIncome, totalExpenditure ->
            binding.availableBalance.text = "Rs. $availableBalance"
            binding.incomeAmount.text = totalIncome.toString()
            binding.expenditureAmount.text = totalExpenditure.toString()
        }
    }
}