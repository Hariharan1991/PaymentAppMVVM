package com.example.paymentapp.viewmodel

import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.MutableLiveData
import com.example.paymentapp.bo.ContactBO

class ContactViewModel:BaseViewModel() {

    private var contactList = MutableLiveData<ArrayList<ContactBO>>()

    fun getContactsList(context: Context){
        val userContactList = ArrayList<ContactBO>()
        val contentResolver = context.contentResolver
        val cursor= contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null)
        while (cursor!!.moveToNext()){
            val dispName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
            val contactBO = ContactBO(dispName,"1234 5678 9012")
            userContactList.add(contactBO)
        }
        contactList.value = userContactList

    }

    fun observeContactList(): MutableLiveData<ArrayList<ContactBO>> {
        return contactList
    }
}