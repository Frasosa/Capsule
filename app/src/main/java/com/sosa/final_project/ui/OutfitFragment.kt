package com.sosa.final_project.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sosa.final_project.R
import com.sosa.final_project.adapters.OutfitAdapter
import com.sosa.final_project.databinding.FragmentOutfitBinding
import com.sosa.final_project.model.OutfitViewModel

/**
 * A reusable fragment
 * that will shows the items chosen
 * in a recycler view format.
 */
class OutfitFragment : Fragment() {
    private var _binding: FragmentOutfitBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: OutfitViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //get binding
        _binding = FragmentOutfitBinding.inflate(inflater, container, false)
        val root = binding.root



        //TODO: ADD FUNCTIONALITY TO EDITING THE OUTFIT
//        binding.fab.setOnClickListener {
//            sharedViewModel.entireWardrobe()
//            findNavController().navigate(R.id.action_outfitFragment_to_wardrobeFragment)
//        }

        //Initialize recyclerview
        val recyclerView = binding.outfitRecyclerView
        recyclerView.adapter = OutfitAdapter(sharedViewModel)

        // Inflate the layout for this fragment
        return root
    }

}