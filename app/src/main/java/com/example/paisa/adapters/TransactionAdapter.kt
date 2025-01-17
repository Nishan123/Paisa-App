package com.example.paisa.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paisa.R
import com.example.paisa.models.TransactionModel

class TransactionAdapter(private val transactionList: ArrayList<TransactionModel>) :
    RecyclerView.Adapter<TransactionAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TransactionAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.transaction_list, parent, false)
        return  MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TransactionAdapter.MyViewHolder, position: Int) {
       val transactionModel:TransactionModel = transactionList[position]
        holder.title.text = transactionModel.title
        holder.description.text = transactionModel.description
        holder.price.text = transactionModel.amount.toString()

    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.titleText)
        val description: TextView = itemView.findViewById(R.id.descText)
        val price: TextView = itemView.findViewById(R.id.priceText)
    }
}