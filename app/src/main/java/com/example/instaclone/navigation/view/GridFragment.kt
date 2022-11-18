package com.example.instaclone.navigation.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instaclone.databinding.FragmentGridBinding
import com.example.instaclone.navigation.view.adapter.GridFragmentRecyclerViewAdapter
import com.example.instaclone.navigation.model.ContentDTO
import com.example.instaclone.navigation.viewmodel.GridViewModel

class GridFragment : Fragment() {
    lateinit var binding: FragmentGridBinding
    private val gridVM: GridViewModel by viewModels()
    lateinit var contentDTOsList: ArrayList<ContentDTO>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGridBinding.inflate(inflater, container, false)
        binding.vm = gridVM
        gridVM.getContentList()
        observeGridViewModel()
        return binding.root
    }

    private fun observeGridViewModel() {
        gridVM.contentDTOs.observe(viewLifecycleOwner) {
            Log.d("GridFragment", "observeGridViewModel()");
            binding.gridfragmentRecyclerview.adapter =
                GridFragmentRecyclerViewAdapter(requireActivity())
            binding.gridfragmentRecyclerview.layoutManager = GridLayoutManager(requireActivity(), 3)
            contentDTOsList = it
            binding.invalidateAll()
        }
    }
}


@SuppressLint("NotifyDataSetChanged")
@BindingAdapter("bindData")
fun bindingData(recyclerView: RecyclerView, contentList: ArrayList<ContentDTO>?) {
    Log.d("GridFragment", "bindingData()");

    if (recyclerView.adapter == null) {
        recyclerView.apply {
            adapter = GridFragmentRecyclerViewAdapter(context)
            layoutManager = GridLayoutManager(context, 3)
        }
    }
    if (contentList != null) {
        (recyclerView.adapter as GridFragmentRecyclerViewAdapter).contentDTOs = contentList
        (recyclerView.adapter as GridFragmentRecyclerViewAdapter).notifyDataSetChanged()
    }
}