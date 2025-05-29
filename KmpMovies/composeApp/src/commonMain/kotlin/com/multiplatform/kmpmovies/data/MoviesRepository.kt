package com.multiplatform.kmpmovies.data

import com.multiplatform.kmpmovies.data.database.MoviesDao
import com.multiplatform.kmpmovies.data.remote.MoviesService
import com.multiplatform.kmpmovies.data.remote.RemoteMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class MoviesRepository(
    private val moviesService: MoviesService,
    private val moviesDao: MoviesDao,
    private val regionRepository: RegionRepository
) {

    val movies: Flow<List<Movie>> = moviesDao.fetchPopularMovies().onEach { movies ->
        if (movies.isEmpty()) {
            val popularMovies =
                moviesService.fetchPopularMovies(regionRepository.fetchRegion())
                    .results.map { it.toDomainMovie() }
            moviesDao.save(popularMovies)
        }
    }

    suspend fun fetchMovieById(id: Int): Flow<Movie?> = moviesDao.fetchMovieById(id).onEach { movie ->
        if (movie == null) {
            val remoteMovie = moviesService.fetchMovieById(id).toDomainMovie()
            moviesDao.save(listOf(remoteMovie))
        }
    }

    suspend fun toggleFavourite(movie: Movie) {
        moviesDao.save(listOf(movie.copy(isFavourite = !movie.isFavourite)))
    }
}

private fun RemoteMovie.toDomainMovie() = Movie(
    id = id,
    tittle = title,
    overview = overview,
    releaseDate = releaseDate,
    poster = "https://image.tmdb.org/t/p/w185/$posterPath",
    backdrop = backdropPath?.let { "https://image.tmdb.org/t/p/w780/$it" },
    originalTitle = originalTitle,
    originalLanguage = originalLanguage,
    popularity = popularity,
    voteAverage = voteAverage,
    isFavourite = false
)