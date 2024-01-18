package com.rogercompanysdavidsoriano.appcompanion

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import android.Manifest
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.remoteconfig.remoteConfig
import java.lang.RuntimeException

enum class ProviderType {
    BASIC,
    GOOGLE
}

class HomeScreen : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    private val requestNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){ isGranted ->

        onNotificationPermissionResponse(isGranted)
    }

    private lateinit var logOutButton: Button
    private lateinit var errorButton: Button
    private lateinit var saveButton: Button
    private lateinit var getButton: Button
    private lateinit var deleteButton: Button
    private lateinit var adressTextView: EditText
    private lateinit var phoneTextView: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        logOutButton = findViewById(R.id.logOutButton)
        errorButton = findViewById(R.id.errorButton)
        saveButton = findViewById(R.id.saveButton)
        getButton = findViewById(R.id.getButton)
        deleteButton = findViewById(R.id.deleteButton)
        adressTextView = findViewById(R.id.adressTextView)
        phoneTextView = findViewById(R.id.phoneTextView)

        askNotificationPermission()

        // Dades del usuari autenticat
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")

        setup(email ?: "", provider ?: "")

        // Guardar dades del usuari al fitxer strings
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()

        // Recuperar datos de remote config
        errorButton.visibility = View.INVISIBLE
        Firebase.remoteConfig.fetchAndActivate().addOnCompleteListener{ task->
            if(task.isSuccessful){
                val showErrorButton = Firebase.remoteConfig.getBoolean("show_error_button")
                val errorButtonText = Firebase.remoteConfig.getString("error_button_text")
                if(showErrorButton){
                    //Podem posar el boto d'error visible desde la consola de firebase
                    errorButton.visibility = View.VISIBLE
                }
                errorButton.text = errorButtonText
            }

        }

    }

    fun onNotificationPermissionResponse(isGranted: Boolean){
        if(isGranted){
           FirebaseMessaging.getInstance().token
               .addOnCompleteListener{ token ->
                   Log.d("Token", "Token::>" + token)
               }
               .addOnFailureListener{

               }
        }else{

        }
    }

    fun askNotificationPermission(){

        val permission = Manifest.permission.POST_NOTIFICATIONS

        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            FirebaseMessaging.getInstance().token
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val token = task.result
                        Log.d("Token", "Token::>$token")
                    } else {
                        // Manejar la falla si es necesario
                        Log.e("Token", "Error al obtener el token", task.exception)
                    }
                }
        } else if (shouldShowRequestPermissionRationale(permission)) {
            // Si las notificaciones están desactivadas, puedes redirigir al usuario a la configuración del dispositivo
        } else {
            // Puedes abrir el cuadro de diálogo de solicitud de permisos
            requestNotificationPermissionLauncher.launch(permission)
        }
    }

    private fun setup(email: String, provdirer: String){

        logOutButton.setOnClickListener{

            // Quan tanquem sessio, borrem les dades guardades del usuari
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }

        errorButton.setOnClickListener{
            // Enviem a firebase informacio del usuari que se li ha produit lerror
            FirebaseCrashlytics.getInstance().setUserId(email)
            FirebaseCrashlytics.getInstance().setCustomKey("provider", provdirer)

            // Context de l'error
            FirebaseCrashlytics.getInstance().log("Se ha pulsado el error de forzar.")

            // Forcem un error
            throw RuntimeException("Error forzado")
        }

        saveButton.setOnClickListener{

            db.collection("users").document(email).set(
                hashMapOf("provider" to provdirer,
                    "adress" to adressTextView.text.toString(),
                    "phone" to phoneTextView.text.toString())
            )
        }

        getButton.setOnClickListener{
            db.collection("users").document(email).get().addOnSuccessListener {
                adressTextView.setText(it.get("adress") as String?)
                phoneTextView.setText(it.get("phone") as String?)
            }
        }

        deleteButton.setOnClickListener{
            db.collection("users").document(email).delete()
        }
    }
}