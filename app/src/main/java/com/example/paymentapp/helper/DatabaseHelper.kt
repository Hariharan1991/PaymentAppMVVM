package com.example.paymentapp.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_CARD_INFO_TABLE)
        db.execSQL(CREATE_TRANSFER_INFO_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + CARD_INFO_TABLE_NAME)
        db.execSQL("DROP TABLE IF EXISTS " + TRANSACTION_INFO_TABLE_NAME)
        onCreate(db)
    }

    companion object {
        // Table Name
        const val CARD_INFO_TABLE_NAME = "CardInfo"
        const val TRANSACTION_INFO_TABLE_NAME = "TransactionInfo"

        const val CARD_NO = "CardNo"
        const val EXPIRY_DATE = "ExpDate"
        const val CARD_AMOUNT = "CardAmount"

        const val TRANSFER_NAME = "TransferName"
        const val TRANSFER_TIME = "TransferTime"
        const val TRANSFER_AMOUNT = "TransferAmount"


        const val DB_NAME = "Payment.DB"

        const val DB_VERSION = 1

        private const val CREATE_CARD_INFO_TABLE = ("create table if not exists " + CARD_INFO_TABLE_NAME + "("
                + CARD_NO + " TEXT, " + CARD_AMOUNT + " TEXT, "+ EXPIRY_DATE + " TEXT);")

        private const val CREATE_TRANSFER_INFO_TABLE = ("create table if not exists " + TRANSACTION_INFO_TABLE_NAME + "("
                + TRANSFER_NAME + " TEXT, " + TRANSFER_TIME + " TEXT, "+ TRANSFER_AMOUNT + " TEXT);")
    }
}