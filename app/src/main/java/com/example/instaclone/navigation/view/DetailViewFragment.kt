package com.example.instaclone.navigation.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.instaclone.R
import com.example.instaclone.databinding.FragmentDetailBinding
import com.example.instaclone.navigation.view.adapter.DetailViewRecyclerViewAdapter
import com.example.instaclone.navigation.model.ContentDTO
import com.example.instaclone.navigation.util.Constants.Companion.firebaseAuth
import com.example.instaclone.navigation.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailViewFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val detailVM: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )

        binding.vm = detailVM
        detailVM.getContentList()
        observeDetailViewModel()

        return binding.root
    }

    private fun observeDetailViewModel() {
        detailVM.contentDTOList.observe(viewLifecycleOwner) {
            Log.d("DetailViewFragment", "it.size - ${it.size}")
            binding.detailviewfragmentRecyclerview.adapter =
                DetailViewRecyclerViewAdapter(requireActivity())
            binding.invalidateAll()
        }
    }
}
