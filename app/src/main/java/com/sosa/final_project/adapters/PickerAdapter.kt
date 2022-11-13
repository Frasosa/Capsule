package com.sosa.final_project.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sosa.final_project.data.Item
import com.sosa.final_project.databinding.FragmentRecyclerItemBinding


class PickerAdapter (private val clickListener: (Item, Boolean) -> Unit):
    ListAdapter<Item, PickerAdapter.ItemViewHolder>(DiffCallback) {

    // list of items for the adapter to display
    private var wardrobe = emptyList<Item>()


    // callback
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
    class ItemViewHolder(binding: FragmentRecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        // Declare and initialize all of the list item UI components
        val image: ImageView = binding.itemImage
        var selected = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // inflate list item layout
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
        val item = wardrobe[position]
        // load the image and set onclick listener for selection logic
        holder.image.load(item.image)
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

    fun setData(wardrobe: List<Item>) {
        this.wardrobe = wardrobe
        notifyDataSetChanged()
    }

}