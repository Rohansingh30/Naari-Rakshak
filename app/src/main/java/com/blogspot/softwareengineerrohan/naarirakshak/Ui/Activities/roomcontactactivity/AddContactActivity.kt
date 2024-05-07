package com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.roomcontactactivity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.blogspot.softwareengineerrohan.naarirakshak.Adapters.FragmentsAdapters.fragmentsAdapters.ContactAdapter
import com.blogspot.softwareengineerrohan.naarirakshak.databinding.ActivityAddContactBinding
import com.blogspot.softwareengineerrohan.naarirakshak.roomDb.Contact
import com.blogspot.softwareengineerrohan.naarirakshak.roomDb.ContactDatabase
import com.blogspot.softwareengineerrohan.naarirakshak.roomDb.ContactViewModel

class AddContactActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityAddContactBinding.inflate(layoutInflater)
    }
    private lateinit var appDb: ContactDatabase

    val viewModel : ContactViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        appDb = ContactDatabase.getDatabase(this)

        binding.saveBtn.setOnClickListener {
            saveContacts()
        }

    }

    private fun saveContacts() {

        val name = binding.etName.text.toString()
        val relation = binding.etRelation.text.toString()
        val number = binding.etNumber.text.toString()

        if (name.isEmpty() || relation.isEmpty() || number.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        } else {
            val contact = Contact(null, name, relation, number)
            viewModel.addContact(contact)
            Toast.makeText(this, "Contact Saved", Toast.LENGTH_SHORT).show()

            finish()


        }
    }
}
