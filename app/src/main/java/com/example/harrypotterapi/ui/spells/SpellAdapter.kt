package com.example.harrypotterapi.ui.spells

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.harrypotterapi.R
import com.example.harrypotterapi.model.Spell

class SpellAdapter(private var spells: List<Spell>, private val onItemClick: (Spell) -> Unit) :
    RecyclerView.Adapter<SpellAdapter.SpellViewHolder>() {

    inner class SpellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val spellName: TextView = itemView.findViewById(R.id.tvSpellTitle)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(spells[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpellViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_spell, parent, false)
        return SpellViewHolder(itemView)
    }

    // Substitui o conteúdo de uma view (invocado pelo layout manager)
    override fun onBindViewHolder(holder: SpellViewHolder, position: Int) {
        val currentSpell = spells[position]

        holder.spellName.text = currentSpell.name
    }

    override fun getItemCount() = spells.size

    fun updateData(newSpells: List<Spell>) {
        spells = newSpells
        notifyDataSetChanged()
    }
}