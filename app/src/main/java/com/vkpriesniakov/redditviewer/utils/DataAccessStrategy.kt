package com.vkpriesniakov.redditviewer.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.vkpriesniakov.redditviewer.utils.Resource.Status.*
import kotlinx.coroutines.Dispatchers

fun <T> performGetOperation(
    networkCall: suspend () -> Resource<T>
): LiveData<Resource<T>> {

    return liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {

            emit(responseStatus) //TODO: check if emitting right source

            Log.i("DataAccessStrategy", responseStatus.status.name)
            Log.i("DataAccessStrategy", responseStatus.data.toString())

        } else if (responseStatus.status == ERROR) {
            emit(Resource.error(responseStatus.message!!))
        }
    }
}