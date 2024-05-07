package com.blogspot.softwareengineerrohan.naarirakshak.roomDb
import androidx.lifecycle.LiveData

class ContactRepo(val dao : ContactDao) {

    fun getAllContacts(): LiveData<List<Contact>> = dao.getAllContacts()
    fun insertContact(contact: Contact) = dao.insertContact(contact)
    fun deleteContact(contact: Contact) = dao.deleteContact(contact)
    fun updateContact(contact: Contact) = dao.updateContact(contact)


}