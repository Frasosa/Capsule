package com.sosa.final_project.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sosa.final_project.data.DataSource.wardrobe
import com.sosa.final_project.data.Item
import com.sosa.final_project.data.Outfit
import com.sosa.final_project.databinding.FragmentRecyclerItemBinding
import com.sosa.final_project.model.OutfitViewModel
import com.sosa.final_project.ui.OutfitFragment


class OutfitAdapter(): ListAdapter<Item, OutfitAdapter.OutfitViewHolder>(DiffCallback) {

    private var outfit = mutableListOf<Item>()

    companion object DiffCallback: DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

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
        return outfit.size
    }

    override fun onBindViewHolder(holder: OutfitViewHolder, position: Int) {
        // Get current item
        //val resources = context.resources
        val item = outfit[position]
        // Update the three text views and the image view for the current card
        holder.image.load(item.image)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(outfit: Outfit) {
        this.outfit = outfit.items
        notifyDataSetChanged()
    }
}