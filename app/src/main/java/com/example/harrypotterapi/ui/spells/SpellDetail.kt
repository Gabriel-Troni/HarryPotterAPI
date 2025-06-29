package com.example.harrypotterapi.ui.spells

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.harrypotterapi.R

class SpellDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_spell_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val spellName = intent.getStringExtra("name")
        val spellDescription = intent.getStringExtra("description")

        findViewById<TextView>(R.id.tvSelectedSpellTitle).text = "Feitiço: $spellName"
        findViewById<TextView>(R.id.tvSelectedSpellDescription).text = spellDescription
    }

    fun exit(view: View) {
        finish()
    }
}