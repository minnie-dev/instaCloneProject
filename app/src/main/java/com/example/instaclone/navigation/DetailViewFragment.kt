package com.example.instaclone.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.instaclone.R
import com.example.instaclone.databinding.FragmentDetailBinding
import com.example.instaclone.navigation.adapter.DetailViewRecyclerViewAdapter
import com.example.instaclone.navigation.model.ContentDTO
import com.example.instaclone.navigation.util.Constants.Companion.firebaseAuth
import com.example.instaclone.navigation.viewmodel.DetailViewModel
import com.google.firebase.firestore.DocumentSnapshot
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
        binding.detailviewfragmentRecyclerview.adapter =
            DetailViewRecyclerViewAdapter(requireActivity())
        return binding.root
    }

    private fun observeDetailViewModel(){
        vm.contentDTOList.observe(viewLifecycleOwner, Observer {
            
        })
    }
}