package com.example.instaclone.navigation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instaclone.navigation.model.ContentDTO
import com.example.instaclone.navigation.model.FollowDTO
import com.example.instaclone.navigation.util.Constants
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject constructor() : ViewModel() {
    private val _contentDTOList = MutableLiveData<ArrayList<ContentDTO>>()
    private val _contentUIDList = MutableLiveData<ArrayList<String>>()

    val contentDTOList: LiveData<ArrayList<ContentDTO>> get() = _contentDTOList
    val contentUIDList: LiveData<ArrayList<String>> get() = _contentUIDList


    private var contentDTOs = ArrayList<ContentDTO>() // 업로드 내용
    private var contentUIDs = ArrayList<String>() // 사용자 정보 list


    fun getContentList() {
        val uid = Constants.firebaseAuth.currentUser!!.uid
        Constants.firebaseFirestore.collection("users")
            .document(uid)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val userDTO = it.result.toObject(FollowDTO::class.java)
                    if (userDTO?.followings != null) {
                        Constants.firebaseFirestore
                            .collection("images")
                            .orderBy("timestamp", Query.Direction.DESCENDING)
                            .addSnapshotListener { querySnapshot, exception ->
                                if (querySnapshot == null) return@addSnapshotListener
                                for (snapshot in querySnapshot) {
                                    val item = snapshot.toObject(ContentDTO::class.java)
                                    contentDTOs.add(item)
                                    contentUIDs.add(snapshot.id)
                                }
                                _contentDTOList.value = contentDTOs
                                _contentUIDList.value = contentUIDs
                            }
                    }
                }
            }
    }
}