package com.example.harrypotterapi.ui.students

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.harrypotterapi.R
import com.example.harrypotterapi.network.HarryPotterAPI
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Students : AppCompatActivity() {
    private lateinit var radioGroup: RadioGroup
    private lateinit var resultTextView: TextView
    private lateinit var harryPotterAPI: HarryPotterAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_students)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        radioGroup = findViewById(R.id.rgHouses)
        resultTextView = findViewById(R.id.tvStudentList)
        val btnSearch: Button = findViewById(R.id.btnSearchStudents)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://hp-api.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        harryPotterAPI = retrofit.create(HarryPotterAPI::class.java)

        btnSearch.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            if (selectedId != -1) {
                val selectedRadio = findViewById<RadioButton>(selectedId)
                val house = selectedRadio.text.toString()
                fetchStudentsByHouse(house)
            } else {
                resultTextView.text = "Selecione uma casa."
            }
        }
        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish() // volta para o Dashboard
        }

    }

    private fun fetchStudentsByHouse(house: String) {
        lifecycleScope.launch {
            try {
                val response = harryPotterAPI.getStudentsByHouse(house)
                if (response.isSuccessful) {
                    val students = response.body() ?: emptyList()
                    val names = students.map { "• " + it.name }.joinToString("\n")
                    resultTextView.text = "Estudantes da casa $house:\n\n$names"

                } else {
                    resultTextView.text = "Erro ao buscar dados."
                }
            } catch (e: Exception) {
                resultTextView.text = "Erro: ${e.message}"
            }
        }
    }
}
