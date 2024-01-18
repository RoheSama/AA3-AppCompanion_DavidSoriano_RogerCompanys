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

enum class ProviderType {
    BASIC,
    GOOGLE
}

class HomeScreen : AppCompatActivity() {

    private val requestNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){ isGranted ->

        onNotificationPermissionResponse(isGranted)
    }

    private lateinit var logOutButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        logOutButton = findViewById(R.id.logOutButton)

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
    }
}