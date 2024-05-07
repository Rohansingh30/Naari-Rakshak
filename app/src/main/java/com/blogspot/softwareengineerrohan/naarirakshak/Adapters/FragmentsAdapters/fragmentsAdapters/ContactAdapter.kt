package com.blogspot.softwareengineerrohan.naarirakshak.Adapters.FragmentsAdapters.fragmentsAdapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.Fragments.GroupFragment
import com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.roomcontactactivity.AddContactActivity
import com.blogspot.softwareengineerrohan.naarirakshak.databinding.RcvContactItemBinding
import com.blogspot.softwareengineerrohan.naarirakshak.roomDb.Contact

class ContactAdapter(val context: Context, val list: List<Contact>, val listener: GroupFragment):RecyclerView.Adapter<ContactAdapter.ViewHolder>(){


    class ViewHolder(val binding : RcvContactItemBinding):RecyclerView.ViewHolder(binding.root) {

    }

interface OnItemClickListener{
    fun deleteContact(contact: Contact)
    fun updateContact(contact: Contact)
}



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RcvContactItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
    return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = list[position]
        holder.binding.tvListName.text = contact.name
        holder.binding.tvRelation.text = contact.relation
        holder.binding.tvListNumber.text = contact.number

        holder.binding.tvDeleteBtn.setOnClickListener {
            listener.deleteContact(contact)
        }

        holder.binding.tvUpdateBtn.setOnClickListener {
            listener.updateContact(contact)

        }

        holder.binding.root.setOnClickListener {
            val intent = Intent(context, AddContactActivity::class.java)
            intent.putExtra("id", contact.id)
            intent.putExtra("name", contact.name)
            intent.putExtra("relation", contact.relation)
            intent.putExtra("number", contact.number)
            context.startActivity(intent)
        }




    }
}