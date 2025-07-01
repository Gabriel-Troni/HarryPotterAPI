package com.example.harrypotterapi.ui.character

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypotterapi.R
import com.example.harrypotterapi.network.HarryPotterAPI
import com.example.harrypotterapi.repository.CharacterRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterListActivity : AppCompatActivity() {

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

    private lateinit var etSearch: EditText
    private lateinit var btnSearch: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvError: TextView
    private lateinit var adapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_list)

        etSearch = findViewById(R.id.etSearch)
        btnSearch = findViewById(R.id.btnSearch)
        recyclerView = findViewById(R.id.rvCharacters)
        tvError = findViewById(R.id.tvError)

        adapter = CharacterAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnSearch.setOnClickListener {
            val query = etSearch.text.toString().trim()
            if (query.isNotEmpty()) {
                viewModel.fetchCharactersByName(query)
            } else {
                Toast.makeText(this, "Digite um nome para buscar", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.characterList.observe(this) { characters ->
            if (characters.isNotEmpty()) {
                adapter.updateList(characters)
                recyclerView.visibility = View.VISIBLE
                tvError.visibility = View.GONE
            } else {
                recyclerView.visibility = View.GONE
                tvError.text = "Nenhum personagem encontrado."
                tvError.visibility = View.VISIBLE
            }
        }

        viewModel.error.observe(this) { errorMsg ->
            if (!errorMsg.isNullOrEmpty()) {
                tvError.text = errorMsg
                tvError.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }
        }
    }
}
