package com.example.instaclone.navigation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instaclone.navigation.model.ContentDTO
import com.example.instaclone.navigation.util.Constants.Companion.firebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor() : ViewModel() {
    private val _contentDTOList = MutableLiveData<ArrayList<ContentDTO>>()
    val contentDTOList: LiveData<ArrayList<ContentDTO>> get() = _contentDTOList
    private var contentDTOs = ArrayList<ContentDTO>() // 업로드 내용

    fun getContentList(uid : String){
        firebaseFirestore.collection("images").whereEqualTo("uid", uid)
            .addSnapshotListener { querySnapshot, firebaseFirestorException ->
                if(querySnapshot == null) return@addSnapshotListener
                contentDTOs.clear()
                for(snapshot in querySnapshot.documents){
                    contentDTOs.add(snapshot.toObject(ContentDTO::class.java)!!)
                }
                _contentDTOList.value = contentDTOs
            }
    }
}