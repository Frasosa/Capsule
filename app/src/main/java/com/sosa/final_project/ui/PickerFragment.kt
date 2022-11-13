package com.sosa.final_project.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sosa.final_project.BaseApplication
import com.sosa.final_project.R
import com.sosa.final_project.adapters.PickerAdapter
import com.sosa.final_project.databinding.FragmentPickerBinding
import com.sosa.final_project.model.*

/**
 * Fragment which displays the wardrobe for the user to select items from
 * after which it will add the items to the current day's outfit
 */
class PickerFragment : Fragment() {
    // get binding
    private var _binding: FragmentPickerBinding? = null
    private val binding get() = _binding!!

    // get view models
    private val wardrobeViewModel: WardrobeViewModel by activityViewModels{
        WardrobeViewModelFactory((activity?.application as BaseApplication).database.itemDao())
    }
    private val outfitViewModel: OutfitViewModel by activityViewModels{
        OutfitViewModelFactory((activity?.application as BaseApplication).database.outfitDao())
    }

    // initialize adapter with onclick to add select items
    private val adapter by lazy { PickerAdapter{ item, selected ->
            if (!selected)
                outfitViewModel.selectItem(item)
            else
                outfitViewModel.deselectItem(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // get binding
        _binding = FragmentPickerBinding.inflate(inflater, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(false)

        // confirm onclick which adds selected items to outfit
        binding.confirmFab.setOnClickListener {
            outfitViewModel.updateOutfit()
            findNavController().navigate(R.id.action_pickerFragment_to_outfitFragment)
        }

        // cancel onclick which clears selected items and navigates back
        binding.cancelFab.setOnClickListener {
            outfitViewModel.deselectAll()
            findNavController().navigate(R.id.action_pickerFragment_to_outfitFragment)
        }

        // observe the current list of items in the wardrobe
        wardrobeViewModel.wardrobe.observe(viewLifecycleOwner) {
            wardrobeViewModel.wardrobe.value?.let { it1 -> adapter.setData(it1) }
        }

        // initialize recyclerview
        val recyclerView = binding.wardrobeRecyclerView
        recyclerView.adapter = adapter

        // Inflate the layout for this fragment
        return binding.root
    }
}