package com.example.movieexplorerapp.utils.extensions

import com.example.movieexplorerapp.data.model.dto.BaseMovieApiResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object NetworkUtility {

    val gson = Gson()
    inline fun <reified T : BaseMovieApiResponse> String.isJsonMovieObject(): Boolean {
        return try {
            gson.fromJson<T>(this, object : TypeToken<T>() {}.type)
            true
        } catch (e: Exception) {
            println(e.message)
            false
        }
    }

    // convert a list of countries from json to object
    inline fun <reified T : BaseMovieApiResponse> String.jsonToMovieObject(): T {
        if (!isJsonMovieObject<T>()) {
            throw IllegalArgumentException("Provided string is not valid JSON for type ${T::class.java.simpleName}")
        }
        try {
            return gson.fromJson(this, object : TypeToken<T>() {}.type)
        } catch (e: Exception) {
            throw IllegalArgumentException("Error parsing JSON for type ${T::class.java.simpleName}: ${e.message}", e)
        }
    }


}