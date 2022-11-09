package com.sosa.final_project.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.Image
import android.media.MediaMetadataRetriever.BitmapParams
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.getBitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.sosa.final_project.R
import com.sosa.final_project.adapters.WardrobeAdapter
import com.sosa.final_project.data.Item
import com.sosa.final_project.databinding.FragmentWardrobeBinding
import com.sosa.final_project.model.OutfitViewModel
import com.sosa.final_project.model.WardrobeViewModel
import kotlinx.coroutines.launch
import java.io.File

/**
 * A fragment representing a list of Items.
 */
class WardrobeFragment : Fragment() {
    private var _binding: FragmentWardrobeBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: OutfitViewModel by activityViewModels()
    private val wardrobeViewModel: WardrobeViewModel by activityViewModels()

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

    //IMPLICIT ACTIVITIES
//    private val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
//        if (isSuccess) {
//            latestTmpUri?.let { uri ->
//                //do something with uri
//                previewImage.setImageURI(uri)
//            }
//        }
//    }

    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent())
    { uri: Uri? ->
        uri?.let {
            //do something with uri
            lifecycleScope.launch {
                wardrobeViewModel.addItem(Item(id, getBitmap(uri)))
            }
        }
    }



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
//            lifecycleScope.launchWhenStarted {
//                getTmpFileUri().let { uri ->
//                    latestTmpUri = uri
//                    takeImageResult.launch(uri)
//                }
//            }
        }

        binding.galleryFab.setOnClickListener {
            selectImageFromGalleryResult.launch("image/*")
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

    private suspend fun getBitmap(uri: Uri): Bitmap {
        val loading: ImageLoader? = this.context?.let { ImageLoader(it) }
        val request: ImageRequest? = this.context?.let {
            ImageRequest.Builder(it)
                .data(uri)
                .build()
        }

        val result: Drawable = (request?.let { loading?.execute(it) } as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }
//    protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
//            val imageUri = data.data
//            imageView.setImageURI(imageUri)
//        }
//    }


}