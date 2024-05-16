package com.blogspot.softwareengineerrohan.naarirakshak.MainActivities

import android.Manifest
import android.R.id.message
import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.telephony.SmsManager
import android.util.Log
import android.view.GestureDetector
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.blogspot.softwareengineerrohan.naarirakshak.Models.FragmentsModels.fragmnetsmodels.RcvCaptureImgModel
import com.blogspot.softwareengineerrohan.naarirakshak.R
import com.blogspot.softwareengineerrohan.naarirakshak.SharedPreferernences.PrefConstants
import com.blogspot.softwareengineerrohan.naarirakshak.SharedPreferernences.SharedPref
import com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.AppScreenInfo.AppInfoActivity
import com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.Fragments.ContactFragment
import com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.Fragments.GroupFragment
import com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.Fragments.HomeFragment
import com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.Fragments.MapsFragment
import com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.Fragments.SosFragment
import com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.HeaderFragments.SettingFragment
import com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.activity.LoginActivity
import com.blogspot.softwareengineerrohan.naarirakshak.databinding.ActivityMainBinding
import com.blogspot.softwareengineerrohan.naarirakshak.roomDb.Contact
import com.blogspot.softwareengineerrohan.naarirakshak.roomDb.ContactDatabase
import com.blogspot.softwareengineerrohan.naarirakshak.roomDb.ContactViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.play.integrity.internal.i
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import java.io.File
import java.net.URLEncoder
import java.util.Objects


class MainActivity : AppCompatActivity() {

    // for shake feature
    private var sensorManager: SensorManager? = null
    private var acceleration = 0f
    private var currentAcceleration = 0f
    private var lastAcceleration = 0f




    private var mediaRecorder: MediaRecorder? = null
    private val PERMISSION_CODE = 11

    //for media player shake feature
    lateinit var mediaPlayer: MediaPlayer

    //location send by shaking feature
    val viewModel: ContactViewModel by viewModels()
    private lateinit var appDb: ContactDatabase


    private lateinit var binding: ActivityMainBinding
    private lateinit var toggled: ActionBarDrawerToggle

    // permissions
    val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_CONTACTS
    )
    val permissionCode = 911

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDb = ContactDatabase.getDatabase(this)

        supportActionBar?.hide()
        setSupportActionBar(binding.toolbar)


        binding.toolbar.setTitle("Naari Rakshak")
        binding.toolbar.setSubtitle("we admire you are strong")
        binding.toolbar.setTitleTextColor(getColor(R.color.white))
        binding.toolbar.setSubtitleTextColor(getColor(R.color.white))

        Toast.makeText(this, "Welcome to Naari Rakshak", Toast.LENGTH_SHORT).show()


        // permissions function

        if (isAllPeremissionsGranted()) {

            if (isLocationEnabled(this)) {
                setUpLocationListener()
            } else {
                showGPSNotEnabledDialog(this)
            }

        } else {
            askForPermissions()
        }

// Upload data to firestore like this
        val db = FirebaseFirestore.getInstance()

        val currentUser = FirebaseAuth.getInstance().currentUser
        val name = currentUser?.displayName.toString()
        val email = currentUser?.email.toString()
        val phoneNumber = currentUser?.phoneNumber.toString()
        val imageUrl = currentUser?.photoUrl.toString()

        val user = hashMapOf(
            "Name " to name,
            "Email" to email,
            "Phone Number" to phoneNumber,
            "Image Url" to imageUrl
        )

        db.collection("users")
            .document(email)
            .set(user)
            .addOnSuccessListener {

            }
            .addOnFailureListener { }
//**************************************************************************************

//**************************************************************************************
        replaceFragment(HomeFragment())

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.nav_open,
            R.string.nav_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()




        binding.fabSosBtn.setOnClickListener {

Toast.makeText(this, "long press for emergency situation", Toast.LENGTH_SHORT).show()
//            replaceFragment(SosFragment())
            // Handle single click event

            // Pass the touch event to the GestureDetector


        }
        binding.fabSosBtn.setOnLongClickListener {
            emergencySystems()

            return@setOnLongClickListener true

        }



        binding.bottomBar.background = null

        binding.bottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                }

                R.id.location -> {
                    replaceFragment(MapsFragment())

                }

                R.id.contact -> {
                    replaceFragment(ContactFragment())
                }

                R.id.chat -> {
                    replaceFragment(GroupFragment())
                }


                else -> {
                    false
                }
            }
            true


        }

        toggled = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggled)
        toggled.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.NavViews.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.headerSignOut -> {
signouts()

                }

                R.id.header_announcements -> {
                    Toast.makeText(applicationContext, "Coming Soon", Toast.LENGTH_SHORT).show()

                }

                R.id.header_home -> {
                    Toast.makeText(applicationContext, "Home", Toast.LENGTH_SHORT).show()
                    replaceFragment(HomeFragment())

                }

                R.id.header_profile -> {
                    replaceFragment(SettingFragment())
                    Toast.makeText(applicationContext, "Profile", Toast.LENGTH_SHORT)
                        .show()


                }
                R.id.header_FAQs -> {
                    Toast.makeText(applicationContext, "Coming Soon", Toast.LENGTH_SHORT).show()
                }
                R.id.header_owner_Details -> {
                    startActivity(Intent(this, AppInfoActivity::class.java))
//                    Toast.makeText(applicationContext, "Coming Soon", Toast.LENGTH_SHORT).show()
                }
                R.id.header_share -> {
                    Toast.makeText(applicationContext, "Coming Soon", Toast.LENGTH_SHORT).show()
                }



                R.id.header_Invite -> {
//                    replaceFragment(InviteFragment())
                    Toast.makeText(applicationContext, "Coming Soon", Toast.LENGTH_SHORT).show()

                }


            }
            true
        }

        //shake features implementation

        mediaPlayer = MediaPlayer.create(this, R.raw.militaryalarm)
        mediaPlayer.setVolume(10f, 10f)
        mediaPlayer.isLooping = true


        // Getting the Sensor Manager instance
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        registerListener()






    }


    // Create a GestureDetector object



    private fun registerListener() {
        // Registering the Listener
        Objects.requireNonNull(sensorManager)!!
            .registerListener(
                sensorListener, sensorManager!!
                    .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL
            )

        acceleration = 10f
        currentAcceleration = SensorManager.GRAVITY_EARTH
        lastAcceleration = SensorManager.GRAVITY_EARTH
    }

    private val sensorListener: SensorEventListener = object : SensorEventListener {
        @SuppressLint("ObsoleteSdkInt")
        override fun onSensorChanged(event: SensorEvent) {

            // Fetching x,y,z values
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            lastAcceleration = currentAcceleration

            // Getting current accelerations
            // with the help of fetched x,y,z values
            currentAcceleration = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta: Float = currentAcceleration - lastAcceleration
            acceleration = acceleration * 0.9f + delta

            // Display a Toast message if
            // acceleration value is over 12
            if (acceleration > 40 && acceleration < 50) {

                //vibrate phone and emergencySystems features
                emergencySystems()

            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    private fun emergencySystems() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(3000, 10))
        }else{

            vibrator.vibrate(3000)

        }

        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Emergency System")
        alertDialog.setMessage("Do you want to activate emergency system?")
        alertDialog.setPositiveButton("Yes") { _, _ ->
            // Handle the positive button click


            activateEmergencySystem()

        }

        alertDialog.setNeutralButton("Cancel") { _, _ ->
            // Handle the neutral button click


        }
        alertDialog.show()
    }
    private fun activateEmergencySystem() {

        // Emergency system activated
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(5000, 10))

            Toast.makeText(applicationContext, "Emergency System Activated", Toast.LENGTH_SHORT).show()
//audioSend()
            mediaPlayer.start()
            viewModel.getAllContacts().observe(this) { contacts ->
                for (contact in contacts) {
                    getUserLocationsByFused(contact)
                }
            }



        } else {
            vibrator.vibrate(5000)
//            Toast.makeText(this, "Playing alarm!", Toast.LENGTH_SHORT).show()

            mediaPlayer.start()
            viewModel.getAllContacts().observe(this) { contacts ->
                for (contact in contacts) {
                    getUserLocationsByFused(contact)
                }
            }

        }

//********audio send function
//        if (isMicrophoneAvailable()) {
//            getMicrophonePermission()
//        }

//********audio send function

    }




fun getUserLocationsByFused(contact: Contact){
    Toast.makeText(this, "Sending sos alerts to saved contacts!", Toast.LENGTH_SHORT).show()

    // Location Request
    val locationRequest = LocationRequest.create().apply {
//        interval = 60 * 1000 // Update interval in milliseconds
        fastestInterval =  5 * 60 * 1000 // Fastest update interval in milliseconds
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    if (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return
    }
    fusedLocationProviderClient.requestLocationUpdates(
        locationRequest,
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                // Use the location object to get the latitude and longitude
                val latitude = locationResult.lastLocation?.latitude
                val longitude = locationResult.lastLocation?.longitude
                // Do something with the latitude and longitude
                //room db contact send location by number
                val recipients = listOf(contact.number)
                val messageBodies = listOf("sms_body", "I am in trable please help me " +
                        "https://www.google.com/maps/?q=${latitude},${longitude}")


                try {

                    val smsManager = SmsManager.getDefault()
                    for (i in recipients.indices) {
                        smsManager.sendTextMessage(recipients[i], null, "$messageBodies", null, null)
                    }

                    Toast.makeText(applicationContext, "Sending message to ${contact.name}", Toast.LENGTH_SHORT).show()

                } catch (e: Exception) {
                    Toast.makeText(
                        applicationContext,
                        "Something went wrong $e",
                        Toast.LENGTH_LONG
                    ).show()
                }



//                Toast.makeText(applicationContext, "Latitude: $latitude, Longitude: $longitude", Toast.LENGTH_LONG).show()
            }
        },
        Looper.getMainLooper()
    )
}
    override fun onResume() {
        sensorManager?.registerListener(sensorListener, sensorManager!!.getDefaultSensor(
            Sensor .TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL
        )
        super.onResume()


    }

    override fun onPause() {
//        sensorManager!!.unregisterListener(sensorListener)
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        // unregister listener

        sensorManager!!.unregisterListener(sensorListener)
        mediaPlayer.stop()

//        stopAudio()

    }
//private val locationSendByShake: BroadcastReceiver = object : BroadcastReceiver() {
//    override fun onReceive(context: Context, intent: Intent) {
//        val lat = intent.getDoubleExtra("lat",)
//        val lng = intent.getDoubleExtra("lng",)
//        val address = intent.getStringExtra("address")
//        Toast.makeText(applicationContext, "Sending Location", Toast.LENGTH_SHORT).show()
//    }
//}


//*****************
    //audio send function
private val handler = Handler()

    fun scheduleDelayedTask() {
        handler.postDelayed({
            // Perform your task here
            startAudio()
            Toast.makeText(this, "Sending audio", Toast.LENGTH_SHORT).show()


        }, 5 * 60 * 1000 )
    }

    private fun audioSend(){
        startAudio()

        scheduleDelayedTask()



    uploadAud()


    }

    private fun isMicrophoneAvailable(): Boolean {
        if (this.packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {

            return true
        } else {

            return false
        }
    }
   private fun startAudio() {
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
   private fun stopAudio() {
        try {
            mediaRecorder!!.stop()
            mediaRecorder!!.release()
            mediaRecorder = null
            Toast.makeText(this, "Recording Stopped", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Toast.makeText(this, "Recording Failed${e.message}", Toast.LENGTH_SHORT).show()
        }




    }
   private fun playAudio() {

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

    private fun uploadAud(){
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val storageRef = Firebase.storage.reference.child("audio/$userId")
        val audioRef = storageRef.child("my_audio.mp3")
        val audioFileUri = Uri.fromFile(File(getRecordingFilePath()))
        val uploadTask = audioRef.putFile(audioFileUri)

// Register observers to listen for the upload status
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        Toast.makeText(this, "Audio Uploading Failed", Toast.LENGTH_SHORT).show()

        }.addOnSuccessListener {

            // Handle successful uploads
            Toast.makeText(this, "Audio Uploaded", Toast.LENGTH_SHORT).show()
        }
    }


    private fun getRecordingFilePath(): String {
        val contextWrapper = ContextWrapper(applicationContext)
        val audioFolder = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val file = File(audioFolder, "testRecording" + ".mp3")
        return file.path
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
//****************



    private fun signouts() {


        if (FirebaseAuth.getInstance().currentUser != null) {
            FirebaseAuth.getInstance().signOut()

        }else{

//        Toast.makeText(applicationContext, "User Signed Out", Toast.LENGTH_SHORT).show()
//        // dekhna remove krke kya change aya smj ni ara ok nxt time see
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val go = GoogleSignIn.getClient(this, gso)



            SharedPref.putBoolean(PrefConstants.IS_USER_LOGGED_IN, false)
            go.signOut()
//        FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            Toast.makeText(applicationContext, "User Signed Out", Toast.LENGTH_SHORT).show()
            finish()

        }


    }

    private fun setUpLocationListener() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
// for getting the current location update after every 2 seconds with high accuracy
        val locationRequest = LocationRequest().setInterval(2000).setFastestInterval(2000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    for (location in locationResult.locations) {
//
//
//                        // location aagayi current location
//                        Log.d("Location89", location.latitude.toString())
//                        Log.d("Location89", location.longitude.toString())
//
//
//
//
//// This code working and get location continously
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
//
//
//
////                                val getLocation = Location("provider")
////                                getLocation.latitude = location.latitude
////                                getLocation.longitude = location.longitude
//
////
////                                Toast.makeText(applicationContext,
////                                    "Location Updated {latitude: ${getLocation.latitude}," +
////                                            " longitude: ${getLocation.longitude}}", Toast.LENGTH_SHORT).show()
////                                This code working and get location continously
////                                check  working in here start 03 march okk remaining work is to get location of current user
//
//
//                            }
//                            .addOnFailureListener {
//
//
//                            }
                    }
                    // Few more things we can do here:
                    // For example: Update the location of user on server
                }
            },
            Looper.myLooper()
        )
    }

    fun isLocationEnabled(context: Context): Boolean {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    /**
     * Function to show the "enable GPS" Dialog box
     */
    fun showGPSNotEnabledDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle("Enable GPS")
            .setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Enable") { _, _ ->
                context.startActivity(Intent(ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .show()
    }
    fun isAllPeremissionsGranted(): Boolean {
        for(item in permissions){

            if( ContextCompat
                .checkSelfPermission(
                    this,
                    item
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }

        }
    return true
    }



    private fun askForPermissions() {
        ActivityCompat.requestPermissions(this, permissions, permissionCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == permissionCode) {
            if (allPermissionsGranted()) {
                Toast.makeText(this, "All Permission Granted", Toast.LENGTH_SHORT).show()
                // **when permision is granted to record video then frequently open the camera
                //openCameras()

                setUpLocationListener()

            } else {
                Toast.makeText(this, "Some Permission Denied", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(
                this,
                "All Permission Denied { PermissionCode not found }",
                Toast.LENGTH_SHORT
            ).show()
        }


    }

    private fun openCameras() {
        //intent for casmera opening
        val intentCameras = Intent("android.media.action.IMAGE_CAPTURE")
        startActivity(intentCameras)
    }

    private fun allPermissionsGranted(): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }

        }
        return true
    }


    override fun onBackPressed() {
        super.onBackPressed()

        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressedDispatcher.onBackPressed()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.Homess -> {
            replaceFragment(HomeFragment())

            }


            R.id.naariRakshak -> {
                startActivity(Intent(this, AppInfoActivity::class.java))
            }

            R.id.Logoutess -> {
                signouts()

            }
            R.id.profiless -> {
                replaceFragment(SettingFragment())

            }

        }

        if (toggled.onOptionsItemSelected(item)) {
            return true
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)

                return  super.onOptionsItemSelected(item)


    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame, fragment)
        fragmentTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.setting_menu, menu)


return true
    }


}











