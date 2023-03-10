package com.nux.studio.dvor.presentation

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nux.studio.dvor.data.local.user.User
import com.nux.studio.dvor.data.local.user.UserEvent
import com.nux.studio.dvor.data.local.user.UserState
import com.nux.studio.dvor.data.repository.UsersRepository
import com.nux.studio.dvor.presentation.error.ErrorMapper
import com.nux.studio.dvor.ui.navigation.ScreenRoutes
import com.nux.studio.dvor.ui.navigation.TAG
import com.nux.studio.dvor.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class
UserViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: UsersRepository
) : ViewModel() {

    var accessToken: String = ""
    var state by mutableStateOf(UserState())
    private val errorMapper = ErrorMapper(context)

    private var searchUser: Job? = null

    fun onEvent(event: UserEvent) {
        when (event) {
            is UserEvent.Refresh -> {
                getFriends(
                    fetchRemote = true,
                    shouldReload = event.shouldReload
                )
                state = state.copy(isRefreshing = false)
            }
            is UserEvent.GetUserByNickname -> {
                getUserByNicknameScope(nickname = event.nickname)
            }
            is UserEvent.GetUserByPhone -> {
                getUserByPhoneScope(phone = event.phone)
            }
            is UserEvent.CheckExistsNickname -> {
                checkExistsNickname(event.nickname)
            }
            is UserEvent.CheckExistsPhone -> {
                checkExistsPhone(event.phone)
            }
            is UserEvent.AddFriend -> {
                addFriend(
                    nickname = event.nickname,
                    phone = event.phone,
                )
            }
            is UserEvent.ClearFriendUser -> {
                state = state.copy(friendUser = null)
            }
            is UserEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchUser?.cancel()
                searchUser = viewModelScope.launch {
                    getUsersFindFriend()
                }
            }
            is UserEvent.OnSearchQueryChangeFindFriend -> {
                state = state.copy(searchQuery = event.query)
                searchUser?.cancel()
                searchUser = viewModelScope.launch {
                    delay(500L)
                    getFriends()
                }
            }
            is UserEvent.GetFriendUser -> {
                getUser(id = event.id)
            }
            is UserEvent.SelectUser -> {
                viewModelScope.launch {
                    val shouldRemove = checkSelectedUser(event.user)
                    if (shouldRemove) {
                        removeSelectedUser(event.user.id)
                    } else {
                        insertSelectedUser(event.user)
                    }
                }
            }
            is UserEvent.GetFriendGames -> {
                getUsersGames(event.user)
            }
            is UserEvent.OpenScreen -> {
                openScreen(event.screen)
            }
            is UserEvent.OpenFriendViaLink -> {
                openFriendViaLink(event.userId)
            }
            is UserEvent.RemoveFriend -> {
                removeFriend(event.friendId)
            }
            is UserEvent.RejectInvite -> {
                rejectInvite(event.userId)
            }
            is UserEvent.GetRecommendedFriends -> {
                getRecommendedFriends()
            }
            is UserEvent.ClearInviteFriend -> {
                clearInviteFriends()
            }
            else -> {
                Log.e("UserViewModel", "unknown event: $event")
            }
        }
    }

    private fun openScreen(
        screen: String
    ) {
        viewModelScope.launch {
            state = state.copy(openScreen = screen)
        }
    }

    private fun getUserByNicknameScope(nickname: String) {
        viewModelScope.launch {
            repository.getUserByNickname(nickname).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data.let { user ->
                            state = state.copy(friendUser = user)
                        }
                    }
                    is Resource.Loading -> {
                        state = state.copy(isLoadingUser = result.isLoading)
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            error = errorMapper.map(result.message)
                        )
                    }
                }
            }
        }
    }


    private suspend fun getUserByNickname(nickname: String) {
        repository.getUserByNickname(nickname).collect { result ->
            when (result) {
                is Resource.Success -> {
                    result.data.let { user ->
                        state = state.copy(friendUser = user)
                    }
                }

                is Resource.Loading -> {
                    state = state.copy(isLoadingUser = result.isLoading)
                }
                is Resource.Error -> {
                    state = state.copy(
                        error = errorMapper.map(result.message)
                    )
                }
            }
        }
    }

    private fun getUserByPhoneScope(phone: String) {
        viewModelScope.launch {
            repository.getUserByPhone(phone).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data.let { user ->
                            state = state.copy(
                                friendUser = user
                            )
                        }
                    }
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoadingUser = result.isLoading
                        )
                    }
                }
            }
        }
    }

    private suspend fun getUserByPhone(phone: String) {
        repository.getUserByPhone(phone).collect { result ->
            when (result) {
                is Resource.Success -> {
                    result.data.let { user ->
                        state = state.copy(
                            friendUser = user
                        )
                    }
                }

                is Resource.Loading -> {
                    state = state.copy(
                        isLoading = result.isLoading
                    )
                }
            }
        }
    }

    fun addFriend(
        nickname: String?,
        phone: String?
    ) {
        Log.e("addFriend", "start")
        viewModelScope.launch {
            val job = launch {
                try {
                    Log.e(TAG, "addFriend: try $nickname $phone")
                    if (nickname != null) {
                        getUserByNickname(nickname)
                    }
                    if (phone != null) {
                        getUserByPhone(phone)
                    }
                } finally {
                    Log.e(TAG, "addFriend: finally ${state.friendUser}")
                }
            }
            job.join()
            Log.e(TAG, "addFriend: I have passed the job")
            try {
                Log.e("addFriend", "viewModel ${state.friendUser!!.id}")
                repository.addFriend(
                    friendId = state.friendUser!!.id,
                ).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            Log.e("addFriend", "result: ${result.data.toString()}")
                        }
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                error = errorMapper.map(result.message)
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("addFriend", e.toString())
            }
        }
    }

    private fun getFriends(
        query: String = state.searchQuery.lowercase(),
        fetchRemote: Boolean = false,
        shouldReload: Boolean = true,
    ) {
        viewModelScope.launch {
            repository.getFriends(
                fetchFromRemote = fetchRemote,
                query = query,
                shouldReload = shouldReload
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data.let { users ->
                            state = state.copy(users = users)
                        }
                        Log.e("USER VIEW MODEL  GET USERS", state.toString())
                    }
                    is Resource.Error -> Unit
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }

    private fun getUsersFindFriend(
        query: String = state.searchQuery.lowercase(),
        fetchRemote: Boolean = false,
        shouldReload: Boolean = true,
    ) {

        viewModelScope.launch {
            repository.getUsersFindFriend(
                fetchFromRemote = fetchRemote,
                accessToken = accessToken,
                query = query,
                shouldReload = shouldReload
            )
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data.let { users ->
                                state = state.copy(
                                    findNewFriendsList = users,
                                )
                            }
                            Log.e("USER VIEW MODEL  GET NEW FRIENDS", state.toString())
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = result.isLoading
                            )
                        }

                    }
                }
        }

    }

    private fun getUser(
        id: String
    ) {
        viewModelScope.launch {
            repository.getUser(id = id)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data.let { user ->
                                state = state.copy(
                                    friendUser = user
                                )
                                if (user != null) {
                                    onEvent(UserEvent.GetFriendGames(user))
                                }
                            }
                            Log.e("JOB", "ended2")
                            Log.e("User View Model Friend", state.toString())
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoadingUser = result.isLoading
                            )
                        }
                    }
                }
        }
    }

    private suspend fun getUserNotAsync(
        id: String
    ) {
        repository.getUser(id = id)
            .collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data.let { user ->
                            state = state.copy(
                                friendUser = user
                            )
                            if (user != null) {
                                onEvent(UserEvent.GetFriendGames(user))
                            }
                        }
                        Log.e("User View Model Friend", state.toString())
                    }
                    is Resource.Error -> Unit
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoadingUser = result.isLoading
                        )
                    }
                }
            }
        Log.e("JOB", "ended1")
    }

    private fun openFriendViaLink(
        userId: String
    ) {
        viewModelScope.launch {
            val job = launch {
                getUserNotAsync(userId)
                Log.e("JOB", "ended2")
            }
            job.join()

            Log.e("JOB", "ended3")
            openScreen(ScreenRoutes.PREVIEW_FRIEND)
        }
    }

    private fun removeSelectedUser(selectedUser: String) {
        Log.d("SELECTEDUSER", "REMOVE")
        state = state.copy(
            inviteFriends = state.inviteFriends.toMutableList()
                .filter { user -> user != selectedUser }.toMutableList()
        )

        Log.d("SELECTEDUSER", "REMOVE " + state.users!!.size.toString())
    }

    fun insertSelectedUser(selectedUser: User) {
        state = state.copy(
            inviteFriends = state.inviteFriends.toMutableList().apply { add(selectedUser.id) }
                .toMutableList(),
        )
        Log.d("SELECTEDUSER", "INSERT " + state.users!!.size.toString())
    }

    private fun checkSelectedUser(selectedUser: User): Boolean {
        Log.d("SELECTEDUSER", "CHECK")
        var inList = false
        for (user in state.inviteFriends) {
            if (user == selectedUser.id) {
                inList = true
                break
            }
        }

        return inList
    }

    fun clearSelectedUser() {
        state = state.copy(
            inviteFriends = mutableListOf(),
        )
    }

    private fun getUsersGames(user: User) {
        viewModelScope.launch {
            repository.getUserGames(id = user.id)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data.let { games ->
                                state = state.copy(
                                    games = games,
                                )
                            }
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoadingGames = result.isLoading
                            )
                        }
                    }
                }
        }
    }

    private fun checkExistsNickname(
        nickname: String,
    ) {
        viewModelScope.launch {
            repository.checkExistsNickname(nickname = nickname).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.e("checkExistsNickname", "viewModel ${result.data.toString()}")
                        state = state.copy(
                            existsUser = result.data!!.exists
                        )
                    }
                    is Resource.Loading -> {
                        if (result.isLoading) {
                            state = state.copy(
                                existsUser = null,
                            )
                        }
                        state = state.copy(
                            isLoading = result.isLoading
                        )
                    }
                }
            }
        }
    }

    private fun checkExistsPhone(
        phone: String,
    ) {
        viewModelScope.launch {
            repository.checkExistsPhone(phone = phone)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            Log.e("checkExistsPhone", "viewModel ${result.data.toString()}")
                            state = state.copy(
                                existsUser = result.data?.exists
                            )
                        }
                        is Resource.Loading -> {
                            if (result.isLoading) {
                                state = state.copy(
                                    existsUser = null,
                                )
                            }
                            state = state.copy(
                                isLoading = result.isLoading
                            )
                        }
                    }
                }
        }
    }

    fun setNullForExistsUser() {
        viewModelScope.launch {
            state = state.copy(
                existsUser = null,
            )
        }
    }

    private fun removeFriend(
        friendId: String
    ) {
        viewModelScope.launch {
            repository.removeFriend(friendId = friendId)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            Log.e("removeFriend", "viewModel ${result.data.toString()}")
                        }
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = result.isLoading
                            )
                        }
                        is Resource.Error -> {
                            Log.e("removeFriend", errorMapper.map(result.message).toString())
                        }
                    }
                }
        }
    }

    private fun rejectInvite(
        userId: String
    ) {
        viewModelScope.launch {
            repository.rejectInvite(userId = userId)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            Log.e("rejectInvite", "viewModel ${result.data.toString()}")
                        }
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                        is Resource.Error -> Unit
                    }
                }
        }
    }

    private fun getRecommendedFriends() {
        viewModelScope.launch {
            repository.getRecommendedFriends()
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data.let { users ->
                                state = state.copy(
                                    recommendedFriends = users?.toMutableList(),
                                )
                            }
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }

    private fun clearInviteFriends() {
        state = state.copy(inviteFriends = mutableListOf())
    }
}