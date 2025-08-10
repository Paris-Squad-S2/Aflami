package com.feature.mediaDetails.mediaDetailsUi.ui.comon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.key
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.feature.mediaDetails.mediaDetailsUi.R
import com.paris_2.aflami.designsystem.components.AppIcon
import com.paris_2.aflami.designsystem.components.IconButton
import com.paris_2.aflami.designsystem.theme.Theme
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun VideoPlayer(
    videoKey: String,
    onCloseClick: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Theme.colors.surface)
    ) {
        key(videoKey) {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth(),
                factory = { ctx ->
                    YouTubePlayerView(ctx).apply {
                        lifecycleOwner.lifecycle.addObserver(this)
                        addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                youTubePlayer.loadVideo(videoKey, 0f)
                            }
                        })
                    }
                }
            )
        }

        IconButton(
            onClick = onCloseClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
        ) {
            AppIcon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_cancel),
                contentDescription = "Close",
                modifier = Modifier.size(20.dp),
                tint = Theme.colors.text.title
            )
        }
    }
}

