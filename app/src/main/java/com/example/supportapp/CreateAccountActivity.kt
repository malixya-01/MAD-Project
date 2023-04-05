package com.example.supportapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.supportapp.databinding.ActivityCreateAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAccountBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initializing auth and database variables
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()


        //Hide action bar
        try {
            this.supportActionBar!!.hide()
        } catch (_: NullPointerException) {
        }

        binding.btnCreateAcc.setOnClickListener{
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val phone = binding.etPhone.text.toString()
            val address = binding.etAddress.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPwd = binding.etConfirmPwd.text.toString()

            binding.signUpProgressbar.visibility = View.VISIBLE

            //checking if the input fields are empty
            if(name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty() || confirmPwd.isEmpty()){
                if(name.isEmpty()){
                    binding.etName.error = "Enter your name"
                }
                if(email.isEmpty()){
                    binding.etEmail.error = "Enter your email"
                }
                if(phone.isEmpty()){
                    binding.etPhone.error = "Enter your phone"
                }
                if(address.isEmpty()){
                    binding.etAddress.error = "Enter your address"
                }
                if(password.isEmpty()){
                    binding.etPassword.error = "Enter your password"
                }
                if(confirmPwd.isEmpty()){
                    binding.etConfirmPwd.error = "Re-enter your password"
                }
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show()
                binding.signUpProgressbar.visibility = View.GONE
            }
            //validate email pattern
            else if (!email.matches(emailPattern.toRegex())){
                binding.etEmail.error = "Enter a valid email address"
                binding.signUpProgressbar.visibility = View.GONE
            }




        }





    }
}