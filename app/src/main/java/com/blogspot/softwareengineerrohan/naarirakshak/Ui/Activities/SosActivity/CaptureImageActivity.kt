package com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.SosActivity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.blogspot.softwareengineerrohan.naarirakshak.Adapters.FragmentsAdapters.fragmentsAdapters.RcvCaptureImgAdapter
import com.blogspot.softwareengineerrohan.naarirakshak.Models.FragmentsModels.fragmnetsmodels.RcvCaptureImgModel
import com.blogspot.softwareengineerrohan.naarirakshak.databinding.ActivityCaptureImageBinding
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.storage.storage

class CaptureImageActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCaptureImageBinding.inflate(layoutInflater)
    }
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)

        getImagesFromFirebaseCloudStorage()

        binding.addPhotoBTN.setOnClickListener {
                        uploadImageToFirebaseCloudStorage()

        }






    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getImagesFromFirebaseCloudStorage() {
//This show image in recyclerview from firebase cloud storage
        binding.rcvCaptureImgFbdb.adapter?.notifyDataSetChanged()

        Firebase.database.reference.child("Photo").get().addOnSuccessListener {
            val list = mutableListOf<RcvCaptureImgModel>()
            for (i in it.children) {
                val img = i.value.toString()
                list.add(RcvCaptureImgModel(img))
            }

            binding.rcvCaptureImgFbdb.layoutManager = GridLayoutManager(this, 3)
            binding.rcvCaptureImgFbdb.adapter = RcvCaptureImgAdapter(this, list)
            binding.rcvCaptureImgFbdb.adapter?.notifyDataSetChanged()
        }

        }

    private fun uploadImageToFirebaseCloudStorage() {
        val intent = Intent()
        intent.action = Intent.ACTION_PICK
        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
        imageLauncher.launch(intent)


    }

    private fun getFilesType(data: Uri): String? {

        val contentResolver = contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getMimeTypeFromExtension(contentResolver.getType(data))

    }

    @SuppressLint("NotifyDataSetChanged")
    val imageLauncher =
        registerForActivityResult(androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                if (it.data != null) {
                    progressDialog.setTitle("Uploading...")
                    progressDialog.show()

                    val ref = Firebase.storage.reference.child(
                        "Photo/" + System.currentTimeMillis() + "." + getFilesType(it.data?.data!!)
                    )
                    ref.putFile(it.data?.data!!).addOnSuccessListener {

                        ref.downloadUrl.addOnSuccessListener {

                            //load image by piccaso
                            Firebase.database.reference.child("Photo").push().setValue(it.toString())
                            Toast.makeText(this, "Image Uploaded", Toast.LENGTH_SHORT).show()
                            progressDialog.dismiss()

//It show imag e in recyclerview but not working

//                            binding.rcvCaptureImgFbdb.adapter = RcvCaptureImgAdapter(this, listOf(RcvCaptureImgModel(it.toString())))
//                            binding.rcvCaptureImgFbdb.layoutManager = GridLayoutManager(this, 3)
//


                        }
                    }
                        .addOnProgressListener {
                            val progress = (100.0 * it.bytesTransferred) / it.totalByteCount
                            progressDialog.setMessage("Uploaded ${progress.toInt()}%")
                        }
                }
            }


        }
}