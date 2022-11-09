package com.example.instaclone.navigation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instaclone.R
import com.example.instaclone.databinding.FragmentDetailBinding
import com.example.instaclone.navigation.adapter.DetailViewRecyclerViewAdapter
import com.example.instaclone.navigation.model.ContentDTO
import com.example.instaclone.navigation.model.FollowDTO
import com.example.instaclone.navigation.util.Constants.Companion.firebaseAuth
import com.example.instaclone.navigation.util.Constants.Companion.firebaseFirestore
import com.google.firebase.firestore.Query

class DetailViewFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private var uid = ""
    private var viewModel : ViewModel()
        get() {
        }
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

        binding.detailviewfragmentRecyclerview.adapter =
            DetailViewRecyclerViewAdapter(requireActivity())
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getContentDTOList() {
        firebaseFirestore.collection("user")
            .document(uid)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val userDTO = it.result.toObject(FollowDTO::class.java)
                    if (userDTO?.followings != null) {
                        firebaseFirestore
                            .collection("images")
                            .orderBy("timestamp", Query.Direction.DESCENDING)
                            .addSnapshotListener { querySnapshot, exception ->
                                contentDTOs.clear()
                                contentUIDList.clear()
                                if (querySnapshot == null) return@addSnapshotListener
                                for (snapshot in querySnapshot.documents) {
                                    val item = snapshot.toObject(ContentDTO::class.java)
                                    contentDTOs.add(item!!)
                                    contentUIDList.add(snapshot.id)
                                }
                                binding.detailviewfragmentRecyclerview.adapter?.notifyDataSetChanged()
                            }
                    }
                }
            }
    }
}