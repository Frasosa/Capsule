package com.sosa.final_project.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sosa.final_project.databinding.FragmentRecyclerItemBinding
import com.sosa.final_project.model.OutfitViewModel

class WardrobeAdapter (sharedViewModel: OutfitViewModel):
    RecyclerView.Adapter<WardrobeAdapter.OutfitViewHolder>() {

    private var wardrobe = sharedViewModel.entireWardrobe()

    /**
     * Initialize view elements
     */
    class OutfitViewHolder(binding: FragmentRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        // Declare and initialize all of the list item UI components
        val image: ImageView = binding.itemImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutfitViewHolder {
        // Inflate list item layout
        val binding = FragmentRecyclerItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return OutfitViewHolder(binding)
    }

    // returns the size of the data set
    override fun getItemCount(): Int {
        return wardrobe.size
    }

    override fun onBindViewHolder(holder: OutfitViewHolder, position: Int) {
        // Get current item
        //val resources = context.resources
        val item = wardrobe[position]
        // Update the three text views and the image view for the current card
        holder.image.setImageResource(item)
    }
}