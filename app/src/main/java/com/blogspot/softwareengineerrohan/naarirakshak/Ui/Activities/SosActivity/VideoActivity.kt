package com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.SosActivity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.blogspot.softwareengineerrohan.naarirakshak.Adapters.FragmentsAdapters.fragmentsAdapters.RcvVideoUploadAdapter
import com.blogspot.softwareengineerrohan.naarirakshak.Models.FragmentsModels.fragmnetsmodels.RcvAudioUploadModel
import com.blogspot.softwareengineerrohan.naarirakshak.Models.FragmentsModels.fragmnetsmodels.RcvVideoUploadModel
import com.blogspot.softwareengineerrohan.naarirakshak.databinding.ActivityVideoBinding
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage

class VideoActivity : AppCompatActivity() {
    val binding by lazy{
        ActivityVideoBinding.inflate(layoutInflater)
    }
    private lateinit var progressDialog: ProgressDialog





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        FirebaseApp.initializeApp(this)

        progressDialog = ProgressDialog(this)
//        binding.videoView.setVideoPath("android.resource://" + packageName + "/" + R.raw.video)

//        binding.videoView.isVisible = false
        binding.videoViewBtn.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "video/*"
            videoLauncher.launch(intent)
        }

        getVideoFromFB()


    }

    private fun getVideoFromFB() {
        //get video from firebase cloud storage
       FirebaseStorage.getInstance().reference.child("videos").listAll().addOnSuccessListener {
           val list = ArrayList<RcvVideoUploadModel>()
           it.items.forEach {
               it.downloadUrl.addOnSuccessListener {
                   list.add(RcvVideoUploadModel(it.toString()))

                   binding.rcvVideoUpload.layoutManager = GridLayoutManager(this, 3)
                   binding.rcvVideoUpload.adapter = RcvVideoUploadAdapter(this, list)

               }






           }

       }



    }

    val videoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
  if(it.resultCode == RESULT_OK){
      if (it.data != null) {
progressDialog.setTitle("Uploading...")
          progressDialog.show()
          val ref = Firebase.storage.reference.child("videos/"+System.currentTimeMillis()+"."+getFileTpye(it.data?.data))
          ref.putFile(it.data?.data!!).addOnSuccessListener {
              ref.downloadUrl.addOnSuccessListener {
                  progressDialog.dismiss()
                  Firebase.database.reference.child("videos").push().setValue(it.toString())
                  Toast.makeText(this, "Video Uploaded", Toast.LENGTH_SHORT).show()
//                  binding.videoView.setVideoURI(it)
//                  binding.videoView.setMediaController(MediaController(this))
//                  binding.videoView.start()
              }
          }
              .addOnProgressListener {
                  val progress = (100.0 * it.bytesTransferred / it.totalByteCount)
                  progressDialog.setMessage("Uploaded ${progress.toInt()}%")
              }

//       binding.videoView.isVisible = true
//       binding.videoView.setVideoURI(it.data?.data)
//       binding.videoView.start()

          //media controller
//



      }

  }
    }

    private fun getFileTpye(data: Uri?): String? {
val r = contentResolver
        val mimeTpye = MimeTypeMap.getSingleton()
        return mimeTpye.getMimeTypeFromExtension(r.getType(data!!))

    }


}

