package com.example.paymentapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paymentapp.adapter.CardInfoAdapter
import com.example.paymentapp.adapter.RecentViewModelAdapter
import com.example.paymentapp.adapter.TodayTransactionListAdapter
import com.example.paymentapp.databinding.ActivityMainBinding
import com.example.paymentapp.helper.DBManager
import com.example.paymentapp.interfacee.ItemClick
import com.example.paymentapp.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(),ItemClick {

    lateinit var binding:ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var dbManager:DBManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS,Manifest.permission.CAMERA), 100);
        }

        dbManager = DBManager(this)
        dbManager.initDB()

        mainViewModel = ViewModelProviders.of(this)[MainViewModel::class.java]

        binding.rvContactList.layoutManager = LinearLayoutManager(this)
        binding.rvCardDetails.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.sendMoneyLayout.rvRecentViewList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        lifecycleScope.launch {
            val insertDataAwait = async(Dispatchers.IO) {
                mainViewModel.addCardInfo(dbManager)
                mainViewModel.addTransferList(dbManager)
            }
            insertDataAwait.await()
            withContext(Dispatchers.Main){
                mainViewModel.getRecentMoneySendList()
                mainViewModel.gatCardInfo(dbManager)
                mainViewModel.getTransactionInfo(dbManager)
            }
        }

        mainViewModel.observeTransferList().observe(this) {
            val todayTransactionListAdapter = TodayTransactionListAdapter(this,it,this)
            binding.rvContactList.adapter = todayTransactionListAdapter
        }

        mainViewModel.observeSendMoneyList().observe(this) {
            val recentListAdapter = RecentViewModelAdapter(it,this)
            binding.sendMoneyLayout.rvRecentViewList.adapter = recentListAdapter
        }

        mainViewModel.observeCardInfoList().observe(this) {
            val cardListAdapter = CardInfoAdapter(it)
            binding.rvCardDetails.adapter = cardListAdapter
        }

        binding.sendMoneyLayout.profileImage.setOnClickListener {
            val intent = Intent(this,ContactListActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    override fun onItemClick(contactName: String?) {
        val intent = Intent(this,PaymentActivity::class.java)
        intent.putExtra("ContactName",contactName)
        startActivity(intent)
    }
}