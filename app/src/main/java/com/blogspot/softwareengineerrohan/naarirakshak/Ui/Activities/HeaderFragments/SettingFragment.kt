package com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.HeaderFragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.blogspot.softwareengineerrohan.naarirakshak.R
import com.blogspot.softwareengineerrohan.naarirakshak.SharedPreferernences.PrefConstants
import com.blogspot.softwareengineerrohan.naarirakshak.SharedPreferernences.SharedPref
import com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.activity.LoginActivity
import com.blogspot.softwareengineerrohan.naarirakshak.databinding.FragmentSettingBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class SettingFragment : Fragment() {
    val binding by lazy{
        FragmentSettingBinding.inflate(layoutInflater)
    }


    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment

        return binding.root

    }

    @SuppressLint("MissingInflatedId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        db = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()



        db.collection("users")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful && it.result != null) {

                    val user = mAuth.currentUser
                    val name = user?.displayName
                    val email = user?.email
                    val photo = user?.photoUrl
                    val number = user?.phoneNumber
                    val id = user?.uid

                    binding.apply {
                        nameSetting.text = name
                        emailSetting.text = email
                        Picasso.get().load(photo).into(profileSetting)


                    }


                }


            }

//        open bottomsheet for see profile Login In
        binding.profileSettingBtn.setOnClickListener {

            val dialogSheet = BottomSheetDialog(this.requireContext())

            val view = layoutInflater.inflate(R.layout.profile_menu_bottom_sheet, null)

            val profileLoginImgage =
                view.findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.profile_login_img)
            val profileLoginName = view.findViewById<TextView>(R.id.profile_name_login)
            val profileLoginEmail = view.findViewById<TextView>(R.id.profile_email_login)

            val signOutBtn = view.findViewById<Button>(R.id.signout_btnn)

            signOutBtn.setOnClickListener {
                signouts()
            }



            db.collection("users")
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful && it.result != null) {

                        val user = mAuth.currentUser
                        val name = user?.displayName
                        val email = user?.email
                        val photo = user?.photoUrl
                        val number = user?.phoneNumber
                        val id = user?.uid

                        Picasso.get().load(photo).into(profileLoginImgage)
                        profileLoginName.text = name
                        profileLoginEmail.text = email


                    }

                    dialogSheet.setCancelable(true)
                    dialogSheet.setCanceledOnTouchOutside(true)

                    dialogSheet.setContentView(view)

                    dialogSheet.show()


                }


        }



    }
    private fun signouts() {
//        Toast.makeText(applicationContext, "User Signed Out", Toast.LENGTH_SHORT).show()
//        // dekhna remove krke kya change aya smj ni ara ok nxt time see
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val go = GoogleSignIn.getClient(requireActivity(), gso)



        SharedPref.putBoolean(PrefConstants.IS_USER_LOGGED_IN, false)
        go.signOut()
//        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(requireActivity(), LoginActivity::class.java))
        Toast.makeText(requireActivity(), "User Signed Out", Toast.LENGTH_SHORT).show()
        requireActivity().finish()


    }


}