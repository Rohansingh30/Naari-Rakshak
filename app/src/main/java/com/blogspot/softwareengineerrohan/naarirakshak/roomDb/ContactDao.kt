package com.blogspot.softwareengineerrohan.naarirakshak.roomDb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
@Dao
interface ContactDao {
    @Query("SELECT * FROM contact")
    fun getAllContacts(): LiveData<List<Contact>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertContact(contact: Contact)
    @Delete
     fun deleteContact(contact: Contact)
    @Update
     fun updateContact(contact: Contact)

}