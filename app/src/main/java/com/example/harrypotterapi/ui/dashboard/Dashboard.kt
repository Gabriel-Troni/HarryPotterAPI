package com.example.harrypotterapi.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.harrypotterapi.R
import com.example.harrypotterapi.ui.staff.StaffActivity
import com.example.harrypotterapi.ui.character.Character
import com.example.harrypotterapi.ui.spells.Spell
import com.example.harrypotterapi.ui.students.Students

class Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun listCharacters(view: View) {
        val intent = Intent(this, Character::class.java)
        startActivity(intent)
    }

    fun listStaff(view: View) {
        val intent = Intent(this, StaffActivity::class.java)
        startActivity(intent)
    }

    fun listStudents(view: View) {
        val intent = Intent(this, Students::class.java)
        startActivity(intent)
    }

    fun listSpells(view: View) {
        val intent = Intent(this, Spell::class.java)
        startActivity(intent)
    }

    fun exit(view: View) {
        finish()
    }

}