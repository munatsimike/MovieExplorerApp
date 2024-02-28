package com.example.movieexplorerapp.data.local.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.RemoteMediator
import com.example.movieexplorerapp.data.local.repository.LocalMovieRepository
import com.example.movieexplorerapp.data.remote.dto.BaseMovieApiResponse
@OptIn(ExperimentalPagingApi::class)
abstract class BaseRemoteMediator(
    private val localMovieRepo: LocalMovieRepository,
): RemoteMediator<Int, BaseMovieApiResponse>()

