package com.example.harrypotterapi.ui.character

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.harrypotterapi.R
import com.example.harrypotterapi.network.HarryPotterAPI
import com.example.harrypotterapi.repository.CharacterRepository
import com.example.harrypotterapi.ui.dashboard.Dashboard
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Character : AppCompatActivity() {

    private lateinit var etCharacterName: EditText
    private lateinit var btnSearch: Button
    private lateinit var btnBack: Button
    private lateinit var imgCharacter: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvSpecies: TextView
    private lateinit var tvHouse: TextView
    private lateinit var tvNameLabel: TextView
    private lateinit var tvSpeciesLabel: TextView
    private lateinit var tvHouseLabel: TextView
    private lateinit var tvError: TextView

    private val viewModel: CharacterViewModel by viewModels {
        CharacterViewModelFactory(
            CharacterRepository(
                Retrofit.Builder()
                    .baseUrl("https://hp-api.onrender.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(HarryPotterAPI::class.java)
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)

        etCharacterName = findViewById(R.id.etCharacterName)
        btnSearch = findViewById(R.id.btnSearch)
        btnBack = findViewById(R.id.btnBack)
        imgCharacter = findViewById(R.id.imgCharacter)
        tvName = findViewById(R.id.tvName)
        tvSpecies = findViewById(R.id.tvSpecies)
        tvHouse = findViewById(R.id.tvHouse)
        tvNameLabel = findViewById(R.id.tvNameLabel)
        tvSpeciesLabel = findViewById(R.id.tvSpeciesLabel)
        tvHouseLabel = findViewById(R.id.tvHouseLabel)
        tvError = findViewById(R.id.tvError)

        btnSearch.setOnClickListener {
            val name = etCharacterName.text.toString().trim()
            if (name.isNotEmpty()) {
                tvError.visibility = View.GONE
                hideDataViews()
                viewModel.fetchCharactersByName(name)
            } else {
                Toast.makeText(this, "Por favor, digite um nome", Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener {
            // Voltar para a dashboard (tela inicial)
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
            finish()
        }

        viewModel.characterList.observe(this) { characters ->
            if (characters != null && characters.isNotEmpty()) {
                showDataViews()
                val character = characters[0]

                tvName.text = character.name
                tvSpecies.text = character.species
                tvHouse.text = character.house ?: "N/A"

                Glide.with(this)
                    .load(character.image)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(imgCharacter)
            } else {
                tvError.text = "Nenhum personagem encontrado"
                tvError.visibility = View.VISIBLE
                hideDataViews()
            }
        }

        viewModel.error.observe(this) { errorMsg ->
            if (!errorMsg.isNullOrEmpty()) {
                tvError.text = errorMsg
                tvError.visibility = View.VISIBLE
                hideDataViews()
            }
        }
    }

    private fun hideDataViews() {
        imgCharacter.visibility = View.GONE
        tvName.visibility = View.GONE
        tvSpecies.visibility = View.GONE
        tvHouse.visibility = View.GONE
        tvNameLabel.visibility = View.GONE
        tvSpeciesLabel.visibility = View.GONE
        tvHouseLabel.visibility = View.GONE
    }

    private fun showDataViews() {
        imgCharacter.visibility = View.VISIBLE
        tvName.visibility = View.VISIBLE
        tvSpecies.visibility = View.VISIBLE
        tvHouse.visibility = View.VISIBLE
        tvNameLabel.visibility = View.VISIBLE
        tvSpeciesLabel.visibility = View.VISIBLE
        tvHouseLabel.visibility = View.VISIBLE
    }
}
