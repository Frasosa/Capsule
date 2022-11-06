package com.sosa.final_project.ui

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sosa.final_project.adapters.WardrobeAdapter
import com.sosa.final_project.databinding.FragmentWardrobeBinding
import com.sosa.final_project.model.OutfitViewModel


/**
 * A fragment representing a list of Items.
 */
class WardrobeFragment : Fragment() {
    private var _binding: FragmentWardrobeBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: OutfitViewModel by activityViewModels()

    // initialize animations
    private val rotateOpen: Animation by lazy {AnimationUtils.loadAnimation(
        this.context, com.sosa.final_project.R.anim.rotate_open)}
    private val rotateClose: Animation by lazy {AnimationUtils.loadAnimation(
        this.context, com.sosa.final_project.R.anim.rotate_close)}
    private val fromBottom: Animation by lazy {AnimationUtils.loadAnimation(
        this.context, com.sosa.final_project.R.anim.to_bottom)}
    private val toBottom: Animation by lazy {AnimationUtils.loadAnimation(
        this.context, com.sosa.final_project.R.anim.from_bottom)}

    // keeps track of the state that the add button is in
    // initialized to false which means only the add button is viewable
    private var clicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // get binding
        _binding = FragmentWardrobeBinding.inflate(inflater, container, false)
        val root = binding.root
        sharedViewModel.entireWardrobe()

        // Functionality for expandable buttons
        binding.addFab.setOnClickListener {
            setVisibility()
            setAnimation()
            setClickable()
            clicked = !clicked
        }

        // TODO: GET DATA FROM THESE IMPLICIT INTENTS TO STORE
        binding.cameraFab.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(intent)
        }

        binding.galleryFab.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivity(intent)
        }


        // Initialize recyclerview
        val recyclerView = binding.wardrobeRecyclerView
        recyclerView.adapter = WardrobeAdapter(sharedViewModel)

        // Inflate the layout for this fragment
        return root
    }

    // helper function to animate expandable buttons
    private fun setAnimation() {
        if (!clicked) {
            binding.cameraFab.startAnimation(fromBottom)
            binding.galleryFab.startAnimation(fromBottom)
            binding.addFab.startAnimation(rotateOpen)
        } else {
            binding.cameraFab.startAnimation(toBottom)
            binding.galleryFab.startAnimation(toBottom)
            binding.addFab.startAnimation(rotateClose)
        }
    }

    // helper function to change visibility of expandable buttons
    private fun setVisibility() {
        if (!clicked) {
            binding.cameraFab.visibility = View.VISIBLE
            binding.galleryFab.visibility = View.VISIBLE
        } else {
            binding.cameraFab.visibility = View.GONE
            binding.galleryFab.visibility = View.GONE
        }
    }

    // helper function to make sure expandable buttons are not clickable when invisible
    private fun setClickable() {
        if (!clicked) {
            binding.cameraFab.isClickable = true
            binding.galleryFab.isClickable = true
        } else {
            binding.cameraFab.isClickable = false
            binding.galleryFab.isClickable = false
        }
    }

//    protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
//            val imageUri = data.data
//            imageView.setImageURI(imageUri)
//        }
//    }
}