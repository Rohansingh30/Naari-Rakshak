package com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.SosActivity

import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import com.blogspot.softwareengineerrohan.naarirakshak.Adapters.FragmentsAdapters.fragmentsAdapters.RcvAudioUploadAdapter
import com.blogspot.softwareengineerrohan.naarirakshak.Adapters.FragmentsAdapters.fragmentsAdapters.RcvVideoUploadAdapter
import com.blogspot.softwareengineerrohan.naarirakshak.Models.FragmentsModels.fragmnetsmodels.RcvAudioUploadModel
import com.blogspot.softwareengineerrohan.naarirakshak.databinding.ActivityAudioBinding
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class AudioActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityAudioBinding.inflate(layoutInflater)
    }
    private lateinit var db: FirebaseFirestore


    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private val PERMISSION_CODE = 11
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.NwButton.setOnClickListener {
            uploadAud()
        }

        if (isMicrophoneAvailable()) {
            getMicrophonePermission()
        }
        getAud()

    }


    fun stopAudio(view: View) {
        try {
            mediaRecorder!!.stop()
            mediaRecorder!!.release()
            mediaRecorder = null
            Toast.makeText(this, "Recording Stopped", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Toast.makeText(this, "Recording Failed${e.message}", Toast.LENGTH_SHORT).show()
        }




    }
    fun playAudio(view: View) {

        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
        }
        try {
            mediaPlayer!!.setDataSource(getRecordingFilePath())
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
            Toast.makeText(this, "Playing Recording", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }
private fun getAud(){
 //    get audio from firebase cloud storage
    val storageRef = FirebaseStorage.getInstance().reference.child("audios")
storageRef.listAll().addOnSuccessListener { listResult ->
    val audios = ArrayList<RcvAudioUploadModel>()
    for (item in listResult.items) {
        audios.add(RcvAudioUploadModel(item.name))
        binding.rcvAudioSos.layoutManager = GridLayoutManager(this, 3)
        binding.rcvAudioSos.adapter = RcvAudioUploadAdapter(this, audios)

        }

    }
}

    private fun uploadAud(){
        val storageRef = FirebaseStorage.getInstance().reference.child("audios" + System.currentTimeMillis() + ".mp3")
        val audioFile = File(getRecordingFilePath())
        val audioUri = audioFile.toUri()
        val uploadTask = storageRef.child(audioFile.name).putFile(audioUri)
        uploadTask.addOnSuccessListener {
            Firebase.database.reference.child("audios").push().setValue(it.toString())

            Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        }


    }


    private fun isMicrophoneAvailable(): Boolean {
        if (this.packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {

            return true
        } else {

            return false
        }
    }

    private fun getMicrophonePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.RECORD_AUDIO),
                PERMISSION_CODE
            )
        }
    }


    private fun getRecordingFilePath(): String {
        val contextWrapper = ContextWrapper(applicationContext)
        val audioFolder = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val file = File(audioFolder, "testRecording" + ".mp3")
        return file.path
    }

}