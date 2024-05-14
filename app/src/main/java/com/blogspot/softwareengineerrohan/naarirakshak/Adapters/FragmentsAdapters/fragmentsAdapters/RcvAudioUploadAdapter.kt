package com.blogspot.softwareengineerrohan.naarirakshak.Adapters.FragmentsAdapters.fragmentsAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.softwareengineerrohan.naarirakshak.Models.FragmentsModels.fragmnetsmodels.RcvAudioUploadModel
import com.blogspot.softwareengineerrohan.naarirakshak.databinding.RcvAudioSosItemBinding

class RcvAudioUploadAdapter(val context: Context, val list : ArrayList<RcvAudioUploadModel>):RecyclerView.Adapter<RcvAudioUploadAdapter.RcvAudioUploadViewHolder>() {
    class RcvAudioUploadViewHolder(var binding : RcvAudioSosItemBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RcvAudioUploadViewHolder {

        val binding = RcvAudioSosItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RcvAudioUploadViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RcvAudioUploadViewHolder, position: Int) {
    val item = list[position]
        holder.binding.audioName.text = item.audio
//        holder.binding.audioType.text = item.audioType
//        holder.binding.audioImg.setImageURI(item.audioImg.toUri())




    }
}