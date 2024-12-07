package com.example.actividad4_apipaises.two

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.actividad4_apipaises.R
import com.squareup.picasso.Picasso
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class PaisesActivity : AppCompatActivity() {

    private lateinit var paisesContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paises)

        paisesContainer = findViewById(R.id.paisesContainer)
        fetchPaises()
    }

    private fun fetchPaises() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(PaisServices::class.java)
        val call = service.getPaises()

        call.enqueue(object : Callback<List<Paises>> {
            override fun onResponse(call: Call<List<Paises>>, response: Response<List<Paises>>) {
                if (response.isSuccessful) {
                    val paisesList = response.body()
                    if (paisesList != null) {
                        displayPaises(paisesList)
                    }
                } else {
                    Log.e("PaisesActivity", "Error en la respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Paises>>, t: Throwable) {
                Log.e("PaisesActivity", "Error al obtener los datos: ${t.message}")
            }
        })
    }

    private fun displayPaises(paisesList: List<Paises>) {
        for (pais in paisesList) {
            val itemLayout = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                setPadding(16, 16, 16, 16)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }

            val textView = TextView(this).apply {
                text = pais.name.common
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f) // Ocupa el espacio restante
            }

            val imageView = ImageView(this).apply {
                layoutParams = LinearLayout.LayoutParams(150, 100)
                Picasso.get().load(pais.flags.png).into(this) // Bandera
            }

            itemLayout.addView(textView)
            itemLayout.addView(imageView)
            paisesContainer.addView(itemLayout)
        }
    }
}