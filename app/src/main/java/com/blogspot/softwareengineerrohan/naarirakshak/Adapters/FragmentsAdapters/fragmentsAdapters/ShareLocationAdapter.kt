//package com.blogspot.softwareengineerrohan.naarirakshak.Adapters.FragmentsAdapters.fragmentsAdapters
//
//import android.content.Context
//import android.content.Intent
//import android.location.Location
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.core.content.ContextCompat.startActivity
//import androidx.recyclerview.widget.RecyclerView
//import com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.activity.SafeTutoialActivity
//import com.blogspot.softwareengineerrohan.naarirakshak.Models.FragmentsModels.fragmnetsmodels.RavShareLocationModel
//import com.blogspot.softwareengineerrohan.naarirakshak.databinding.RavShareLocationBinding
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore
//
//class ShareLocationAdapter(var context: Context,var shareLocationList :ArrayList<RavShareLocationModel>): RecyclerView.Adapter<ShareLocationAdapter.ViewHolder>() {
//    class ViewHolder(var binding :RavShareLocationBinding): RecyclerView.ViewHolder(binding.root){
//
//    }
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//
//val binding = RavShareLocationBinding.inflate(LayoutInflater.from(context),parent,false)
//        return ViewHolder(binding)
//
//
//    }
//
//    override fun getItemCount(): Int {
//return shareLocationList.size
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//
//        holder.binding.shareTitle.text = shareLocationList.get(position).sLocTitle
//        holder.binding.shareDesp.text = shareLocationList.get(position).sLocDesp
//        holder.binding.shareImage.setImageResource(shareLocationList.get(position).sLocImage)
//
//        holder.binding.cardViewShareLoc.setOnClickListener {
//            when (position) {
//                0 -> {
//                    //  Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show()
//
//
//
//
//                }
//
//                1 -> {
//                    Toast.makeText(context, "Saving Tips", Toast.LENGTH_SHORT).show()
//
//                    val intent = Intent (context, SafeTutoialActivity::class.java)
//                    startActivity(context, intent, null)
//                }
//
//            }
//
//
//
//        }
//
//
//    }}