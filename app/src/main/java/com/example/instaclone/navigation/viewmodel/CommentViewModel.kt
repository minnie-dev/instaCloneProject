package com.example.instaclone.navigation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instaclone.navigation.model.ContentDTO
import com.example.instaclone.navigation.util.Constants.Companion.firebaseFirestore
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor() : ViewModel() {
    private val _commentLiveData = MutableLiveData<ArrayList<ContentDTO.Comment>>()

    val commentLiveData: LiveData<ArrayList<ContentDTO.Comment>> get() = _commentLiveData

    private var commentList = ArrayList<ContentDTO.Comment>()

    fun getFireBaseCommentList(contentUid: String) {
        firebaseFirestore
            .collection("images")
            .document(contentUid)
            .collection("comments")
            .orderBy("timestamp")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                commentList.clear()
                if (querySnapshot == null) return@addSnapshotListener
                for (snapshot in querySnapshot.documents) {
                    commentList.add(snapshot.toObject(ContentDTO.Comment::class.java)!!)
                }
                _commentLiveData.value = commentList
            }
    }

    fun setCommentDB(contentUid: String, comment: ContentDTO.Comment) {
        firebaseFirestore.collection("images")
            .document(contentUid)
            .collection("comments")
            .document()
            .set(comment)
    }

}