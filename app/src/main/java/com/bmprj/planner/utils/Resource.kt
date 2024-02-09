package com.bmprj.planner.utils

sealed class Resource<out R> {
    data class Success<out R>(val result: R): Resource<R>()
    data class Failure(val exception: Throwable) : Resource<Nothing>()

    object loading: Resource<Nothing>()


}