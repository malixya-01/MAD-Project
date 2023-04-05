package com.example.supportapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.supportapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initializing auth
        auth = FirebaseAuth.getInstance()

        //redirect user to the main activity if user is already logged in
        if ( auth.currentUser != null ){
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        //Hide action bar
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        //set onclick listner on login button
        binding.loginBtn.setOnClickListener() {

            binding.loginProgressbar.visibility = View.VISIBLE
            binding.etloginPasswordLayout.isPasswordVisibilityToggleEnabled = true

            val email = binding.etloginEmail.text.toString()
            val password = binding.etloginPassword.text.toString()

            //checking if the input fields are empty
            if(email.isEmpty() || password.isEmpty()){

                if(email.isEmpty()){
                    binding.etloginEmail.error = "Enter your email address"
                }
                if(password.isEmpty()){
                    binding.etloginPassword.error = "Enter your password"
                    binding.etloginPasswordLayout.isPasswordVisibilityToggleEnabled = false
                }
                binding.loginProgressbar.visibility = View.GONE

            } else if (!email.matches(emailPattern.toRegex())){
                //validate email pattern
                binding.etloginEmail.error = "Enter a valid email address"
                binding.loginProgressbar.visibility = View.GONE
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()

            } else if (password.length < 7){
                //validate passwords
                binding.etloginPassword.error = "Password must be at least 7 characters."
                binding.etloginPasswordLayout.isPasswordVisibilityToggleEnabled = false
                binding.loginProgressbar.visibility = View.GONE
                Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show()

            } else{
                //Log in
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    if(it.isSuccessful){
                        intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT).show()
                        binding.loginProgressbar.visibility = View.GONE
                    }
                }
            }
        }

        //set onclick listner on register tv
        binding.tvRegister.setOnClickListener() {
            intent = Intent(applicationContext, CreateAccountActivity::class.java)
            startActivity(intent)
        }

        //set onclick listner on tvForgotPwd tv
            binding.tvForgotPwd.setOnClickListener() {
            intent = Intent(applicationContext, forgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}