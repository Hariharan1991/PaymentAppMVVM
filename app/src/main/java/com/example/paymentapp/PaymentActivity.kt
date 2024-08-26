package com.example.paymentapp

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.paymentapp.adapter.CardSpinnerAdapter
import com.example.paymentapp.databinding.ActivityPaymentBinding
import com.example.paymentapp.helper.DBManager
import com.example.paymentapp.viewmodel.PaymentViewModel

class PaymentActivity : AppCompatActivity() {

    lateinit var binding:ActivityPaymentBinding
    lateinit var paymentViewModel: PaymentViewModel
    lateinit var dbManager: DBManager

    private var contactName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        dbManager = DBManager(this)
        dbManager.initDB()
        paymentViewModel = ViewModelProviders.of(this)[PaymentViewModel::class.java]
        contactName = bundle!!.getString("ContactName")!!
        binding.tvContactName.text = "To Pay : " +contactName

        paymentViewModel.gatCardInfo(dbManager)

        paymentViewModel.observeCardInfoList().observe(this){
            val adapter = CardSpinnerAdapter(this,it)
            binding.spinnerCardList.adapter = adapter
        }
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.edAmount.setOnEditorActionListener { v, actionId, event ->
            if(binding.edAmount.text.toString().isEmpty()) {
                Toast.makeText(applicationContext, "Please Enter the Amount", Toast.LENGTH_SHORT)
                    .show()
                false
            }else {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    val intent = Intent(this, QRCodeScannerActivity::class.java)
                    intent.putExtra("ContactName",contactName)
                    intent.putExtra("Amount",binding.edAmount.text.toString())
                    startActivity(intent)
                }
                false
            }
        }
    }
}
