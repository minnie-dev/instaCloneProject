package com.example.instaclone.navigation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
    private var uid = ""
    private val vm: DetailViewModel by viewModels()
    private var contentDTOs: ArrayList<ContentDTO> = arrayListOf() // 업로드 내용
    private var contentUIDList: ArrayList<String> = arrayListOf() // 사용자 정보 List

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

        uid = firebaseAuth.currentUser!!.uid
        vm.getContentDTOList()
        observeDetailViewModel()

        return binding.root
    }

    private fun observeDetailViewModel() {
        vm.contentDTOList.observe(viewLifecycleOwner) {
            Log.d("DetailViewFragment", "it.size - ${it.size}")
            for (snapshot in it) {
                val item = snapshot.toObject(ContentDTO::class.java)
                contentDTOs.add(item!!)
                contentUIDList.add(snapshot.id)
            }
            binding.detailviewfragmentRecyclerview.adapter =
                DetailViewRecyclerViewAdapter(requireActivity(), contentDTOs, contentUIDList);
        }
    }
}