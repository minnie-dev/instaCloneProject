package com.example.instaclone.navigation.view.adapter

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.instaclone.R
import com.example.instaclone.navigation.UserFragment
import com.example.instaclone.navigation.model.ContentDTO
import com.example.instaclone.navigation.util.Constants.Companion.DESTINATION_UID
import com.example.instaclone.navigation.util.Constants.Companion.firebaseFirestore

class GridFragmentRecyclerViewAdapter(context: Context) :
    RecyclerView.Adapter<GridFragmentRecyclerViewAdapter.CustomViewHolder>() {
    var contentDTOs = ArrayList<ContentDTO>()
    var context: Context

    init {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val width = context.resources.displayMetrics.widthPixels / 3 //폭의 3분의 1 값
        val imageview = ImageView(parent.context)
        imageview.layoutParams = ConstraintLayout.LayoutParams(width, width)
        return CustomViewHolder(imageview)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return contentDTOs.size
    }


    inner class CustomViewHolder(var imageview: ImageView) :
        RecyclerView.ViewHolder(imageview) {
        fun bind() {
            val position = adapterPosition
            val imageView = imageview
            Glide.with(itemView.context)
                .load(contentDTOs[position].imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .apply(RequestOptions().centerCrop())
                .into(imageView)

            imageView.setOnClickListener {
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