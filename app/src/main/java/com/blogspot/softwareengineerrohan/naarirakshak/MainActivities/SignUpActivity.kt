package com.blogspot.softwareengineerrohan.naarirakshak.MainActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.blogspot.softwareengineerrohan.naarirakshak.Ui.Activities.activity.LoginActivity
import com.blogspot.softwareengineerrohan.naarirakshak.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    val binding by lazy{
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    private val firebaseAuth by lazy{
        FirebaseAuth.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.createAccountBtn.setOnClickListener {
            val email: String = binding.etEmailSignup.text.toString().trim()
val pass: String = binding.etPasswordSignup.text.toString().trim()
            val emailValidator = EmailValidator()

            if (email.isEmpty()) {
                binding.etEmailSignup.error = "Email is required"
                binding.etEmailSignup.requestFocus()
                return@setOnClickListener
            }
            if (pass.isEmpty()) {
                binding.etPasswordSignup.error = "Password is required"
                binding.etPasswordSignup.requestFocus()
                return@setOnClickListener
            }

                if (emailValidator.validate(email)) {

                    firebaseAuth.createUserWithEmailAndPassword(
                        email,
                        binding.etPasswordSignup.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT)
                                .show()
//                        Toast.makeText(this, "Valid Email Address", Toast.LENGTH_SHORT).show()

                        }
                    }
                }

        }
        binding.alreadyAccountBtn.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
    }


class EmailValidator {

    companion object {
        private const val EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    }

    private val pattern: Pattern = Pattern.compile(EMAIL_PATTERN)

    fun validate(email: String): Boolean {
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
}

//
//        binding.alreadyAccountBtn.setOnClickListener {
//            val intent = Intent(this,LoginActivity::class.java)
//            startActivity(intent)
//        }
//        binding.createAccountBtn.setOnClickListener {
//            val emailSignup = binding.etEmailSignup.text.toString()
//            val passSignup = binding.etPasswordSignup.text.toString()
//
//            if(emailSignup.isNotEmpty() && passSignup.isNotEmpty()){
//                firebaseAuth.createUserWithEmailAndPassword(emailSignup,passSignup).addOnCompleteListener {
//                    if(it.isSuccessful){
//                        Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()
//                        val intent = Intent(this, LoginActivity::class.java)
//                        startActivity(intent)
//                    }else{
//                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        }



