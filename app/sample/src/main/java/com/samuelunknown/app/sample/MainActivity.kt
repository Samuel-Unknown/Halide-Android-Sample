package com.samuelunknown.app.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.samuelunknown.app.sample.ui.theme.HalideAndroidSampleTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HalideAndroidSampleTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Content(viewModel)
                }
            }
        }
    }
}

@Composable
fun Content(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.stateFlow.collectAsState()

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(state.bitmap)
                .transformations(RoundedCornersTransformation(with(LocalDensity.current) { 16.dp.toPx() }))
                .build(),
            contentDescription = "Image",
            modifier = Modifier
                .padding(24.dp)
                .aspectRatio(1f)
        )

        Spacer(modifier = Modifier.size(24.dp))

        Button(onClick = { viewModel.invertImage() }) {
            Text(text = "Invert image")
        }
    }
}
