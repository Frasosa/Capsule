package com.sosa.final_project.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.getBitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.sosa.final_project.BaseApplication
import com.sosa.final_project.adapters.WardrobeAdapter
import com.sosa.final_project.data.Item
import com.sosa.final_project.databinding.FragmentWardrobeBinding
import com.sosa.final_project.model.WardrobeViewModel
import com.sosa.final_project.model.WardrobeViewModelFactory
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 */
class WardrobeFragment : Fragment() {
    // get binding
    private var _binding: FragmentWardrobeBinding? = null
    private val binding get() = _binding!!

    // get view model
    private val wardrobeViewModel: WardrobeViewModel by activityViewModels{
        WardrobeViewModelFactory((activity?.application as BaseApplication).database.itemDao())
    }

    // initialize recycler adapter
    private val adapter by lazy { WardrobeAdapter({ item, selected ->
        if (!selected)
            wardrobeViewModel.selectItem(item)
        else
            wardrobeViewModel.deselectItem(item)
        }, {item, ->
            wardrobeViewModel.removeItem(item)
            setHint()
        })
    }

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

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // get binding
        _binding = FragmentWardrobeBinding.inflate(inflater, container, false)
        val root = binding.root

        //setHint()

        // Functionality for expandable buttons
        binding.addFab.setOnClickListener {
            setVisibility()
            setAnimation()
            setClickable()
            clicked = !clicked
        }

        // set onclick to trash items
        binding.trashFab.setOnClickListener {
            wardrobeViewModel.trashItems()
            adapter.resetBackgrounds(binding, adapter.itemCount)
        }

        // on click for camera intent
        binding.cameraFab.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraImage.launch(intent)
        }

        // on click for gallery intent
        binding.galleryFab.setOnClickListener {
            galleryImage.launch("image/*")
        }

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

    // inserts bitmap retrieved from camera into the database and tells adapter to show it
    private val cameraImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        lifecycleScope.launch {
            val item = Item(0, it?.data?.extras?.get("data") as Bitmap)
            wardrobeViewModel.addItem(item)
        }
        wardrobeViewModel.wardrobe.observe(viewLifecycleOwner) {
            wardrobeViewModel.wardrobe.value?.let { it1 -> adapter.setData(it1) }
        }
    }

    // inserts uri retrieved from gallery into the database and tells adapter to show it
    private val galleryImage = registerForActivityResult(ActivityResultContracts.GetContent())
    { uri: Uri? ->
        uri?.let {
            //do something with uri
            lifecycleScope.launch {
                val item = Item(0, getBitmap(uri))
                wardrobeViewModel.addItem(item)
            }
            wardrobeViewModel.wardrobe.observe(viewLifecycleOwner) {
                wardrobeViewModel.wardrobe.value?.let { it1 -> adapter.setData(it1) }
            }
        }
    }

    // gets a bitmap from an uri using coil
    private suspend fun getBitmap(uri: Uri): Bitmap {
        val loading = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data(uri)
            .allowHardware(false)
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
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

    // set the visibility of the text to tell the user what to do
    private fun setHint() {
        if (wardrobeViewModel.isEmpty())
            binding.hint.visibility = View.VISIBLE
        else
            binding.hint.visibility = View.INVISIBLE
    }

}
