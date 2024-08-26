package com.example.paymentapp.helper

import android.content.ContentValues
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.example.paymentapp.bo.CardBO
import com.example.paymentapp.bo.TodayTransactionBO


class DBManager(private val context: Context) {
    private var dbHelper: DatabaseHelper? = null
    private var database: SQLiteDatabase? = null

    fun initDB(){
        dbHelper = DatabaseHelper(context)
    }
    @Throws(SQLException::class)
    fun open(): DBManager {
        database = dbHelper!!.writableDatabase
        return this
    }

    fun close() {
        dbHelper!!.close()
    }

    fun insertTransactionData(transferList:ArrayList<TodayTransactionBO>) {
        open()
        for(transfer in transferList) {
            val contentValue = ContentValues()
            contentValue.put(DatabaseHelper.TRANSFER_NAME, transfer.transferName)
            contentValue.put(DatabaseHelper.TRANSFER_TIME, transfer.transferTime)
            contentValue.put(DatabaseHelper.TRANSFER_AMOUNT, transfer.transferAmount)
            database!!.insert(DatabaseHelper.TRANSACTION_INFO_TABLE_NAME, null, contentValue)
        }
        close()
    }

    fun insertCardData(cardInfo:ArrayList<CardBO>) {
        open()
        for(card in cardInfo) {
            val contentValue = ContentValues()
            contentValue.put(DatabaseHelper.CARD_NO, card.cardNo)
            contentValue.put(DatabaseHelper.EXPIRY_DATE, card.expDate)
            contentValue.put(DatabaseHelper.CARD_AMOUNT, card.cardAmount)
            database!!.insert(DatabaseHelper.CARD_INFO_TABLE_NAME, null, contentValue)
        }
        close()
    }


    fun getTransactionData():ArrayList<TodayTransactionBO> {
        val transactionList = ArrayList<TodayTransactionBO>()
        try {
            database = dbHelper!!.readableDatabase

            val columns =
                arrayOf<String>(DatabaseHelper.TRANSFER_NAME, DatabaseHelper.TRANSFER_TIME, DatabaseHelper.TRANSFER_AMOUNT)
            val cursor = database!!.query(DatabaseHelper.TRANSACTION_INFO_TABLE_NAME, columns, null, null, null, null, null)

            while (cursor.moveToNext()){
                val transactionBO = TodayTransactionBO(cursor.getString(0),cursor.getString(1),cursor.getString(2))
                transactionList.add(transactionBO)
            }

        }catch (ex:Exception){
            ex.printStackTrace()
        }
        return transactionList
    }

    fun getCardInfoData():ArrayList<CardBO> {
        val cardInfoList = ArrayList<CardBO>()
        try {
            database = dbHelper!!.readableDatabase

            val columns =
                arrayOf<String>(DatabaseHelper.CARD_NO, DatabaseHelper.EXPIRY_DATE, DatabaseHelper.CARD_AMOUNT)
            val cursor = database!!.query(DatabaseHelper.CARD_INFO_TABLE_NAME, columns, null, null, null, null, null)

            while (cursor.moveToNext()){
                val cardBO = CardBO(cursor.getString(0),cursor.getString(1),cursor.getString(2))
                cardInfoList.add(cardBO)
            }

        }catch (ex:Exception){
            ex.printStackTrace()
        }
        return cardInfoList
    }

    fun checkTransactionDataIsAvailable():Boolean{
        try {
            database = dbHelper!!.readableDatabase

            val columns =
                arrayOf<String>(DatabaseHelper.TRANSFER_TIME, DatabaseHelper.TRANSFER_NAME, DatabaseHelper.TRANSFER_AMOUNT)
            val cursor = database!!.query(DatabaseHelper.TRANSACTION_INFO_TABLE_NAME, columns, null, null, null, null, null)
            if(cursor.count > 0)
                return true
        }catch (ex:Exception){
            ex.printStackTrace()
        }

        return false
    }

    fun checkCardDataIsAvailable():Boolean{
        try {
            database = dbHelper!!.readableDatabase

            val columns =
                arrayOf<String>(DatabaseHelper.CARD_NO, DatabaseHelper.EXPIRY_DATE, DatabaseHelper.CARD_AMOUNT)
            val cursor = database!!.query(DatabaseHelper.CARD_INFO_TABLE_NAME, columns, null, null, null, null, null)
            if(cursor.count > 0)
                return true
        }catch (ex:Exception){
            ex.printStackTrace()
        }

        return false
    }



    fun delete(tableName:String) {
        database!!.delete(DatabaseHelper.CARD_INFO_TABLE_NAME, null, null)
        database!!.delete(DatabaseHelper.TRANSACTION_INFO_TABLE_NAME, null, null)
    }
}