package com.ledokol.thebestproject.internet

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.ContextCompat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

val Context.currentConnectivityState: ConnectionState
    get() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return getCurrentConnectivityState(connectivityManager)
    }

private fun getCurrentConnectivityState(
    connectivityManager: ConnectivityManager
): ConnectionState {
    val connected = connectivityManager.allNetworks.any { network ->
        connectivityManager.getNetworkCapabilities(network)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }

    return if (connected) ConnectionState.Available else ConnectionState.Unavailable
}

@ExperimentalCoroutinesApi
fun Context.observeConnectivityAsFlow() = callbackFlow {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val callback = networkCallback { connectionState -> trySend(connectionState) }

    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    connectivityManager.registerNetworkCallback(networkRequest, callback)

    // Set current state
    val currentState = getCurrentConnectivityState(connectivityManager)
    trySend(currentState)

    // Remove callback when not used
    awaitClose {
        // Remove listeners
        connectivityManager.unregisterNetworkCallback(callback)
    }
}

fun networkCallback(callback: (ConnectionState) -> Unit): ConnectivityManager.NetworkCallback {
    return object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            callback(ConnectionState.Available)
        }

        override fun onLost(network: Network) {
            callback(ConnectionState.Unavailable)
        }
    }
}

//class NetworkConnection constructor(
//    private val viewModel: TodoItemsViewModel
//) {
//    fun setNetworkConnection(){
//        val connectivityManager = ContextCompat.getSystemService(
//            ToDoApplication.context,
//            ConnectivityManager::class.java
//        ) as ConnectivityManager
//
//        val networkRequest = NetworkRequest.Builder()
//            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
//            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
//            .build()
//
//        val networkCallback = object : ConnectivityManager.NetworkCallback() {
//            var validNetworkConnections : Set<Network> = emptySet()
//            var isOnline = true
//
//            override fun onAvailable(network: Network) {
//                super.onAvailable(network)
//                val networkCapability = connectivityManager.getNetworkCapabilities(network)
//                val hasNetworkConnection = networkCapability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)?:false
//                if (hasNetworkConnection){
//                    validNetworkConnections = validNetworkConnections.plusElement(network)
//                }
//                updateStatus()
//            }
//
//            override fun onCapabilitiesChanged(
//                network: Network,
//                networkCapabilities: NetworkCapabilities
//            ) {
//                super.onCapabilitiesChanged(network, networkCapabilities)
//                validNetworkConnections = if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)){
//                    validNetworkConnections.plusElement(network)
//                } else {
//                    validNetworkConnections.minusElement(network)
//                }
//                updateStatus()
//            }
//
//            override fun onLost(network: Network) {
//                super.onLost(network)
//                validNetworkConnections = validNetworkConnections.minusElement(network)
//                updateStatus()
//            }
//
//            private fun updateStatus(){
//                isOnline = if(validNetworkConnections.isNotEmpty()){
//                    if(!isOnline){
//                        viewModel.onEvent(TodoItemsEvent.UpdateTodoItems)
//                    }
//                    Log.e("INTERNET","GOT ${validNetworkConnections.size}")
//                    true
//                } else{
//                    Log.e("INTERNET","LOST")
//                    false
//                }
//            }
//        }
//
//        connectivityManager.requestNetwork(networkRequest, networkCallback)
//    }
//}