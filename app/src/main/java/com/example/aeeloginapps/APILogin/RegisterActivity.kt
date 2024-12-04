package com.example.aeeloginapps.APILogin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aeeloginapps.R
import com.example.aeeloginapps.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun aLogin(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    fun registrarOnClick(view: View) {
        if (binding.nombre.toString().isNotBlank() && binding.correo.toString()
                .isNotBlank() && binding.pass.toString().isNotBlank()
        ) {
            val respuesta = ApiClient.crearUsuario(
                binding.nombre.toString(),
                binding.correo.toString(),
                binding.pass.toString()
            )
            if (respuesta == 200) {
                aLogin(view)
            } else {
                Toast.makeText(this, "Error: " + respuesta, Toast.LENGTH_SHORT).show()

            }
        }else{
            Toast.makeText(this, "No puedes dejar campos en blanco", Toast.LENGTH_SHORT).show()

        }
    }
}