package com.rogercompanysdavidsoriano.appcompanion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class NewsActivity : AppCompatActivity() {
    private lateinit var guideButton: Button
    private lateinit var buildsButton: Button
    private lateinit var settingsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        guideButton = findViewById(R.id.guideButton)
        buildsButton = findViewById(R.id.buildsButton)
        settingsButton = findViewById(R.id.settingsButton)

        settingsButton.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}