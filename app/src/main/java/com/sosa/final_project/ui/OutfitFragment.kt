package com.sosa.final_project.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.sosa.final_project.adapters.OutfitAdapter
import com.sosa.final_project.databinding.FragmentOutfitBinding
import com.sosa.final_project.model.OutfitViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OutfitFragment.newInstance] factory method to
 * create an instance of this fragment.
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
        // Inflate the layout for this fragment
        _binding = FragmentOutfitBinding.inflate(inflater, container, false)
        val root = binding.root

        //Initialize recyclerview
        val recyclerView = binding.outfitRecyclerView
        recyclerView.adapter = OutfitAdapter(this, sharedViewModel)

        return root
    }


}