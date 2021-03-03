package com.vkpriesniakov.redditviewer.utils

/**
 * Helper class for encapsulating repository responses according to their state.
 * Helps to observe and update application views
 */

class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?
) {

    enum class Status {
        SUCCES,
        ERROR,
        LOADING
    }

    companion object {

        fun <T> succes(data: T): Resource<T> {
            return Resource(Status.SUCCES, data, null)
        }

        fun <T> error(data: T): Resource<T> {
            return Resource(Status.ERROR, data, null)
        }

        fun <T> loading(data: T): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

    }

}