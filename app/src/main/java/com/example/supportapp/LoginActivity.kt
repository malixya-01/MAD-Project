package com.example.supportapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Hide action bar
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        setContentView(R.layout.activity_login)

        //set onclick listner on login button
        var loginBtn = findViewById<ImageView>(R.id.loginBtn)
        loginBtn.setOnClickListener() {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        //set onclick listner on register tv
        var tvRegister = findViewById<TextView>(R.id.tvRegister)
        tvRegister.setOnClickListener() {
            intent = Intent(applicationContext, createAccountActivity::class.java)
            startActivity(intent)
        }

        //set onclick listner on tvForgotPwd tv
        var tvForgotPwd = findViewById<TextView>(R.id.tvForgotPwd)
        tvForgotPwd.setOnClickListener() {
            intent = Intent(applicationContext, forgotPasswordActivity::class.java)
            startActivity(intent)
        }







    }
}