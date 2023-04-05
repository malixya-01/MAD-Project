package com.example.supportapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.supportapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //redirect user to the main activity if user is already logged in
        /*
        auth = FirebaseAuth.getInstance()
        if ( auth.currentUser != null ){

        }
        */


        //Hide action bar
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        //set onclick listner on login button
        binding.loginBtn.setOnClickListener() {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
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