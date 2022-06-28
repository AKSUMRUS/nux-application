package com.ledokol.thebestprojectever.presentation

import com.ledokol.thebestprojectever.data.repository.GamesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class GamesViewModel @Inject constructor(
    private val repository: GamesRepository
) {

}