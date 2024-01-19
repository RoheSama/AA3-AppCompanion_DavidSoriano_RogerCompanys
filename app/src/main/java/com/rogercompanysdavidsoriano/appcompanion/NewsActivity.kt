package com.rogercompanysdavidsoriano.appcompanion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class NewsActivity : AppCompatActivity() {
    private lateinit var guideButton: ImageButton
    private lateinit var buildsButton: ImageButton
    private lateinit var settingsButton: ImageButton
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