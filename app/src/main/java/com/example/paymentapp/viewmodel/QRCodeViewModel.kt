package com.example.paymentapp.viewmodel

import com.example.paymentapp.bo.TodayTransactionBO
import com.example.paymentapp.helper.DBManager

class QRCodeViewModel:BaseViewModel() {

    fun insertTransactionDetails(dbManager: DBManager,contactName:String,amount:String){
        var transactionList = ArrayList<TodayTransactionBO>()
        val todayTransactionBO = TodayTransactionBO(contactName,"5.35 Transfer", "$$amount")
        transactionList.add(todayTransactionBO)

        dbManager.insertTransactionData(transactionList)
    }
}