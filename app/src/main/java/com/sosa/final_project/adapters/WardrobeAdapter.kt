package com.sosa.final_project.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sosa.final_project.data.Item
import com.sosa.final_project.databinding.FragmentRecyclerItemBinding

class WardrobeAdapter (): RecyclerView.Adapter<WardrobeAdapter.ItemViewHolder>() {

    private var wardrobe = emptyList<Item>()

    /**
     * Initialize view elements
     */
    class ItemViewHolder(binding: FragmentRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        // Declare and initialize all of the list item UI components
        val image: ImageView = binding.itemImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // Inflate list item layout
        val binding = FragmentRecyclerItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    // returns the size of the data set
    override fun getItemCount(): Int {
        return wardrobe.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        // Get current item
        //val resources = context.resources
        val item = wardrobe[position]
        // Update the three text views and the image view for the current card
        holder.image.load(item.image)
    }

    fun setData(wardrobe: List<Item>) {
        this.wardrobe = wardrobe
        notifyDataSetChanged()
    }
}