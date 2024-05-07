package com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.Fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blogspot.softwareengineerrohan.naarirakshak.Adapters.FragmentsAdapters.fragmentsAdapters.ContactAdapter
import com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.roomcontactactivity.AddContactActivity
import com.blogspot.softwareengineerrohan.naarirakshak.databinding.FragmentGroupBinding
import com.blogspot.softwareengineerrohan.naarirakshak.roomDb.Contact
import com.blogspot.softwareengineerrohan.naarirakshak.roomDb.ContactDatabase
import com.blogspot.softwareengineerrohan.naarirakshak.roomDb.ContactViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore


class GroupFragment : Fragment(),  ContactAdapter.OnItemClickListener {
    lateinit var binding: FragmentGroupBinding
    val viewModel : ContactViewModel by viewModels()
    private lateinit var appDb: ContactDatabase
    private lateinit var db: FirebaseFirestore

    val array = ArrayList<String>()


     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGroupBinding.inflate(inflater, container, false)
         appDb = ContactDatabase.getDatabase(requireContext())

        binding.addGuardianBtn.setOnClickListener {
            val intent = Intent(context, AddContactActivity::class.java)
            startActivity(intent)
        }


         readContacts()
        return binding.root
    }

    private fun readContacts() {

        viewModel.getAllContacts().observe(requireActivity()) {
            binding.rcc.layoutManager = LinearLayoutManager(requireContext())
            binding.rcc.adapter = ContactAdapter(requireContext(), it, this@GroupFragment)

        }



    }

    override fun deleteContact(contact: Contact) {
        viewModel.deleteContact(contact)
        Toast.makeText(requireContext(), "Contact deleted", Toast.LENGTH_SHORT).show()


    }

    override fun updateContact(contact: Contact) {
        setUpLocationListener(contact)




//        val uri = Uri.parse("geo:${contact.number},0?q=${contact.number}")
//        val intent = Intent(Intent.ACTION_VIEW, uri)
//
//        startActivity(intent)

    }

    private fun setUpLocationListener(contact: Contact) {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
// for getting the current location update after every 2 seconds with high accuracy
            val locationRequest = LocationRequest()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
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

                            try {
                                val intent = Intent(Intent.ACTION_SENDTO)
                        intent.data = Uri.parse("smsto:${contact.number}")
                        intent.putExtra("sms_body", "I am in trable please help me " +
                                "https://www.google.com/maps/?q=${location.latitude},${location.longitude}")
                        startActivity(intent)


                        Toast.makeText(requireContext(), "Sending message to ${contact.name}", Toast.LENGTH_SHORT).show()

                            } catch (e: Exception) {
                            Toast.makeText(
                                requireContext(),
                                "Something went wrong $e",
                                Toast.LENGTH_LONG
                            ).show()
                        }




                    }
                    // Few more things we can do here:
                    // For example: Update the location of user on server
                }
            },
            Looper.myLooper()
        )
    }


}







