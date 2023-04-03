package com.example.supportapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class createAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Hide action bar
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        setContentView(R.layout.activity_create_account)
    }
}