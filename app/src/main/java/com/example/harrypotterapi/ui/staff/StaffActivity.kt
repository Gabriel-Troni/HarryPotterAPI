package com.example.harrypotterapi.ui.staff

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.harrypotterapi.R
import com.example.harrypotterapi.network.HarryPotterAPI
import com.example.harrypotterapi.repository.StaffRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StaffActivity : AppCompatActivity() {

    private lateinit var etSearchStaff: EditText
    private lateinit var btnSearchStaff: Button
    private lateinit var tvResult: TextView
    private lateinit var tvError: TextView

    private val viewModel: StaffViewModel by viewModels {
        StaffViewModelFactory(
            StaffRepository(
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
        setContentView(R.layout.activity_staff)

        etSearchStaff = findViewById(R.id.etStaffName)
        btnSearchStaff = findViewById(R.id.btnSearchStaff)
        tvResult = findViewById(R.id.tvResultStaff)
        tvError = findViewById(R.id.tvErrorStaff)

        btnSearchStaff.setOnClickListener {
            val query = etSearchStaff.text.toString().trim()
            if (query.isNotEmpty()) {
                viewModel.fetchStaffByName(query)
            } else {
                Toast.makeText(this, "Digite um nome para buscar", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.matchedStaff.observe(this) { staffList ->
            val formatted = staffList.joinToString("\n\n") { staff ->
                "Nome: ${staff.name}\n" +
                        "Nomes Alternativos: ${staff.alternate_names.joinToString()}\n" +
                        "Espécie: ${staff.species}\n" +
                        "Casa: ${staff.house ?: "N/A"}"
            }
            tvResult.text = formatted
            tvResult.visibility = View.VISIBLE
        }

        viewModel.error.observe(this) { error ->
            if (!error.isNullOrEmpty()) {
                tvError.text = error
                tvError.visibility = View.VISIBLE
                tvResult.visibility = View.GONE
            } else {
                tvError.visibility = View.GONE
            }
        }
    }

    fun btnBackDashboard(view: View) {
        finish()
    }
}
