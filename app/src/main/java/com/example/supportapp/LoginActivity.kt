package com.example.supportapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.supportapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
            intent = Intent(applicationContext, createAccountActivity::class.java)
            startActivity(intent)
        }

        //set onclick listner on tvForgotPwd tv
            binding.tvForgotPwd.setOnClickListener() {
            intent = Intent(applicationContext, forgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}