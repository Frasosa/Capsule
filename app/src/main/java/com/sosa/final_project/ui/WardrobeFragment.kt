package com.sosa.final_project.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        //TODO: ADD FUNCTIONALITY TO EDITING THE WARDROBE

        //Initialize recyclerview
        val recyclerView = binding.wardrobeRecyclerView
        recyclerView.adapter = OutfitAdapter(sharedViewModel, true)

        // Inflate the layout for this fragment
        return root
    }
}