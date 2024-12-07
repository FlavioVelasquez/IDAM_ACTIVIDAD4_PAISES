package com.example.actividad4_apipaises.two

data class Paises(
    val name: Name,
    val flags: Flags
)

data class Name(val common: String)
data class Flags(val png: String)
