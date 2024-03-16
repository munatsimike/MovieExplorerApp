package com.example.movieexplorerapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import com.example.movieexplorerapp.R

/**
 * This displays images from the remote saver.
 * @param imageUrl the string url is provided as a parameter in the movie object
 * @param imageSize various poster sizes are also provided by the API.
 * The default size is set to extralarge so that it can be reduced with a modifier
 */
@Composable
fun NetworkImageLoader(
    imageUrl: String,
    modifier: Modifier = Modifier,
    imageDescription: String = stringResource(R.string.poster_image_desc),
    imageSize: ImageSize = ImageSize.ExtraLarge
) {
    Image(
        painter = rememberAsyncImagePainter(model = ImageUrlBuilder.getUrl(imageUrl, imageSize)),
        contentDescription = imageDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}