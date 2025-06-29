package com.example.harrypotterapi.ui.spells

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypotterapi.R
import com.example.harrypotterapi.network.HarryPotterAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Spell : AppCompatActivity() {
    private lateinit var harryPotterAPI: HarryPotterAPI
    private lateinit var recyclerView: RecyclerView
    private lateinit var spellAdapter: SpellAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_spell)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuração do RecyclerView
        recyclerView = findViewById(R.id.rvSpells)
        spellAdapter = SpellAdapter(emptyList()) { selectedSpell ->
            val intent = Intent(this, SpellDetail::class.java).apply {
                putExtra("id", selectedSpell.id)
                putExtra("name", selectedSpell.name)
                putExtra("description", selectedSpell.description)
            }
            startActivity(intent)
        }
        recyclerView.adapter = spellAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Configuração da API
        val retrofit = Retrofit.Builder()
            .baseUrl("https://hp-api.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        harryPotterAPI = retrofit.create(HarryPotterAPI::class.java)

        // Apresentação dos feitiços
        lifecycleScope.launch {
            val spellsResponse = harryPotterAPI.getSpells()
            if (spellsResponse.isSuccessful) {
                val spellsList = spellsResponse.body() ?: emptyList()
                spellAdapter.updateData(spellsList)
            } else {
                spellAdapter.updateData(emptyList())
            }
        }
    }

    fun exit(view: View) {
        finish()
    }
}