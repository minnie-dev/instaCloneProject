package com.example.instaclone.navigation.view.adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.instaclone.R
import com.example.instaclone.databinding.ItemGridBinding
import com.example.instaclone.navigation.view.UserFragment
import com.example.instaclone.navigation.model.ContentDTO
import com.example.instaclone.navigation.util.Constants.Companion.DESTINATION_UID

class GridFragmentRecyclerViewAdapter(context: Context) :
    RecyclerView.Adapter<GridFragmentRecyclerViewAdapter.CustomViewHolder>() {
    var contentDTOs = ArrayList<ContentDTO>()
    var context: Context

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        Log.d("GridRecyclerViewAdapter", "onCreateViewHolder()")
        val width = context.resources.displayMetrics.widthPixels / 3 //폭의 3분의 1 값
        val binding =
            ItemGridBinding.inflate(LayoutInflater.from(parent.context), parent, false).apply {
                profileImage.layoutParams = ConstraintLayout.LayoutParams(width, width)
                lifecycleOwner = root.context as LifecycleOwner
            }
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return contentDTOs.size
    }


    inner class CustomViewHolder(var binding: ItemGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            Log.d("GridRecyclerViewAdapter", "bind position - $adapterPosition")
            val position = adapterPosition
            binding.imageUrl = contentDTOs[position].imageUrl

            binding.root.setOnClickListener {
                val fragment = UserFragment()
                val bundle = Bundle()

                bundle.putString(DESTINATION_UID, contentDTOs[position].uid)
                bundle.putString("userId", contentDTOs[position].userId)

                fragment.arguments = bundle
                (context as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_content, fragment)
                    .commit()
            }
        }
    }
}