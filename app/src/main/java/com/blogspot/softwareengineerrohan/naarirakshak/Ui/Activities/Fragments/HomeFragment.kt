package com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.Fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.telephony.SmsManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat.Action
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.blogspot.softwareengineerrohan.naarirakshak.Adapters.FragmentsAdapters.fragmentsAdapters.ContactAdapter
import com.blogspot.softwareengineerrohan.naarirakshak.Adapters.FragmentsAdapters.fragmentsAdapters.ExploreAdapter
import com.blogspot.softwareengineerrohan.naarirakshak.Adapters.FragmentsAdapters.fragmentsAdapters.RavEmergencyAdapter
//import com.blogspot.softwareengineerrohan.naarirakshak.Adapters.FragmentsAdapters.fragmentsAdapters.ShareLocationAdapter
import com.blogspot.softwareengineerrohan.naarirakshak.Models.FragmentsModels.fragmnetsmodels.ExploreModel
import com.blogspot.softwareengineerrohan.naarirakshak.Models.FragmentsModels.fragmnetsmodels.RavEmergencyItem
import com.blogspot.softwareengineerrohan.naarirakshak.Models.FragmentsModels.fragmnetsmodels.RavShareLocationModel
import com.blogspot.softwareengineerrohan.naarirakshak.R
import com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.activity.SafeTutoialActivity
import com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.roomcontactactivity.AddContactActivity
import com.blogspot.softwareengineerrohan.naarirakshak.databinding.FragmentHomeBinding
import com.blogspot.softwareengineerrohan.naarirakshak.roomDb.Contact
import com.blogspot.softwareengineerrohan.naarirakshak.roomDb.ContactDatabase
import com.blogspot.softwareengineerrohan.naarirakshak.roomDb.ContactViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

private lateinit var db : FirebaseFirestore
private lateinit var mAuth: FirebaseAuth
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    val viewModel : ContactViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var db = FirebaseFirestore.getInstance()



        val dataList = ArrayList<RavEmergencyItem>()
        dataList.add(RavEmergencyItem(R.drawable.baseline_gpp_good_24, "Police", "Call 122 for emergencies", "122"))
        dataList.add(RavEmergencyItem(R.drawable.baseline_health_and_safety_24, "Ambulance", "Call 102 for ambulance", "108"))
        dataList.add(RavEmergencyItem(R.drawable.baseline_fire_truck_24, "Fire Brigade", "Call 101 for fire brigade ", "101"))


        binding.ravEmergency.adapter = RavEmergencyAdapter(requireContext(), dataList)

        val exploreList = ArrayList<ExploreModel>()
        exploreList.add(ExploreModel(R.drawable.baseline_local_police_24, "Police"))
        exploreList.add(ExploreModel(R.drawable.baseline_local_hospital_24, "Hospital"))
        exploreList.add(ExploreModel(R.drawable.baseline_local_pharmacy_24, "Pharmacies"))
        exploreList.add(ExploreModel(R.drawable.baseline_fire_truck_24, "Ambulance"))
        binding.ravEmergency.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.ravExplore.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.ravExplore.adapter = ExploreAdapter(requireContext(), exploreList)

//        val shareLocation = ArrayList<RavShareLocationModel>()
//        shareLocation.add(RavShareLocationModel( " Get Home \n Safe", "Share live Location to Saved Contacts", R.drawable.home))
//        shareLocation.add(RavShareLocationModel( "Safe Tutorial", "Be\nSafe", R.drawable.home))
//
//        binding.ravShareLocation.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        binding.ravShareLocation.adapter = ShareLocationAdapter(requireContext(), shareLocation)
//

        binding.apply {
            cardViewTutorial.setOnClickListener {
                startActivity(Intent(context, SafeTutoialActivity::class.java))
            }
            cardViewSafe.setOnClickListener {


                initFusedLocationProviderClient()
                viewModel.getAllContacts().observe(requireActivity()) {
                    for (i in it) {
                        getUserLocation(i)
                    }
                }
                setUpLocationListener()




            }




        }











    }

    private fun setUpLocationListener() {
        viewModel.getAllContacts().observe(requireActivity()) {
            if (it.isEmpty()) {

                val intent = Intent(context, AddContactActivity::class.java)
                startActivity(intent)
            }


        }


    }



    private fun initFusedLocationProviderClient() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }
    private fun getUserLocation(contact: Contact) {

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1

            )
            return
        }

        else {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                if (it != null) {
//it show my location on google map
//                    val latitude = it.latitude
//                    val longitude = it.longitude
//                    val intent = Intent(Intent.ACTION_VIEW)
//                    intent.data = Uri.parse("geo:$latitude,$longitude")
//                    startActivity(intent)

//it send my location of google map to share by intent
//                    val uri = "https://www.google.com/maps/?q=${it.latitude},${it.longitude}"
//                    val sharingIntent = Intent(Intent.ACTION_SENDTO)
//                    sharingIntent.type = "text/plain"
//                    sharingIntent.putExtra(Intent.EXTRA_TEXT, uri)
//                    startActivity(Intent.createChooser(sharingIntent, "Share in..."))

//                    get room data to list


// this code best for send location with msg to contact numbers
                    val uri = "https://www.google.com/maps/?q=${it.latitude},${it.longitude}"
                    val intent = Intent(Intent.ACTION_VIEW)
                    val phoneNumber = arrayListOf<String>("${contact.number}")
                    intent.data = Uri.parse("sms:$phoneNumber")
                    intent.putExtra("sms_body", "I am in trouble, help me!! $uri")
                    startActivity(intent)

                } else {

//                    14 march pending work is get contacts and send location

                }
            }

        }
    }


    }

