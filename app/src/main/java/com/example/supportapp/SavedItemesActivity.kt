package com.example.supportapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SavedItemesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_itemes)

        supportActionBar?.title = "Saved Items"
    }
}