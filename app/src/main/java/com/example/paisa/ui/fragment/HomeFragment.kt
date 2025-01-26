package com.example.paisa.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paisa.adapters.TransactionAdapter
import com.example.paisa.databinding.FragmentHomeBinding
import com.example.paisa.repo.TransactionRepoImpl
import com.example.paisa.ui.activity.AddTransaction
import com.example.paisa.viewModel.TransactionViewModel
import java.util.ArrayList
import java.util.Objects


class HomeFragment : Fragment() {

    lateinit var homeBinding: FragmentHomeBinding
    lateinit var transactionViewModel: TransactionViewModel
    lateinit var adapter: TransactionAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


var repo = TransactionRepoImpl()
        transactionViewModel =TransactionViewModel(repo)

        adapter = TransactionAdapter(requireContext(), ArrayList())

        transactionViewModel.getAllTransaction()
        transactionViewModel.allTransaction.observe(requireActivity()){
            it?.let{
                adapter.updateData(it)
            }
        }

        transactionViewModel.loading.observe(requireActivity()){loading->
            if(loading){
                homeBinding.progressBar.visibility = View.VISIBLE
            }else{
                homeBinding.progressBar.visibility = View.GONE
            }
        }

        homeBinding.recyclerView.adapter = adapter
        homeBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())



        homeBinding.fab.setOnClickListener {
            var intent = Intent(requireContext(), AddTransaction::class.java)
            startActivity(intent)
        }
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
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
    }

}