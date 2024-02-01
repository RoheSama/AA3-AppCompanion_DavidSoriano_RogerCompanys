package com.rogercompanysdavidsoriano.appcompanion

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val REQUEST_CODE_IMAGE_PICK = 0

class NewsActivity : AppCompatActivity() {
    private lateinit var guideButton: ImageButton
    private lateinit var buildsButton: ImageButton
    private lateinit var settingsButton: ImageButton
    private lateinit var uploadImageButton: AppCompatButton
    private lateinit var chooseButton: AppCompatButton
    private lateinit var ivImage: ImageView

    var curFile: Uri? = null

    val imageRef = Firebase.storage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        guideButton = findViewById(R.id.guideButton)
        buildsButton = findViewById(R.id.buildsButton)
        settingsButton = findViewById(R.id.settingsButton)

        uploadImageButton = findViewById(R.id.image_button)
        chooseButton = findViewById(R.id.choose_button2)
        ivImage = findViewById(R.id.av_image)

        chooseButton.setOnClickListener{
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                startActivityForResult(it, REQUEST_CODE_IMAGE_PICK)
            }
        }

        // Donem de nom la data i hora actual als arxius que anem pujant
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val filename = formatter.format(now)

        uploadImageButton.setOnClickListener{
            uploadImageToStorage(filename)
        }

        settingsButton.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private  fun uploadImageToStorage(filename: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            curFile?.let {
                imageRef.child("images/$filename").putFile(it).await()
                withContext(Dispatchers.Main){
                    Toast.makeText(this@NewsActivity, "Succesfully uploaded image",
                        Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(this@NewsActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_IMAGE_PICK){
            data?.data.let {
                curFile = it
                ivImage.setImageURI(it)
            }
        }
    }
}