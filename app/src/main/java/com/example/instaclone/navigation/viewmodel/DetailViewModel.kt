package com.example.instaclone.navigation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instaclone.navigation.model.FollowDTO
import com.example.instaclone.navigation.util.Constants
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject constructor() : ViewModel() {
    var contentDTOList = MutableLiveData<List<DocumentSnapshot>>()

    fun getContentDTOList() {
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
                                contentDTOList.value = querySnapshot.documents
                            }
                    }
                }
            }
    }
}