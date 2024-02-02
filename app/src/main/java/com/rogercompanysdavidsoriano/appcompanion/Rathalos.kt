package com.rogercompanysdavidsoriano.appcompanion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class Rathalos : AppCompatActivity() {

    //Buttons var
    private lateinit var newsButton: ImageButton
    private lateinit var buildsButton: ImageButton
    private lateinit var settingsButton: ImageButton
    private lateinit var guideButton: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rathalos)

        //Bind button var with button designer
        newsButton = findViewById(R.id.newsButton)
        buildsButton = findViewById(R.id.buildsButton)
        settingsButton = findViewById(R.id.settingsButton)
        guideButton = findViewById(R.id.guideButton)

        //Functionality of buttons
        settingsButton.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        newsButton.setOnClickListener {
            val intent = Intent(this, NewsActivity::class.java)
            startActivity(intent)
        }

        guideButton.setOnClickListener {
            val intent = Intent(this, GuideActivity::class.java)
            startActivity(intent)
        }

    }
}