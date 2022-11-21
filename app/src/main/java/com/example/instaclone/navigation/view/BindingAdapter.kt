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


@SuppressLint("NotifyDataSetChanged")
@BindingAdapter("bindAlarmData")
fun bindAlarmList(recyclerView: RecyclerView, alarmList: ArrayList<AlarmDTO>?) {
    Log.d("AlarmFragment", "bindAlarmList()")

    if (recyclerView.adapter == null) {
        recyclerView.apply {
            adapter = AlarmRecyclerviewAdapter(context)
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
    Log.d("DetailViewFragment", "bindContentList()")

    if (recyclerView.adapter == null) {
        recyclerView.apply {
            adapter = DetailViewRecyclerViewAdapter(context)
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
    Log.d("GridFragment", "bindingData()");

    if (recyclerView.adapter == null) {
        recyclerView.apply {
            adapter = GridFragmentRecyclerViewAdapter(context)
            layoutManager = GridLayoutManager(context, 3)
        }
    }
    if (contentList != null) {
        (recyclerView.adapter as GridFragmentRecyclerViewAdapter).contentDTOs = contentList
        (recyclerView.adapter as GridFragmentRecyclerViewAdapter).notifyDataSetChanged()
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