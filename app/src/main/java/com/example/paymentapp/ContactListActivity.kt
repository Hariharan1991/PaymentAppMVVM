package com.example.paymentapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paymentapp.adapter.ContactListAdapter
import com.example.paymentapp.bo.ContactBO
import com.example.paymentapp.databinding.ActivityContactListBinding
import com.example.paymentapp.interfacee.ContactInterface
import com.example.paymentapp.viewmodel.ContactViewModel


class ContactListActivity : AppCompatActivity(),ContactInterface {

    lateinit var binding:ActivityContactListBinding
    lateinit var contactViewModel: ContactViewModel
    lateinit var contactList: ArrayList<ContactBO>
    lateinit var contactListAdapter:ContactListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contactViewModel = ViewModelProviders.of(this)[ContactViewModel::class.java]
        contactViewModel.getContactsList(this)

        binding.rvContactList.layoutManager = LinearLayoutManager(this)
        contactViewModel.observeContactList().observe(this){
            contactList = it
            contactListAdapter = ContactListAdapter(contactList,this)
            binding.rvContactList.adapter = contactListAdapter
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                contactListAdapter.filter.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })

    }

    override fun getContactName(contactBO: ContactBO) {
        val intent = Intent(this,PaymentActivity::class.java)
        intent.putExtra("ContactName",contactBO.contactName)
        startActivity(intent)
    }
}