package com.example.instaclone.navigation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.instaclone.navigation.model.AlarmDTO
import com.example.instaclone.navigation.util.Constants
import com.example.instaclone.navigation.util.Constants.Companion.DESTINATION_UID
import com.example.instaclone.navigation.util.Constants.Companion.firebaseAuth
import com.example.instaclone.navigation.util.Constants.Companion.firebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor() : ViewModel() {
    private val _alarmDTOList = MutableLiveData<ArrayList<AlarmDTO>>()
    val alarmDTOList: LiveData<ArrayList<AlarmDTO>> get() = _alarmDTOList
    private var alarmDTOs = ArrayList<AlarmDTO>()

    fun getAlarmList() {
        val uid = firebaseAuth.currentUser!!.uid

        firebaseFirestore.collection("alarms")
            .whereEqualTo(DESTINATION_UID, uid)
            .addSnapshotListener { querySnapshot, exception ->
                if (querySnapshot == null) return@addSnapshotListener
                alarmDTOs.clear()
                for (snapshot in querySnapshot.documents) {
                    alarmDTOs.add(snapshot.toObject(AlarmDTO::class.java)!!)
                }
                alarmDTOs.sortByDescending { it.timestamp }
                _alarmDTOList.value = alarmDTOs
            }
    }
}