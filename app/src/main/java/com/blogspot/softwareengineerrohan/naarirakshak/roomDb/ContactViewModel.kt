package com.blogspot.softwareengineerrohan.naarirakshak.roomDb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ContactViewModel(application: Application):AndroidViewModel(application) {

    val repo : ContactRepo
    init {
        val dao = ContactDatabase.getDatabase(application).myContactDao()
        repo = ContactRepo(dao)
    }



    fun addContact(contact: Contact) = viewModelScope.launch {
        repo.insertContact(contact)
    }
    fun updateContact(contact: Contact) = viewModelScope.launch {
        repo.updateContact(contact)
    }
    fun deleteContact(contact: Contact) = viewModelScope.launch {
        repo.deleteContact(contact)
    }
    fun getAllContacts() : LiveData<List<Contact>> = repo.getAllContacts()



}