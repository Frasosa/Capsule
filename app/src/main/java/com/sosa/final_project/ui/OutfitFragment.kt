package com.sosa.final_project.ui

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.sosa.final_project.BaseApplication
import com.sosa.final_project.R
import com.sosa.final_project.adapters.OutfitAdapter
import com.sosa.final_project.data.Item
import com.sosa.final_project.data.Outfit
import com.sosa.final_project.databinding.FragmentOutfitBinding
import com.sosa.final_project.model.OutfitViewModel
import com.sosa.final_project.model.OutfitViewModelFactory
import com.sosa.final_project.model.OutfitViewModelWIP

/**
 * A reusable fragment
 * that will shows the items chosen
 * in a recycler view format.
 */
class OutfitFragment : Fragment() {
    private var _binding: FragmentOutfitBinding? = null
    private val binding get() = _binding!!
    private val outfitViewModel: OutfitViewModelWIP by activityViewModels{
        OutfitViewModelFactory((activity?.application as BaseApplication).database.outfitDao())
    }

    private val adapter by lazy {OutfitAdapter()}

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
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_outfitFragment_to_pickerFragment)
        }

        outfitViewModel.mondayLiveData.observe(viewLifecycleOwner) {
            if (outfitViewModel.mondayLiveData.value != null) {
                println("MADE IT HERE")
                adapter.setData(outfitViewModel.mondayLiveData.value!!)
            } else {
                println("NO MONDAY YET")
                adapter.setData(Outfit("monday", mutableListOf<String>()))
            }
        }

        //Initialize recyclerview
        val recyclerView = binding.outfitRecyclerView
        recyclerView.adapter = adapter

        // Inflate the layout for this fragment
        return root
    }

}