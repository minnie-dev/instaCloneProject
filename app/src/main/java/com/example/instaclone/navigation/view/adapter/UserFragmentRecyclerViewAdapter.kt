package com.example.instaclone.navigation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.instaclone.databinding.ItemUserBinding
import com.example.instaclone.navigation.model.ContentDTO
import com.google.firebase.firestore.FirebaseFirestore

class UserFragmentRecyclerViewAdapter(
    contentDTOs: ArrayList<ContentDTO>,
    uid: String,
    context: Context
) :
    RecyclerView.Adapter<UserFragmentRecyclerViewAdapter.CustomViewHolder>() {
    private var contentDTOs: ArrayList<ContentDTO>
    var uid: String
    var context: Context
    var imageUrl = ""


    init {
        this.uid = uid
        this.context = context
        this.contentDTOs = contentDTOs
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val width = context.resources.displayMetrics.widthPixels / 3 //폭의 3분의 1 값
        binding.profileImage.layoutParams = ConstraintLayout.LayoutParams(width, width)
        binding.adapter = this
        return CustomViewHolder(binding)
    }

    inner class CustomViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val position = adapterPosition
            imageUrl = contentDTOs[position].imageUrl
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return contentDTOs.size
    }
}

