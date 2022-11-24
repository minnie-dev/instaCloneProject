package com.example.instaclone.navigation.view

import android.annotation.SuppressLint
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.instaclone.navigation.model.AlarmDTO
import com.example.instaclone.navigation.model.ContentDTO
import com.example.instaclone.navigation.util.Constants
import com.example.instaclone.navigation.view.adapter.AlarmRecyclerviewAdapter
import com.example.instaclone.navigation.view.adapter.DetailViewRecyclerViewAdapter
import com.example.instaclone.navigation.view.adapter.GridFragmentRecyclerViewAdapter
import com.example.instaclone.navigation.view.adapter.UserFragmentRecyclerViewAdapter


@SuppressLint("NotifyDataSetChanged")
@BindingAdapter("bindAlarmData")
fun bindAlarmList(recyclerView: RecyclerView, alarmList: ArrayList<AlarmDTO>?) {
    if (recyclerView.adapter == null) {
        recyclerView.apply {
            adapter = AlarmRecyclerviewAdapter()
        }
    }

    if (alarmList != null) {
        (recyclerView.adapter as AlarmRecyclerviewAdapter).apply {
            alarmDTOs = alarmList
            notifyDataSetChanged()
        }
    }
}

@SuppressLint("NotifyDataSetChanged")
@BindingAdapter(value = ["bindDTO", "bindUID"], requireAll = false)
fun bindContentList(
    recyclerView: RecyclerView,
    dtoList: ArrayList<ContentDTO>?,
    uidList: ArrayList<String>?
) {

    if (recyclerView.adapter == null) {
        recyclerView.apply {
            adapter = DetailViewRecyclerViewAdapter()
        }
    }
    if (dtoList != null && uidList != null) {
        (recyclerView.adapter as DetailViewRecyclerViewAdapter).apply {
            contentDTOs = dtoList
            contentUIDs = uidList
            notifyDataSetChanged()
        }
    }
}

@SuppressLint("NotifyDataSetChanged")
@BindingAdapter("bindData")
fun bindingData(recyclerView: RecyclerView, contentList: ArrayList<ContentDTO>?) {
    if (recyclerView.adapter == null) {
        recyclerView.apply {
            adapter = GridFragmentRecyclerViewAdapter()
            layoutManager = GridLayoutManager(context, 3)
        }
    }
    if (contentList != null) {
        (recyclerView.adapter as GridFragmentRecyclerViewAdapter).apply {
            contentDTOs = contentList
            notifyDataSetChanged()
        }
    }
}

@SuppressLint("NotifyDataSetChanged")
@BindingAdapter("bindUserContentList")
fun bindUserContentList(recyclerView: RecyclerView, contentList: ArrayList<ContentDTO>?){
    if(recyclerView.adapter == null){
        recyclerView.apply {
            adapter = UserFragmentRecyclerViewAdapter()
            layoutManager = GridLayoutManager(context, 3)
        }

        if(contentList != null){
            (recyclerView.adapter as UserFragmentRecyclerViewAdapter).apply {
                contentDTOs = contentList
                notifyDataSetChanged()
            }
        }
    }
}

@BindingAdapter(value = ["setImageUrl", "setGlideType"], requireAll = false)
fun setImageByGlide(view: ImageView, url: String?, type: String) {
    when (type) {
        Constants.GLIDE_CENTER -> {
            Glide.with(view.context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .apply(RequestOptions().centerCrop())
                .into(view)
        }
        Constants.GLIDE_CIRCLE -> {
            Glide.with(view.context)
                .load(url)
                .apply(RequestOptions().circleCrop())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(view)
        }

        else -> {
            Glide.with(view.context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(view)
        }


    }
}