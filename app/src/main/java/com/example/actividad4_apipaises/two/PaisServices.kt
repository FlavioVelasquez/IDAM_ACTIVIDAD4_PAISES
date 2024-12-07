package com.example.actividad4_apipaises.two

import retrofit2.Call
import retrofit2.http.GET

interface PaisServices {
    @GET("${Constants.BASE_URL}${Constants.PATH_PAISES}")
    fun getPaises(): Call<List<Paises>>
}