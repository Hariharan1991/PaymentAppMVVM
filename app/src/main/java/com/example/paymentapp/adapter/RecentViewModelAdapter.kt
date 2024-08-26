package com.example.paymentapp.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paymentapp.bo.RecentMoneySendBO
import com.example.paymentapp.databinding.RecentViewListBinding
import com.example.paymentapp.databinding.TodayTransactionListBinding
import com.example.paymentapp.interfacee.ItemClick

class RecentViewModelAdapter(private val recentList:ArrayList<RecentMoneySendBO>,var itemClick:ItemClick) : RecyclerView.Adapter<RecentViewModelAdapter.RecentViewModelViewHolder>(){

    override fun getItemCount(): Int {
        return recentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewModelViewHolder {
        val binding = RecentViewListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecentViewModelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentViewModelViewHolder, position: Int) {
        holder.bind(recentList[position])
        holder.itemView.setOnClickListener {
            itemClick.onItemClick(recentList[position].name)
        }
    }

    class RecentViewModelViewHolder(private val binding: RecentViewListBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(recentListBO:RecentMoneySendBO){
            binding.tvName.text = recentListBO.name
        }
    }
}