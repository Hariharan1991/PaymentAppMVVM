package com.example.paymentapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.paymentapp.R
import com.example.paymentapp.bo.CardBO

class CardSpinnerAdapter(var context:Context,var contactList:ArrayList<CardBO>):BaseAdapter() {

    override fun getCount(): Int {
        return contactList.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.spinner_item,null)

        val tvCardNo = view.findViewById<TextView>(R.id.tvCardNo)
        tvCardNo.text = contactList[position].cardNo

        return view
    }
}