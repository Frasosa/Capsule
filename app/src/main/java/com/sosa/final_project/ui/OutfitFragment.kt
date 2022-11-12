package com.sosa.final_project.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sosa.final_project.BaseApplication
import com.sosa.final_project.R
import com.sosa.final_project.adapters.OutfitAdapter
import com.sosa.final_project.data.Outfit
import com.sosa.final_project.databinding.FragmentOutfitBinding
import com.sosa.final_project.model.OutfitViewModel
import com.sosa.final_project.model.OutfitViewModelFactory

/**
 * A reusable fragment
 * that will shows the items chosen
 * in a recycler view format.
 */
class OutfitFragment : Fragment() {
    // init binding
    private var _binding: FragmentOutfitBinding? = null
    private val binding get() = _binding!!

    // gets view model
    private val outfitViewModel: OutfitViewModel by activityViewModels{
        OutfitViewModelFactory((activity?.application as BaseApplication).database.outfitDao())
    }

    // TODO: init adapter with onclick
    private val adapter by lazy {OutfitAdapter()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //get binding
        _binding = FragmentOutfitBinding.inflate(inflater, container, false)

        // sets onclick for button to navigate to picker
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_outfitFragment_to_pickerFragment)
        }

        // observe the data for recycler view
        outfitViewModel.currentOutfit.observe(viewLifecycleOwner) {
            if (outfitViewModel.currentOutfit.value != null) {
                adapter.setData(outfitViewModel.currentOutfit.value!!)
            } else {
                adapter.setData(Outfit(outfitViewModel.currentDay, mutableListOf()))
            }
        }

        // initialize recyclerview
        val recyclerView = binding.outfitRecyclerView
        recyclerView.adapter = adapter

        // Inflate the layout for this fragment
        return binding.root
    }

}