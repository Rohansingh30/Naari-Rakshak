package com.blogspot.softwareengineerrohan.naarirakshak.Adapters.FragmentsAdapters.fragmentsAdapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.softwareengineerrohan.naarirakshak.Models.FragmentsModels.fragmnetsmodels.RavEmergencyItem
import com.blogspot.softwareengineerrohan.naarirakshak.databinding.RavEmergencyItemBinding

class RavEmergencyAdapter(val context : Context, private val emergencyItems : ArrayList<RavEmergencyItem>) : RecyclerView.Adapter<RavEmergencyAdapter.RavEmergencyViewHolder>() {
    class RavEmergencyViewHolder(var binding: RavEmergencyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RavEmergencyViewHolder {

        val binding = RavEmergencyItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return RavEmergencyViewHolder(binding)


    }

    override fun getItemCount(): Int {
        return emergencyItems.size
    }

    override fun onBindViewHolder(holder: RavEmergencyViewHolder, position: Int) {


        holder.binding.imageSiren.setImageResource(emergencyItems[position].sirenIimage)
        holder.binding.emertitle.text = emergencyItems[position].emerTitleTextView
       // holder.binding.emerDesp.text = emergencyItems[position].emerdDespTextView
        holder.binding.emerNum.text = emergencyItems[position].emerCallTextView

        holder.binding.cardViewEmer.setOnClickListener {
            when (position) {
                0 -> {




                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:112")
                    context.startActivity(intent)

                    Toast.makeText(
                        context,
                        "Calling " + emergencyItems[position].emerTitleTextView,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                1 -> {


                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:108")
                    context.startActivity(intent)


                    Toast.makeText(
                        context,
                        "Calling " + emergencyItems[position].emerTitleTextView,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                2 -> {

                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:101")
                    context.startActivity(intent)

                    Toast.makeText(
                        context,
                        "Calling " + emergencyItems[position].emerTitleTextView,
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }


        }
    }
}