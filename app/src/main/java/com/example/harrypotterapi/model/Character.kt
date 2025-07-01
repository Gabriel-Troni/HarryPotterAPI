package com.example.harrypotterapi.model

data class Character(
    val id: String,
    val name: String,
    val species: String,
    val house: String?,
    val image: String,
    val alternate_names: List<String> = emptyList()
)
