package com.blogspot.softwareengineerrohan.naarirakshak.Adapters.FragmentsAdapters.fragmentsAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso

import androidx.recyclerview.widget.RecyclerView
import com.blogspot.softwareengineerrohan.naarirakshak.Models.FragmentsModels.fragmnetsmodels.RcvCaptureImgModel
import com.blogspot.softwareengineerrohan.naarirakshak.databinding.RcvCaptureImgFbdbItemBinding

class RcvCaptureImgAdapter(val context: Context, val list:List<RcvCaptureImgModel>): RecyclerView.Adapter<RcvCaptureImgAdapter.ViewHolder>() {
    class ViewHolder(val binding : RcvCaptureImgFbdbItemBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RcvCaptureImgFbdbItemBinding.inflate(LayoutInflater.from(context),parent,false)
    return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]
        Picasso.get().load(item.img).into(holder.binding.captureImgPlaceholder)

    }
}