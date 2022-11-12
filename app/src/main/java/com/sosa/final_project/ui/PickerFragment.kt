package com.sosa.final_project.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sosa.final_project.BaseApplication
import com.sosa.final_project.R
import com.sosa.final_project.adapters.PickerAdapter
import com.sosa.final_project.databinding.FragmentPickerBinding
import com.sosa.final_project.model.OutfitViewModelFactory
import com.sosa.final_project.model.OutfitViewModelWIP
import com.sosa.final_project.model.WardrobeViewModel
import com.sosa.final_project.model.WardrobeViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PickerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PickerFragment : Fragment() {
    // get binding
    private var _binding: FragmentPickerBinding? = null
    private val binding get() = _binding!!

    // get view model
    private val wardrobeViewModel: WardrobeViewModel by activityViewModels{
        WardrobeViewModelFactory((activity?.application as BaseApplication).database.itemDao())
    }
    private val outfitViewModel: OutfitViewModelWIP by activityViewModels{
        OutfitViewModelFactory((activity?.application as BaseApplication).database.outfitDao())
    }

    // initialize recycler adapter
    private val adapter by lazy { PickerAdapter{ item ->
            outfitViewModel.updateOutfit(item)
            findNavController().navigate(R.id.action_pickerFragment_to_outfitFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // get binding
        _binding = FragmentPickerBinding.inflate(inflater, container, false)
        val root = binding.root

        // observe the current list of items in the wardrobe
        wardrobeViewModel.wardrobe.observe(viewLifecycleOwner) {
            wardrobeViewModel.wardrobe.value?.let { it1 -> adapter.setData(it1) }
        }

        // Initialize recyclerview
        val recyclerView = binding.wardrobeRecyclerView
        recyclerView.adapter = adapter

        // Inflate the layout for this fragment
        return root
    }
}