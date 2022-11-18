package com.example.instaclone.navigation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instaclone.navigation.model.ContentDTO
import com.example.instaclone.navigation.util.Constants.Companion.firebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GridViewModel
@Inject constructor() : ViewModel() {
    var contentDTOs = MutableLiveData<ArrayList<ContentDTO>>()

    fun getContentList() {
        firebaseFirestore.collection("images")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (querySnapshot == null) return@addSnapshotListener
                var contentDTOList = ArrayList<ContentDTO>()
                //Get data
                for (snapshot in querySnapshot.documents) {
                    contentDTOList.add(snapshot.toObject(ContentDTO::class.java)!!)
                }
                contentDTOs.value = contentDTOList
            } // 내가 올린 이미지만 내 유아이디일때만 검색
    }
}