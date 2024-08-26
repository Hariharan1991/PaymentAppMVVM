package com.example.paymentapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paymentapp.bo.ContactBO
import com.example.paymentapp.bo.TodayTransactionBO
import com.example.paymentapp.databinding.TodayTransactionListBinding
import com.example.paymentapp.interfacee.ItemClick

class TodayTransactionListAdapter(var context:Context, var contactList:ArrayList<TodayTransactionBO>,var itemClick: ItemClick):RecyclerView.Adapter<TodayTransactionListAdapter.TodayTransactionListViewHolder>() {

    override fun getItemCount(): Int {
        return contactList.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayTransactionListViewHolder {
        val binding = TodayTransactionListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TodayTransactionListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodayTransactionListViewHolder, position: Int) {
        holder.bind(context,contactList[position])

        holder.itemView.setOnClickListener {
            itemClick.onItemClick(contactList[position].transferName)
        }
    }

    class TodayTransactionListViewHolder(var binding:TodayTransactionListBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, values: TodayTransactionBO) {
            binding.tvName.text = values.transferName
            binding.tvTransferTime.text = values.transferTime
            binding.tvAmount.text = values.transferAmount
        }
    }
}