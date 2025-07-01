package com.example.harrypotterapi.ui.character

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.harrypotterapi.R
import com.example.harrypotterapi.model.Character

class CharacterAdapter(private var characters: List<Character>) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCharacter: ImageView = itemView.findViewById(R.id.imgCharacter)
        val tvName: TextView = itemView.findViewById(R.id.tvCharacterName)
        val tvInfo: TextView = itemView.findViewById(R.id.tvCharacterInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_character, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.tvName.text = character.name
        holder.tvInfo.text = "Espécie: ${character.species} | Casa: ${character.house ?: "N/A"}"

        Glide.with(holder.itemView.context)
            .load(character.image)
            .placeholder(R.drawable.ic_placeholder)
            .into(holder.imgCharacter)
    }

    override fun getItemCount(): Int = characters.size

    fun updateList(newCharacters: List<Character>) {
        characters = newCharacters
        notifyDataSetChanged()
    }
}
