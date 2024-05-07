package com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.media.MediaPlayer
import android.media.MediaRecorder.VideoSource
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
//import com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.MenusWork.CreateContactActivity
import com.blogspot.softwareengineerrohan.naarirakshak.R
import com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.SosActivity.AudioActivity
import com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.SosActivity.VideoActivity
import com.blogspot.softwareengineerrohan.naarirakshak.databinding.FragmentSosBinding
import com.google.firebase.firestore.FirebaseFirestore

class SosFragment : Fragment() {

    lateinit var binding: FragmentSosBinding

    private lateinit var db: FirebaseFirestore
    lateinit var mediaPlayer: MediaPlayer


    private lateinit var cameraManager: CameraManager
    private var isFlashlightOn = false



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): LinearLayout {
        // Inflate the layout for this fragment

        cameraManager = getSystemService(requireContext(), CameraManager::class.java) as CameraManager
         binding = FragmentSosBinding.inflate(inflater, container, false)
        // sos sound work
        mediaPlayer = MediaPlayer.create(context, R.raw.militaryalarm)
        mediaPlayer.setVolume(1f, 1f)
        mediaPlayer.isLooping = true

        //  totalTime = mediaPlayer.duration


        binding.tweetStart.setOnClickListener {
            Toast.makeText(context, "Playing alarm!", Toast.LENGTH_SHORT).show()
            mediaPlayer.start()


        }

        binding.tweetStop.setOnClickListener {
            mediaPlayer.pause()
        }

        binding.captureImg.setOnClickListener {
           captureImage()
        }
        binding.videoRec.setOnClickListener {
        videoRecord()

        }

        binding.audioRec.setOnClickListener {
            startActivity(Intent(context, AudioActivity::class.java))
        }



        binding.flashlightBtn.setOnClickListener {
            toggleFlashlight()
        }










        return binding.root

    }



    private fun toggleFlashlight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val cameraId = cameraManager.cameraIdList[0]
            cameraManager.setTorchMode(cameraId, !isFlashlightOn)
            isFlashlightOn = !isFlashlightOn
        }
    }
    private fun captureImage() {

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun videoRecord() {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {

            startActivity(intent)
        }

    }


    private fun replaceFragments(fragment: Fragment) {

        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame, fragment)
        fragmentTransaction.commit()


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        db = FirebaseFirestore.getInstance()



















    }

    private fun displayRcv(){

    }



    }



//
//        val dialog = Dialog(this.requireContext())
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setContentView(R.layout.dialog_sos_sending)
//
//        //work remaining 3 march work not complete
//
//        dialog.setCancelable(false)
//        dialog.setCanceledOnTouchOutside(false)
//        dialog.show()
//        val sosSendBtn = dialog.findViewById<Button>(R.id.send_sos_location_btn)
//        val sosCancelBtn = dialog.findViewById<Button>(R.id.send_sos_cancel_btn)
//        sosSendBtn.setOnClickListener {
//            Toast.makeText(context, "Sending Sos Location", Toast.LENGTH_SHORT).show()
            //setUpLocationListener()
//        }
//
//        sosCancelBtn.setOnClickListener {
//            dialog.dismiss()
//        }
//
//
//
//    }
////copy this code from main activity ok its is dublicate code using for check location send to contact okk not complete u can remove it bcoz this is copy code for testing
//    private fun setUpLocationListener() {
//        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.requireContext())
//// for getting the current location update after every 2 seconds with high accuracy
//        val locationRequest = LocationRequest().setInterval(2000).setFastestInterval(2000)
//            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//        if (ActivityCompat.checkSelfPermission(
//                this.requireContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this.requireContext(),
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return
//        }
//        fusedLocationProviderClient.requestLocationUpdates(
//            locationRequest,
//            object : LocationCallback() {
//                override fun onLocationResult(locationResult: LocationResult) {
//                    super.onLocationResult(locationResult)
//                    for (location in locationResult.locations) {
//
//                        // location aagayi current location
//                        Log.d("Location89", location.latitude.toString())
//                        Log.d("Location89", location.longitude.toString())
//
//
//
//
//
//                        val db = FirebaseFirestore.getInstance()
//
//                        val currentUser = FirebaseAuth.getInstance().currentUser
//                        val email = currentUser?.email.toString()
//
//                        val locationData = mutableMapOf<String, Any>(
//                            "latitude" to location.latitude,
//                            "longitude" to location.longitude
//                        )
//
//
//                        db.collection("users")
//                            .document(email)
//                            .update(locationData)
//                            .addOnSuccessListener {
//
////                                val getLocation = Location("provider")
////                                getLocation.latitude = location.latitude
////                                getLocation.longitude = location.longitude
////                               check  working in here start 03 march okk remainging work is to get location of current user
//
//
//                            }
//                            .addOnFailureListener {
//
//
//                            }
//                    }
//                    // Few more things we can do here:
//                    // For example: Update the location of user on server
//                }
//            },
//            Looper.myLooper()
//        )




