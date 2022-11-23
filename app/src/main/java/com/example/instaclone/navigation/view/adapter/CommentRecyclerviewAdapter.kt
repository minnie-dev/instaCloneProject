package com.example.instaclone.navigation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instaclone.databinding.ItemCommentBinding
import com.example.instaclone.navigation.model.ContentDTO
import com.example.instaclone.navigation.util.Constants.Companion.firebaseFirestore


class CommentRecyclerviewAdapter() :
    RecyclerView.Adapter<CommentRecyclerviewAdapter.CustomViewHolder>() {
    private var comments: ArrayList<ContentDTO.Comment> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding =
            ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }

    inner class CustomViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val position = adapterPosition

            binding.commentviewitemTextviewComment.text = comments[position].comment
            binding.commentviewitemTextviewProfile.text = comments[position].userId

            firebaseFirestore
                .collection("profileImages")
                .document(comments[position].uid)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        binding.imageUrl = task.result!!["image"].toString()// url주소 받아옴
                    }
                }
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return comments.size
    }

}