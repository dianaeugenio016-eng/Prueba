package com.example.universidad

import android.os.Binder
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.universidad.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    val db = Firebase.firestore
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnGuardar.setOnClickListener {
            val clave = binding.etClave.text.toString()
            val nombre = binding.etNombre.text.toString()
            val telefono = binding.etTelefono.text.toString()
            val lic = binding.etLicenciatura.text.toString()

            db.collection("empleados").document(clave)
                .set(hashMapOf("Nombre" to nombre, "Telefono" to telefono, "Licenciatura" to lic))

        }

        binding.btnConsultar.setOnClickListener {
            val clave = binding.etClave.text.toString()
            db.collection("empleados").document(clave)
                .get().addOnSuccessListener {
                    binding.etNombre.setText(it.get("Nombre") as String?)
                    binding.etTelefono.setText(it.get("Telefono")as String?)
                    binding.etLicenciatura.setText(it.get("Licenciatura")as String?)
                }
        }
        binding.btnEliminar.setOnClickListener {
            val clave = binding.etClave.text.toString()
            db.collection("empleados").document(clave).delete()
        }
    }
}