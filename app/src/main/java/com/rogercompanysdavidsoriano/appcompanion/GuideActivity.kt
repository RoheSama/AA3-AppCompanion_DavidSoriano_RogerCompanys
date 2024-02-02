package com.rogercompanysdavidsoriano.appcompanion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

private const val REQUEST_CODE_IMAGE_PICK = 0
class GuideActivity : AppCompatActivity() {

    //Buttons var
    private lateinit var newsButton: ImageButton
    private lateinit var buildsButton: ImageButton
    private lateinit var settingsButton: ImageButton
    private lateinit var monsterButton: ImageButton
    private lateinit var monster1Button: ImageButton
    private lateinit var monster2Button: ImageButton
    private lateinit var rathalosButton: ImageButton
    private lateinit var questsButton: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        //Bind button var with button designer
        newsButton = findViewById(R.id.newsButton)
        buildsButton = findViewById(R.id.buildsButton)
        settingsButton = findViewById(R.id.settingsButton)
        monsterButton = findViewById(R.id.monster1)
        rathalosButton = findViewById(R.id.monster2)
        monster1Button = findViewById(R.id.monster3)
        monster2Button = findViewById(R.id.monster4)
        questsButton = findViewById(R.id.quests)

        //Functionality of buttons
        settingsButton.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        newsButton.setOnClickListener {
            val intent = Intent(this, NewsActivity::class.java)
            startActivity(intent)
        }

        //All the monster buttons go to rathalos info
        monsterButton.setOnClickListener {
            val intent = Intent(this, Rathalos::class.java)
            startActivity(intent)
        }
        monster1Button.setOnClickListener {
            val intent = Intent(this, Rathalos::class.java)
            startActivity(intent)
        }
        monster2Button.setOnClickListener {
            val intent = Intent(this, Rathalos::class.java)
            startActivity(intent)
        }
        rathalosButton.setOnClickListener {
            val intent = Intent(this, Rathalos::class.java)
            startActivity(intent)
        }
        questsButton.setOnClickListener {
            val intent = Intent(this, MissionActivity::class.java)
            startActivity(intent)
        }
    }

}