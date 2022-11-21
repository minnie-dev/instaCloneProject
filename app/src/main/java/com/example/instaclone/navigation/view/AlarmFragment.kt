package com.example.instaclone.navigation.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instaclone.databinding.FragmentAlarmBinding
import com.example.instaclone.navigation.model.AlarmDTO
import com.example.instaclone.navigation.view.adapter.AlarmRecyclerviewAdapter
import com.example.instaclone.navigation.viewmodel.AlarmViewModel

class AlarmFragment : Fragment() {
    private lateinit var binding: FragmentAlarmBinding
    private val alarmVM: AlarmViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlarmBinding.inflate(inflater, container, false)

        binding.alarmVM = alarmVM
        alarmVM.getAlarmList()
        observeAlarmViewModel()
        return binding.root
    }

    private fun observeAlarmViewModel() {
        alarmVM.alarmDTOList.observe(viewLifecycleOwner) {
            Log.d("AlarmFragment", "it.size - ${it.size}")
            binding.alarmfragmentRecyclerview.adapter =
                AlarmRecyclerviewAdapter(requireActivity())
            binding.invalidateAll()
        }
    }
}
