package com.example.instaclone.navigation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.instaclone.databinding.ItemUserBinding
import com.example.instaclone.navigation.model.ContentDTO

class UserFragmentRecyclerViewAdapter : RecyclerView.Adapter<UserFragmentRecyclerViewAdapter.CustomViewHolder>() {
    var contentDTOs: ArrayList<ContentDTO> = arrayListOf()

    init{

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
                val width = this.root.context.resources.displayMetrics.widthPixels / 3 //폭의 3분의 1 값
                profileImage.layoutParams = ConstraintLayout.LayoutParams(width, width)
            }
        return CustomViewHolder(binding)
    }

    inner class CustomViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val position = adapterPosition
            binding.imageUrl = contentDTOs[position].imageUrl
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return contentDTOs.size
    }
}

