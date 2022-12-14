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
import com.sosa.final_project.databinding.FragmentHomeBinding
import com.sosa.final_project.model.*

/**
 * Fragment to display the home screen which allows the user to pick a day of the week
 */
class HomeFragment : Fragment() {
    // init binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // get view model
    private val outfitViewModel: OutfitViewModel by activityViewModels{
        OutfitViewModelFactory((activity?.application as BaseApplication).database.outfitDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = binding.root

        //set onclick listeners for each button
        binding.sundayButton.setOnClickListener{
            goToOutfitOfTheDay("sunday")
        }

        binding.mondayButton.setOnClickListener{
            goToOutfitOfTheDay("monday")
        }

        binding.tuesdayButton.setOnClickListener{
            goToOutfitOfTheDay("tuesday")
        }

        binding.wednesdayButton.setOnClickListener{
            goToOutfitOfTheDay("wednesday")
        }

        binding.thursdayButton.setOnClickListener{
            goToOutfitOfTheDay("thursday")
        }

        binding.fridayButton.setOnClickListener{
            goToOutfitOfTheDay("friday")
        }

        binding.saturdayButton.setOnClickListener{
            goToOutfitOfTheDay("saturday")
        }

        return root
    }

    private fun goToOutfitOfTheDay(day: String) {
        outfitViewModel.setOutfit(day)
        findNavController().navigate(R.id.action_homeFragment_to_outfitFragment)
    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}