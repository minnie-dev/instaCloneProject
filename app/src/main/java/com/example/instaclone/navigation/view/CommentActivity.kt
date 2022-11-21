package com.example.instaclone.navigation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instaclone.R
import com.example.instaclone.databinding.ActivityCommentBinding
import com.example.instaclone.navigation.view.adapter.CommentRecyclerviewAdapter
import com.example.instaclone.navigation.model.AlarmDTO
import com.example.instaclone.navigation.model.ContentDTO
import com.example.instaclone.navigation.util.Constants.Companion.DESTINATION_UID
import com.example.instaclone.navigation.util.Constants.Companion.firebaseAuth
import com.example.instaclone.navigation.util.Constants.Companion.firebaseFirestore
import com.example.instaclone.navigation.util.Constants.Companion.recyclerView_type
import com.example.instaclone.navigation.util.FcmPush

class CommentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommentBinding
    private var contentUid = " "
    private var destinationUid = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        contentUid = intent.getStringExtra("contentUid")!!
        destinationUid = intent.getStringExtra(DESTINATION_UID)!!

        binding.commentRecyclerview.adapter = CommentRecyclerviewAdapter(contentUid)
        binding.commentRecyclerview.layoutManager = LinearLayoutManager(this)

        binding.commentBtnSend.setOnClickListener {
            val comment = ContentDTO.Comment()
            comment.userId = firebaseAuth.currentUser!!.email!!
            comment.uid = firebaseAuth.currentUser!!.uid
            comment.comment = binding.commentEditMessage.text.toString()
            comment.timestamp = System.currentTimeMillis()

            //DB 저장
            firebaseFirestore.collection("images")
                .document(contentUid)
                .collection("comments")
                .document()
                .set(comment)
            //커멘트 받는 부분
            commentAlarm(destinationUid, binding.commentEditMessage.text.toString())
            binding.commentEditMessage.setText("") // 보내고 나서 edit 초기화

        }
    }

    //커멘트 달았을 때 알려주는 커멘트 알림 함수
    private fun commentAlarm(destinationUid: String, message: String) {
        val alarmDTO = AlarmDTO()
        alarmDTO.apply {
            this.destinationUid = destinationUid
            userId = firebaseAuth.currentUser!!.email!!
            uid = firebaseAuth.currentUser!!.uid
            kind = 1
            this.message = message
            timestamp = System.currentTimeMillis()
        }
        firebaseFirestore.collection("alarms").document().set(alarmDTO)

        val msg =
            firebaseAuth.currentUser!!.email + " " + resources.getString(R.string.alarm_comment) + " of " + message
        FcmPush.instance.sendMessage(destinationUid, "InstaClone", msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        recyclerView_type = false
    }
}