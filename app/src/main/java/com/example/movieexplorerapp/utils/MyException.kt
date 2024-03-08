package com.example.movieexplorerapp.utils

sealed class MyException : Exception() {
    data class HttpException(val code: Int, val errorMessage: String) : MyException()
    data class OtherException(val errorMessage: String) : MyException()
    data class NetworkException(val errorMessage: String) : MyException()
    data class EmptyBodyException(val errorMessage: String) : MyException()
}
