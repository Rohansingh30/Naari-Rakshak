package com.blogspot.softwareengineerrohan.naarirakshak.Adapters.FragmentsAdapters.fragmentsAdapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.softwareengineerrohan.naarirakshak.Models.FragmentsModels.fragmnetsmodels.ExploreModel
import com.blogspot.softwareengineerrohan.naarirakshak.databinding.RavExploreItemBinding


class ExploreAdapter(var context: Context, var exploreList : ArrayList<ExploreModel>) : RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder>() {
    class ExploreViewHolder(var binding : RavExploreItemBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreViewHolder {
val binding = RavExploreItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ExploreViewHolder(binding)


    }

    override fun getItemCount(): Int {
return  exploreList.size

    }

    override fun onBindViewHolder(holder: ExploreViewHolder, position: Int) {

holder.binding.exploreImage.setImageResource(exploreList.get(position).expImage)
holder.binding.tvPolice.text = exploreList.get(position).tvPoliceText

        holder.binding.cardViewExplore.setOnClickListener {
            when(position){
                0 -> {
                 val address = "Police station Near me"
                    val url = "http://maps.google.com/maps?daddr=$address"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

                    context.startActivity(intent)
                }
                1 -> {
                    val address = "Hospital Near me"
                    val url = "http://maps.google.com/maps?daddr=$address"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

                    context.startActivity(intent)
                }
                2 -> {
                    val address = "Pharmacies Near me"
                    val url = "http://maps.google.com/maps?daddr=$address"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

                    context.startActivity(intent)
                }
                3 -> {
                    val address = "Fire Brigade Near me"
                    val url = "http://maps.google.com/maps?daddr=$address"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)


                }


        }

    }}}
