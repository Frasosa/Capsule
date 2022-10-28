package com.sosa.final_project.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sosa.final_project.R
import com.sosa.final_project.databinding.FragmentHomeBinding
import com.sosa.final_project.model.OutfitViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: OutfitViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.apply {
//            lifecycleOwner = viewLifecycleOwner
//            viewModel = sharedViewModel
//            // initialize the AccompanimentMenuFragment variables
//            fragmentHome = this@HomeFragment
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = binding.root

        activity?.title = "Home"

        //set onclick listeners
        binding.sundayButton.setOnClickListener{
            goToNextScreen("sunday")
        }

        binding.mondayButton.setOnClickListener{
            goToNextScreen("monday")
        }

        binding.tuesdayButton.setOnClickListener{
            goToNextScreen("tuesday")
        }

        binding.wednesdayButton.setOnClickListener{
            goToNextScreen("wednesday")
        }

        binding.thursdayButton.setOnClickListener{
            goToNextScreen("thursday")
        }

        binding.fridayButton.setOnClickListener{
            goToNextScreen("friday")
        }

        binding.saturdayButton.setOnClickListener{
            goToNextScreen("saturday")
        }

        binding.wardrobe.setOnClickListener{
            sharedViewModel.entireWardrobe()
            findNavController().navigate(R.id.action_homeFragment_to_wardrobeFragment)
        }

        binding.weather.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_weatherFragment)
        }

        return root
    }

    private fun goToNextScreen(day: String) {
        sharedViewModel.setOutfit(day)
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