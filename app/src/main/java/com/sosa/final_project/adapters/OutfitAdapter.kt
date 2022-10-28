package com.sosa.final_project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sosa.final_project.R
import com.sosa.final_project.ui.OutfitFragment

class OutfitAdapter(private val context: OutfitFragment, _outfit: MutableList<Int>):
    RecyclerView.Adapter<OutfitAdapter.OutfitViewHolder>() {
//
//    private val outfit = _outfit
//
//    /**
//     * Initialize view elements
//     */
//    class OutfitViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        // Declare and initialize all of the list item UI components
//        val image: ImageView = view.findViewById(R.id.pokemon_image)
//        val typeSymbol: ImageView = view.findViewById(R.id.pokemon_type)
//        val name: TextView = view.findViewById(R.id.name)
//        val type: TextView = view.findViewById(R.id.type)
//        val attack: TextView = view.findViewById(R.id.attack)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutfitViewHolder {
//        // Inflate list item layout
//        val adapterLayout = LayoutInflater.from(parent.context).
//        inflate(R.layout.pokedex_list_item, parent, false)
//        return PokemonCardViewHolder(adapterLayout)
//    }
//
//    // returns the size of the data set
//    override fun getItemCount(): Int {
//        return outfit.size
//    }
//
//    override fun onBindViewHolder(holder: PokemonCardViewHolder, position: Int) {
//        // Get current item
//        val resources = context?.resources
//        val poke = pokemonList[position]
//        // Update the three text views and the image view for the current card
//        holder.image.setImageResource(poke.imageResourceId)
//        holder.typeSymbol.setImageResource(poke.typeResourceId)
//        holder.name.text = poke.name
//        holder.type.text = poke.type
//        holder.attack.text = resources?.getString(R.string.attack, poke.attack)
//    }

}