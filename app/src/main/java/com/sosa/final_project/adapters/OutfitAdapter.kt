package com.sosa.final_project.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sosa.final_project.data.Item
import com.sosa.final_project.data.Outfit
import com.sosa.final_project.data.converters.OutfitConverter
import com.sosa.final_project.databinding.FragmentOutfitBinding
import com.sosa.final_project.databinding.FragmentRecyclerItemBinding


class OutfitAdapter(private val clickListener: (String, Boolean) -> Unit):
    ListAdapter<Item, OutfitAdapter.OutfitViewHolder>(DiffCallback) {

    private var outfit = emptyList<String>()

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
        var selected = false
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
        // load the image and set onclick for removal
        holder.image.load(OutfitConverter.StringToBitMap(item))
        holder.itemView.setOnClickListener{
            // if selected turn background green to indicate
            if (!holder.selected)
                holder.itemView.setBackgroundColor(Color.GREEN)
            // if unselected reset background
            else
                holder.itemView.setBackgroundColor(0)
            clickListener(item, holder.selected)
            // invert value of selected for next click
            holder.selected = !holder.selected
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(outfit: Outfit) {
        this.outfit = outfit.items
        notifyDataSetChanged()
    }

    // resets the backgrounds on delete so items do not look selected unintentionally
    fun resetBackgrounds(binding: FragmentOutfitBinding, size : Int) {
        for (i in 0..size) {
            val holder = binding.outfitRecyclerView.findViewHolderForAdapterPosition(i)
            if (holder != null) {
                val itemViewHolder : OutfitViewHolder = holder as OutfitViewHolder
                itemViewHolder.itemView.setBackgroundColor(0)
                itemViewHolder.selected = false
            }
        }
    }
}