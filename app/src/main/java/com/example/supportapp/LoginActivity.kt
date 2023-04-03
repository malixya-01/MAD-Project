package com.example.supportapp

import android.os.Bundle
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
    }
}