package com.example.paymentapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paymentapp.bo.CardBO
import com.example.paymentapp.databinding.CardViewListBinding

class CardInfoAdapter(private val cardList:ArrayList<CardBO>): RecyclerView.Adapter<CardInfoAdapter.CardInfoViewHolder>() {

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardInfoViewHolder {
        val binding = CardViewListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CardInfoViewHolder(binding)

    }
    override fun onBindViewHolder(holder: CardInfoViewHolder, position: Int) {
        holder.bind(cardList[position])
    }

    class CardInfoViewHolder(var binding:CardViewListBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(cardBO: CardBO){
            binding.tvCardNo.text = cardBO.cardNo
            binding.tvAmount.text = cardBO.cardAmount
            binding.tvExpDate.text = cardBO.expDate

        }
    }
}