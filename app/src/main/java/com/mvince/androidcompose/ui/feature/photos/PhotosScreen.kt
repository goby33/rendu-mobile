package com.mvince.androidcompose.ui.feature.photos

import android.Manifest
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.mvince.androidcompose.R

@ExperimentalPermissionsApi
@Composable
fun PhotosScreen(viewModel: PhotosViewModel) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF413A39))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "",
                    modifier = Modifier
                        .width(270.dp)
                        .padding(bottom = 30.dp)
                )
                FeatureThatRequiresCameraPermission(viewModel)
            }

        }
    }
}

@ExperimentalPermissionsApi
@Composable
private fun FeatureThatRequiresCameraPermission(viewModel: PhotosViewModel) {
    val bitmapFromCamera = remember { mutableStateOf<Bitmap?>(null) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            bitmapFromCamera.value = it
        }
    // Camera permission state
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    when (cameraPermissionState.status) {
        // If the camera permission is granted, then show screen with the feature enabled
        PermissionStatus.Granted -> {
            if (bitmapFromCamera.value == null) {
                Button(
                    modifier = Modifier
                        .width(350.dp)
                        .height(60.dp)
                        .padding(top = 20.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6D95DB)),
                    onClick = {
                        launcher.launch()
                    }) {
                    Text(
                        text = "Take a picture"
                    )
                }

            } else {
                bitmapFromCamera.let {
                    val data = it.value
                    if (data != null) {
                        viewModel.addPhotoStorage(data)
                        Image(
                            contentScale = ContentScale.Crop,
                            bitmap = data.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(128.dp)
                                .clip(CircleShape)                       // clip to the circle shape
                                .border(2.dp, Color.Gray, CircleShape)
                        )

                        Button(
                            modifier = Modifier
                                .width(350.dp)
                                .height(60.dp)
                                .padding(top = 20.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6D95DB)),
                            onClick = {
                                //TODO
                            }) {
                            Text(
                                text = "continuer"
                            )
                        }


                    }
                }
            }
        }
        is PermissionStatus.Denied -> {
            Column {
                val status = cameraPermissionState.status as PermissionStatus.Denied
                val textToShow = if (status.shouldShowRationale) {
                    "The camera is important for this app. Please grant the permission."
                } else {
                    "Camera permission required for this feature to be available. " +
                            "Please grant the permission"
                }
                Text(textToShow)
                Button(
                    modifier = Modifier
                        .width(350.dp)
                        .height(60.dp)
                        .padding(top = 20.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6D95DB)),
                    onClick = {
                        cameraPermissionState.launchPermissionRequest()
                    }) {
                    Text(
                        text = "Request permission"
                    )
                }
            }
        }
    }
}


