package com.example.movieexplorerapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.movieexplorerapp.data.model.dto.Movie
import com.example.movieexplorerapp.ui.components.ImageSize
import com.example.movieexplorerapp.ui.components.NetworkImageLoader

/**
 * This contains code to display the movie detail page.
 * The detail page displays movie poster release date and overview
 */
object Detail {
    @Composable
    fun Screen(movie: Movie, onClick: () -> Unit) {
        MovieDetailPage(movie = movie) {
            onClick()
        }
    }

    /**
     * Displays the movie details within a custom dialog box. This dialog box is non-dismissible by clicking outside,
     * ensuring the user must interact with the content directly to dismiss it. It presents a detailed view with the movie's
     * poster, title, release date, and an overview in a visually appealing layout. The background poster image is loaded first,
     * with a semi-transparent overlay containing text details to ensure readability.
     *
     * @param movie The Movie object containing details like posterPath, title, release date, and overview.
     * @param onClick A lambda function to execute when the dialog is dismissed, typically used to handle the dialog closure.
     */
    @Composable
    private fun MovieDetailPage(movie: Movie, onClick: () -> Unit) {
        Dialog(
            onDismissRequest = { onClick() }, properties = DialogProperties(
                dismissOnClickOutside = false, usePlatformDefaultWidth = false
            )
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
                .clip(RoundedCornerShape(5.dp))
                .clickable { onClick() }

            ) {
                // loads movie poster as background
                NetworkImageLoader(imageUrl = movie.posterPath, Modifier.fillMaxSize())
               // box contains code to display movie overview title, release date etc
                Box(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .alpha(0.9f)
                        .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .align(Alignment.BottomCenter)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                        // display movie poster on the detail section
                            NetworkImageLoader(
                                imageUrl = movie.posterPath,
                                imageSize = ImageSize.ExtraSmall,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(topStart = 20.dp, bottomEnd = 20.dp))
                            )
                            // display title and release date
                            Text(
                                text = movie.title + " " + "(" + (movie.releaseDate.substringBefore("-") + ")"),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(7.dp))
                        // display overview
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = movie.overview,
                                maxLines = 6,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}
