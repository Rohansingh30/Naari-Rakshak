package com.blogspot.softwareengineerrohan.naarirakshak.Adapters.FragmentsAdapters.fragmentsAdapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.softwareengineerrohan.naarirakshak.Models.FragmentsModels.fragmnetsmodels.RcvVideoUploadModel
import com.blogspot.softwareengineerrohan.naarirakshak.databinding.RcvVideoUploadItemBinding
import com.bumptech.glide.Glide

class RcvVideoUploadAdapter(val context: Context, val list: ArrayList<RcvVideoUploadModel>):
    RecyclerView.Adapter<RcvVideoUploadAdapter.ViewHolder>() {


        class ViewHolder(val binding : RcvVideoUploadItemBinding):RecyclerView.ViewHolder(binding.root) {



        }








    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {



    val binding = RcvVideoUploadItemBinding.inflate(
        LayoutInflater.from(parent.context),parent,false
    )
        return ViewHolder(binding)


    }


    override fun getItemCount(): Int {

return list.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val item = list[position]
        // Get the video URL
        val videoUrl = item.video
        Glide.with(context).load(videoUrl).into(holder.binding.videoUploadImg)

        holder.binding.videoUploadImg.setOnClickListener {
playVideo(videoUrl)
        }


        }

    private fun playVideo(videoUrl: String) {

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
        intent.setDataAndType(Uri.parse(videoUrl), "video/*")
        context.startActivity(intent)

    }


}