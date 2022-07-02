package com.ledokol.thebestprojectever.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ledokol.thebestprojectever.data.repository.StatusRepository
import com.ledokol.thebestprojectever.domain.StatusJSON
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatusViewModel @Inject constructor(
    private val repository: StatusRepository
): ViewModel() {

    fun setStatus(
    androidPackageName : String,
    name : String,
    androidCategory : String
    ){
        viewModelScope.launch {
            repository.setStatus(
                androidPackageName = androidPackageName,
                name = name,
                androidCategory = androidCategory
            )
        }
    }

    fun leaveStatus(){
        viewModelScope.launch {
            repository.leaveStatus()
        }
    }

}