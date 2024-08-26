package com.example.paymentapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.paymentapp.bo.ContactBO
import com.example.paymentapp.databinding.ContactListBinding
import com.example.paymentapp.interfacee.ContactInterface
import java.util.Locale


class ContactListAdapter(var contactList:ArrayList<ContactBO>,var contactInterface: ContactInterface):RecyclerView.Adapter<ContactListAdapter.ContactListViewHolder>(),Filterable {

    var nameList:ArrayList<ContactBO>?=null

    init {
        this.nameList = contactList
    }
    override fun getItemCount(): Int {
        return contactList.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListViewHolder {
        val binding = ContactListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ContactListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactListViewHolder, position: Int) {
        holder.bind(contactList[position])

        holder.itemView.setOnClickListener {
            contactInterface.getContactName(contactList[position])
        }
    }


    class ContactListViewHolder(private val binding:ContactListBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(contactBO: ContactBO){
            binding.tvName.text = contactBO.contactName
            binding.tvTransferTime.text = contactBO.accNo
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val charSequenceString = constraint.toString()
                if (charSequenceString.isEmpty()) {
                    contactList = nameList!!
                } else {
                    val filteredList: ArrayList<ContactBO> = ArrayList()
                    for (name in nameList!!) {
                        if (name.contactName.toLowerCase()
                                .contains(charSequenceString.lowercase(Locale.getDefault()))
                        ) {
                            filteredList.add(name)
                        }
                        contactList = filteredList
                    }
                }
                val results = FilterResults()
                results.values = contactList
                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                contactList = results.values as ArrayList<ContactBO>
                notifyDataSetChanged()
            }
        }
    }

}