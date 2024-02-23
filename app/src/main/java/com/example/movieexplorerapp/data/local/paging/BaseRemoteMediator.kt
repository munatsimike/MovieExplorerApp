package com.example.movieexplorerapp.data.local.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.RemoteMediator
import com.example.movieexplorerapp.data.local.database.MovieLocalDatabase
import com.example.movieexplorerapp.data.local.repository.LocalMovieRepository
import com.example.movieexplorerapp.data.remote.repo.RemoteMovieRepository
import com.example.movieexplorerapp.domain.model.Movie

@OptIn(ExperimentalPagingApi::class)
abstract class BaseRemoteMediator(
    private val remoteRepo: RemoteMovieRepository,
    private val localMovieRepo: LocalMovieRepository,
    private val  database: MovieLocalDatabase
): RemoteMediator<Int, Movie>()