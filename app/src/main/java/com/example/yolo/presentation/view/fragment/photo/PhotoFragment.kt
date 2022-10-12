package com.example.yolo.presentation.view.fragment.photo

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.yolo.R
import com.example.yolo.databinding.FragmentPhotoBinding
import com.example.yolo.presentation.architecture.BaseFragment
import jp.wasabeef.blurry.Blurry

class PhotoFragment : BaseFragment<FragmentPhotoBinding>(FragmentPhotoBinding::inflate) {

    private val args by navArgs<PhotoFragmentArgs>()
    private lateinit var menuHost: MenuHost

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        val photo = args.photo
        Glide.with(binding.root).load(photo?.urls?.full).encodeQuality(10).centerCrop()
            .into(binding.fullPhoto)

        menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.photo_fragment_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.apply {
            Blurry.with(requireContext()).radius(25).sampling(8)
                .capture(activity?.findViewById(R.id.share))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
}