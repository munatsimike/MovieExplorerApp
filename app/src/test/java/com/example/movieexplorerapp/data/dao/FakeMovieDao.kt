package com.example.movieexplorerapp.data.dao

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieexplorerapp.data.local.dao.BaseMovieDao
import com.example.movieexplorerapp.data.local.database.DatabaseTable
import com.example.movieexplorerapp.domain.model.BaseMovieApiResponse
import com.example.movieexplorerapp.domain.model.DiscoverMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.NowPlayingMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.PopularMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.TopRatedMovieAPIResponseImp
import com.example.movieexplorerapp.domain.model.UpcomingMovieAPIResponseImp

class FakeMovieDao : BaseMovieDao {

<<<<<<< HEAD
    private val movies = mutableListOf<BaseMovieApiResponse>()
    override suspend fun insertDiscover(discoverApiResponse: DiscoverMovieAPIResponseImp) {
        movies.add(discoverApiResponse)
    }

    override suspend fun insertNowPlaying(nowPlaying: NowPlayingMovieAPIResponseImp) {
        movies.add(nowPlaying)
    }

    override suspend fun insertPopular(popular: PopularMovieAPIResponseImp) {
        movies.add(popular)
    }

    override suspend fun insertTopRated(topRated: TopRatedMovieAPIResponseImp) {
        movies.add(topRated)
    }

    override suspend fun insertUpcoming(upcoming: UpcomingMovieAPIResponseImp) {
        movies.add(upcoming)
    }

    override fun fetchDiscover(): PagingSource<Int, DiscoverMovieAPIResponseImp> {
        return  loadPagingSource()
    }

    override fun fetchNowPlaying(): PagingSource<Int, NowPlayingMovieAPIResponseImp> {
        return  loadPagingSource()}

    override fun fetchPopular(): PagingSource<Int, PopularMovieAPIResponseImp> {
        return  loadPagingSource()    }

    override fun fetchTopRated(): PagingSource<Int, TopRatedMovieAPIResponseImp> {
        return  loadPagingSource()    }

    override fun fetchUpcoming(): PagingSource<Int, UpcomingMovieAPIResponseImp> {
        return  loadPagingSource()    }

    override suspend fun clearTable(tableName: DatabaseTable) {
        movies.clear()
    }

    private fun<T: BaseMovieApiResponse> loadPagingSource(): PagingSource<Int, T> {
        return object : PagingSource<Int, T>() {
            override fun getRefreshKey(state: PagingState<Int, T>): Int? {
                return state.anchorPosition
            }

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
                return try {

                    val typedMovies: List<T> = movies as List<T>
                    LoadResult.Page(
                        data = typedMovies,
=======
    private val allMovies = mutableListOf<BaseMovieApiResponse>()
    override suspend fun insertDiscover(discoverApiResponse: DiscoverMovieAPIResponseImp) {
        allMovies.add(discoverApiResponse)
    }

    override suspend fun insertNowPlaying(nowPlaying: NowPlayingMovieAPIResponseImp) {
        allMovies.add(nowPlaying)
    }

    override suspend fun insertPopular(popular: PopularMovieAPIResponseImp) {
        allMovies.add(popular)
    }

    override suspend fun insertTopRated(topRated: TopRatedMovieAPIResponseImp) {
        allMovies.add(topRated)
    }

    override suspend fun insertUpcoming(upcoming: UpcomingMovieAPIResponseImp) {
        allMovies.add(upcoming)
    }

    override fun fetchDiscover(): PagingSource<Int, DiscoverMovieAPIResponseImp> {
        return object : PagingSource<Int, DiscoverMovieAPIResponseImp>() {
            override fun getRefreshKey(state: PagingState<Int, DiscoverMovieAPIResponseImp>): Int? {
                return state.anchorPosition
            }

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DiscoverMovieAPIResponseImp> {
                return try {

                    LoadResult.Page(
                        data = allMovies.filterIsInstance<DiscoverMovieAPIResponseImp>(),
>>>>>>> 37dcae5b8d5ecc014a17fffae75ed9b91a84b988
                        prevKey = 1,
                        nextKey = null
                    )

                } catch (e: Exception) {
                    LoadResult.Error(e)
                }
            }
        }
    }
<<<<<<< HEAD
=======

    override fun fetchNowPlaying(): PagingSource<Int, NowPlayingMovieAPIResponseImp> {
        TODO("Not yet implemented")
    }

    override fun fetchPopular(): PagingSource<Int, PopularMovieAPIResponseImp> {
        TODO("Not yet implemented")
    }

    override fun fetchTopRated(): PagingSource<Int, TopRatedMovieAPIResponseImp> {
        TODO("Not yet implemented")
    }

    override fun fetchUpcoming(): PagingSource<Int, UpcomingMovieAPIResponseImp> {
        TODO("Not yet implemented")
    }

    override suspend fun clearTable(tableName: DatabaseTable) {



    }
>>>>>>> 37dcae5b8d5ecc014a17fffae75ed9b91a84b988
}