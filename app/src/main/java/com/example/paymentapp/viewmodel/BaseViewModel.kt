package com.example.paymentapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.paymentapp.bo.CardBO
import com.example.paymentapp.bo.ContactBO
import com.example.paymentapp.helper.DBManager


open class BaseViewModel:ViewModel() {


    var cardList = MutableLiveData<ArrayList<CardBO>>()

    fun gatCardInfo(dbManager: DBManager){
        cardList.value = dbManager.getCardInfoData()
    }

    fun observeCardInfoList():MutableLiveData<ArrayList<CardBO>>{
        return cardList
    }
}