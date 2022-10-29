package com.sosa.final_project.ui

import android.R
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sosa.final_project.adapters.OutfitAdapter
import com.sosa.final_project.databinding.FragmentWardrobeBinding
import com.sosa.final_project.model.OutfitViewModel


/**
 * A fragment representing a list of Items.
 */
class WardrobeFragment : Fragment() {
    private var _binding: FragmentWardrobeBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: OutfitViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //get binding
        _binding = FragmentWardrobeBinding.inflate(inflater, container, false)
        val root = binding.root

        //TODO: ADD FUNCTIONALITY TO EDIT THE WARDROBE


        //Initialize recyclerview
        val recyclerView = binding.wardrobeRecyclerView
        recyclerView.adapter = OutfitAdapter(sharedViewModel)

        // Inflate the layout for this fragment
        return root
    }
}