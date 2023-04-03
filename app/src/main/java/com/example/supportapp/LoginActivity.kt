package com.example.supportapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
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

        //set onclick listner to login btn
        var loginBtn = findViewById<ImageView>(R.id.loginBtn)
        loginBtn.setOnClickListener() {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }





    }
}