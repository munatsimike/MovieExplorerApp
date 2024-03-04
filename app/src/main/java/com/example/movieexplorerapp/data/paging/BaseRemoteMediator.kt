package com.example.movieexplorerapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.RemoteMediator
import com.example.movieexplorerapp.data.local.repository.LocalMovieRepository
import com.example.movieexplorerapp.data.model.dto.BaseMovieApiResponse
@OptIn(ExperimentalPagingApi::class)
abstract class BaseRemoteMediator(
    private val localMovieRepo: LocalMovieRepository,
): RemoteMediator<Int, BaseMovieApiResponse>()

