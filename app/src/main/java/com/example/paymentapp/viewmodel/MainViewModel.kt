package com.example.paymentapp.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.paymentapp.bo.CardBO
import com.example.paymentapp.bo.RecentMoneySendBO
import com.example.paymentapp.bo.TodayTransactionBO
import com.example.paymentapp.helper.DBManager

class MainViewModel:BaseViewModel() {

    var transferList = MutableLiveData<ArrayList<TodayTransactionBO>>()
    var recentMoneySendList = MutableLiveData<ArrayList<RecentMoneySendBO>>()

    fun addTransferList(dbManager: DBManager){

        if(!dbManager.checkTransactionDataIsAvailable()) {
            val todayTransferList = ArrayList<TodayTransactionBO>()
            var todayTransactionBO = TodayTransactionBO("Person 1", "21.11 Transfer", "$2100")
            todayTransferList.add(todayTransactionBO)
            todayTransactionBO = TodayTransactionBO("Burger King", "17.11 Restaurant", "$120")
            todayTransferList.add(todayTransactionBO)
            todayTransactionBO = TodayTransactionBO("Pizza Hut", "13.23 Restaurant", "$80")
            todayTransferList.add(todayTransactionBO)
            todayTransactionBO = TodayTransactionBO("Sachin Tendulkar", "10.20 Transfer", "$111")
            todayTransferList.add(todayTransactionBO)
            todayTransactionBO = TodayTransactionBO("MSD", "09.00 Transfer", "$123")
            todayTransferList.add(todayTransactionBO)
            todayTransactionBO = TodayTransactionBO("Ricky Ponting", "17.11 Transfer", "$345")
            todayTransferList.add(todayTransactionBO)
            todayTransactionBO = TodayTransactionBO("Lionel Messi", "02.00 Transfer", "$234")
            todayTransferList.add(todayTransactionBO)
            todayTransactionBO = TodayTransactionBO("Dominos", "01.11 Restaurant", "$980")
            todayTransferList.add(todayTransactionBO)
            todayTransactionBO = TodayTransactionBO("KFC", "12.00 Restaurant", "$1000")
            todayTransferList.add(todayTransactionBO)

            dbManager.insertTransactionData(todayTransferList)
        }
        //transferList.value = todayTransferList

    }

    fun addCardInfo(dbManager: DBManager){
        if(!dbManager.checkCardDataIsAvailable()) {
            val cardList = ArrayList<CardBO>()
            cardList.add(CardBO("****2334", "12/32", "$2312"))
            cardList.add(CardBO("****1244", "10/30", "$123"))
            cardList.add(CardBO("****4145", "01/28", "$2123"))
            cardList.add(CardBO("****3225", "08/29", "$8797"))
            dbManager.insertCardData(cardList)
        }
    }

    fun getTransactionInfo(dbManager: DBManager){
        transferList.value = dbManager.getTransactionData()
    }

    fun getRecentMoneySendList(){
        val recentMoneySendDetails = ArrayList<RecentMoneySendBO>()
        recentMoneySendDetails.add(RecentMoneySendBO("Micheal"))
        recentMoneySendDetails.add(RecentMoneySendBO("Mitchell"))
        recentMoneySendDetails.add(RecentMoneySendBO("Dhoni"))
        recentMoneySendDetails.add(RecentMoneySendBO("Kohli"))
        recentMoneySendDetails.add(RecentMoneySendBO("Sharma"))
        recentMoneySendDetails.add(RecentMoneySendBO("Karthik"))

        recentMoneySendList.value = recentMoneySendDetails
    }

    fun observeTransferList():MutableLiveData<ArrayList<TodayTransactionBO>>{
        return transferList
    }

    fun observeSendMoneyList():MutableLiveData<ArrayList<RecentMoneySendBO>>{
        return recentMoneySendList
    }

}