package com.example.aeeloginapps.APILogin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aeeloginapps.R
import com.example.aeeloginapps.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun btnLoginOnClick(view: View) {
        val correo = binding.correo.text.toString()
        val pass = binding.pass.text.toString()
        var usuario=Usuario()
        if (ApiClient.validarUser(correo, pass,usuario)){
            startActivity(Intent(this,MainActivity::class.java).putExtra("nombre",usuario.nombre))
        }else{
            //TODO toast user incorrecto
        }
    }

    fun etARegOnClick(view: View) {
        //TODO ir a reg
    }
}