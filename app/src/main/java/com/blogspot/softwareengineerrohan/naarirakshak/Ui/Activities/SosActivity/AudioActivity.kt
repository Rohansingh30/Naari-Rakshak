package com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.SosActivity

import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.blogspot.softwareengineerrohan.naarirakshak.R
import com.blogspot.softwareengineerrohan.naarirakshak.databinding.ActivityAudioBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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


        if (isMicrophoneAvailable()) {
            getMicrophonePermission()
        }


    }
    fun startAudio(view: View) {
        try {
            mediaRecorder = MediaRecorder()
            mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder!!.setOutputFile(getRecordingFilePath())
            mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mediaRecorder!!.prepare()
            mediaRecorder!!.start()
            Toast.makeText(this, "Recording Started", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Recording Failed${e.message}", Toast.LENGTH_SHORT).show()
        }
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
    private fun uploadMusicToFirebase(){
        val db = FirebaseFirestore.getInstance()

        val myVoice = getRecordingFilePath()


                        val currentUser = FirebaseAuth.getInstance().currentUser
                        val email = currentUser?.email.toString()

                        val myRecordings = hashMapOf(
                            "recording" to myVoice

                        )


                        db.collection("users")
                            .document(email)
                            .set(myRecordings)
                            .addOnCompleteListener {
                                Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show()
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

    fun uploadRec(view: View) {
        uploadMusicToFirebase()
    }

}