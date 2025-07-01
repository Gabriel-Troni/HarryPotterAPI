package com.example.harrypotterapi.model

data class Staff(
    val name: String,
    val alternate_names: List<String>,
    val species: String,
    val house: String?
)
