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
import com.sosa.final_project.databinding.FragmentRecyclerItemBinding
import com.sosa.final_project.databinding.FragmentWardrobeBinding
import com.sosa.final_project.model.WardrobeViewModel


class WardrobeAdapter (private val clickListener: (Item, Boolean) -> Unit,
                       private val longClickListener: (Item) -> Unit):
    ListAdapter<Item, WardrobeAdapter.ItemViewHolder>(DiffCallback) {

    // list of all items for adapter
    private var wardrobe = emptyList<Item>()


    // callback function
    companion object DiffCallback: DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    // initialize view elements
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

        holder.itemView.setOnClickListener {
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

        holder.itemView.setOnLongClickListener {
            longClickListener(item)
            notifyItemRemoved(position)
            true
        }

    }


    // sets the data for the adapter from the viewModel
    @SuppressLint("NotifyDataSetChanged")
    fun setData(wardrobe: List<Item>) {
        this.wardrobe = wardrobe
        notifyDataSetChanged()
    }

    // resets the backgrounds on delete so items do not look selected unintentionally
    fun resetBackgrounds(binding: FragmentWardrobeBinding, size : Int) {
        for (i in 0..size) {
            val holder = binding.wardrobeRecyclerView.findViewHolderForAdapterPosition(i)
            if (holder != null) {
                val itemViewHolder : ItemViewHolder = holder as ItemViewHolder
                itemViewHolder.itemView.setBackgroundColor(0)
                itemViewHolder.selected = false
            }
        }
    }
}