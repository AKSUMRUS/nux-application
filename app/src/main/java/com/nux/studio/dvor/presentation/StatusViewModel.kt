package com.nux.studio.dvor.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nux.studio.dvor.data.repository.StatusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatusViewModel @Inject constructor(
    private val repository: StatusRepository
) : ViewModel() {

    fun setStatus(
        name: String,
        androidCategory: Int,
        androidPackageName: String,
    ) {
        viewModelScope.launch {
            repository.setStatus(
                androidPackageName = androidPackageName,
                name = name,
                androidCategory = androidCategory,
            )
        }
    }

    fun leaveStatus() {
        viewModelScope.launch {
            repository.leaveStatus()
        }
    }

}