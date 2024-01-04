package com.example.appcompanion.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.appcompanion.R

class MainScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener{
            val intent1 = Intent (this, LoginScreen::class.java)
            startActivity(intent1)
        }
    }
}