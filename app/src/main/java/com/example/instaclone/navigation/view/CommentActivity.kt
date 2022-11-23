package com.example.instaclone.navigation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instaclone.R
import com.example.instaclone.databinding.ActivityCommentBinding
import com.example.instaclone.navigation.view.adapter.CommentRecyclerviewAdapter
import com.example.instaclone.navigation.model.AlarmDTO
import com.example.instaclone.navigation.model.ContentDTO
import com.example.instaclone.navigation.util.Constants.Companion.DESTINATION_UID
import com.example.instaclone.navigation.util.Constants.Companion.firebaseAuth
import com.example.instaclone.navigation.util.Constants.Companion.firebaseFirestore
import com.example.instaclone.navigation.util.FcmPush
import com.example.instaclone.navigation.viewmodel.CommentViewModel

class CommentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommentBinding
    private var contentUid = " "
    private var destinationUid = " "
    private val commentVM: CommentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        contentUid = intent.getStringExtra("contentUid")!!
        destinationUid = intent.getStringExtra(DESTINATION_UID)!!

        commentVM.getFireBaseCommentList(contentUid)
        observeCommentViewModel()

        binding.commentBtnSend.setOnClickListener {

            ContentDTO.Comment().apply {
                userId = firebaseAuth.currentUser!!.email!!
                uid = firebaseAuth.currentUser!!.uid
                this.comment = binding.commentEditMessage.text.toString()
                timestamp = System.currentTimeMillis()

                //DB 저장
                commentVM.setCommentDB(contentUid, this)
            }

            //커멘트 받는 부분
            commentAlarm(destinationUid, binding.commentEditMessage.text.toString())
            binding.commentEditMessage.setText("") // 보내고 나서 edit 초기화
        }
    }

    private fun observeCommentViewModel() {
        commentVM.commentLiveData.observe(this) {
            Log.d("CommentActivity", "it.size - ${it.size}")
            binding.commentRecyclerview.adapter =
                CommentRecyclerviewAdapter()
        }
    }

    //커멘트 달았을 때 알려주는 커멘트 알림 함수
    private fun commentAlarm(destinationUid: String, message: String) {
        AlarmDTO().apply {
            this.destinationUid = destinationUid
            userId = firebaseAuth.currentUser!!.email!!
            uid = firebaseAuth.currentUser!!.uid
            kind = 1
            this.message = message
            timestamp = System.currentTimeMillis()
            firebaseFirestore.collection("alarms").document().set(this)
        }

        val msg =
            firebaseAuth.currentUser!!.email + " " + resources.getString(R.string.alarm_comment) + " of " + message
        FcmPush.instance.sendMessage(destinationUid, "InstaClone", msg)
    }
}