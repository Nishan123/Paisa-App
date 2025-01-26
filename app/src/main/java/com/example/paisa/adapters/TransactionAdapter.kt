package com.example.paisa.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paisa.R
import com.example.paisa.models.TransactionModel
import org.w3c.dom.Text

class TransactionAdapter(val context: Context,  var data: ArrayList<TransactionModel>):RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>(){
    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.titleText)
        val desc: TextView = itemView.findViewById(R.id.descText)
        val amount: TextView = itemView.findViewById(R.id.priceText)
        val date:TextView = itemView.findViewById(R.id.date)
        val time:TextView= itemView.findViewById(R.id.time)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemView :View = LayoutInflater.from(context).inflate(R.layout.transaction_list, parent, false)
        return TransactionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.title.text = data[position].title
        holder.desc.text=data[position].description
        holder.amount.text= "${data[position].amount}"
        holder.date.text = data[position].transactionDate
        holder.time.text = data[position].transactionTime
    }

    override fun getItemCount(): Int {
        return  data.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateData(transactions: List<TransactionModel>){
        data.clear()
        data.addAll(transactions)
        notifyDataSetChanged()
    }

    fun getTransactionId(position:Int):String{
        return data[position].transactionId
    }

}