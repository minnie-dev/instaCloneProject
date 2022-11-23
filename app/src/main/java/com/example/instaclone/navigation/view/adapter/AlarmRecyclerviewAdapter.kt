package com.example.instaclone.navigation.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instaclone.R
import com.example.instaclone.databinding.ItemCommentBinding
import com.example.instaclone.navigation.model.AlarmDTO
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("NotifyDataSetChanged")
class AlarmRecyclerviewAdapter(context: Context) :
    RecyclerView.Adapter<AlarmRecyclerviewAdapter.CustomViewHolder>() {
    var alarmDTOs: ArrayList<AlarmDTO> = arrayListOf() //알람 저장하는 리스트 변수
    private var context: Context

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }

    inner class CustomViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val position = adapterPosition
            FirebaseFirestore.getInstance().collection("profilesImages")
                .document(alarmDTOs[position].uid)
                .get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        binding.imageUrl = it.result["image"].toString()
                    }
                }
            when (alarmDTOs[position].kind) {
                0 -> {
                    var str_0 =
                        alarmDTOs[position].userId + context.resources.getString(R.string.alarm_favorite)
                    binding.commentviewitemTextviewProfile.text = str_0
                }//좋아요 이벤트 알람
                1 -> {
                    var str_1 = alarmDTOs[position].userId + " " +
                            context.resources.getString(R.string.alarm_comment) + " of " + alarmDTOs[position].message
                    binding.commentviewitemTextviewProfile.text = str_1
                }//코멘트 이벤트 알람
                2 -> {
                    var str_2 =
                        alarmDTOs[position].userId + " " + context.resources.getString(R.string.alarm_follow)
                    binding.commentviewitemTextviewProfile.text = str_2
                }//팔로우 이벤트 알람
            }
            binding.commentviewitemTextviewComment.visibility = View.INVISIBLE
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return alarmDTOs.size
    }

}