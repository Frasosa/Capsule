package com.sosa.final_project.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sosa.final_project.databinding.FragmentOutfitItemBinding
import com.sosa.final_project.model.OutfitViewModel
import com.sosa.final_project.ui.OutfitFragment


class OutfitAdapter(sharedViewModel: OutfitViewModel):
    RecyclerView.Adapter<OutfitAdapter.OutfitViewHolder>() {

    private var outfit = sharedViewModel.getOutfit()

    /**
     * Initialize view elements
     */
    class OutfitViewHolder(binding: FragmentOutfitItemBinding) : RecyclerView.ViewHolder(binding.root) {
        // Declare and initialize all of the list item UI components
        val image: ImageView = binding.itemImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutfitViewHolder {
        // Inflate list item layout
        val binding = FragmentOutfitItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return OutfitViewHolder(binding)
    }

    // returns the size of the data set
    override fun getItemCount(): Int {
        return outfit.size
    }

    override fun onBindViewHolder(holder: OutfitViewHolder, position: Int) {
        // Get current item
        //val resources = context.resources
        val item = outfit[position]
        // Update the three text views and the image view for the current card
        holder.image.setImageResource(item)
    }
}