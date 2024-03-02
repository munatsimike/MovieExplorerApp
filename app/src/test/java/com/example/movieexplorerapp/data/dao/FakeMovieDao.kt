package com.example.movieexplorerapp.data.dao

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieexplorerapp.data.local.dao.BaseMovieDao
import com.example.movieexplorerapp.data.local.model.MovieCategory
import com.example.movieexplorerapp.data.local.model.MovieEntity

class FakeMovieDao : BaseMovieDao {
    private val allMovies = mutableListOf<MovieEntity>()
    override suspend fun insertMovies(movies: List<MovieEntity>) {
        allMovies.addAll(movies)
    }

    override fun fetchMovies(category: MovieCategory): PagingSource<Int, MovieEntity> {
        val result = allMovies.filter { movie -> movie.category == category }
        return object : PagingSource<Int, MovieEntity>() {
            override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
                return state.anchorPosition
            }

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
                return try {
                    LoadResult.Page(
                        data = result,
                        prevKey = null,
                        nextKey = null
                    )
                } catch (e: Exception) {
                    LoadResult.Error(e)
                }
            }
        }
    }

    override suspend fun deleteMovies(category: MovieCategory) {
        allMovies.removeIf { movie -> movie.category == category }
    }
}