package com.whdaud.pillinTimeAndroid.presentation.mypage.cabinet

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale
import com.whdaud.pillinTimeAndroid.presentation.Dimens
import com.whdaud.pillinTimeAndroid.presentation.Dimens.BasicHeight
import com.whdaud.pillinTimeAndroid.presentation.common.CustomSnackBar

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QrCodeReaderScreen(
    cameraPermissionState: PermissionState,
    onDismiss: () -> Unit = {},
    onQrCodeScanned: (String) -> Unit
) {
    val localContext = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(localContext)
    }
    val snackbarHostState = remember { SnackbarHostState() }

    DisposableEffect(Unit) {
        val cameraProvider = cameraProviderFuture.get()
        onDispose {
            cameraProvider.unbindAll()
            onDismiss()
        }
    }
    if (cameraPermissionState.status.isGranted) {
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    val previewView = PreviewView(context)
                    val preview = Preview.Builder().build()
                    val selector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()

                    preview.setSurfaceProvider(previewView.surfaceProvider)

                    val imageAnalysis = ImageAnalysis.Builder().build()
                    imageAnalysis.setAnalyzer(
                        ContextCompat.getMainExecutor(context),
                        QrCodeAnalyzer(onQrCodeScanned)
                    )
                    runCatching {
                        cameraProviderFuture.get().bindToLifecycle(
                            lifecycleOwner,
                            selector,
                            preview,
                            imageAnalysis
                        )
                    }.onFailure {
                        Log.e("CAMERA", "Camera bind error ${it.localizedMessage}", it)
                    }
                    previewView
                }
            )

            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "닫기",
                    tint = Color.White
                )
            }
        }
    } else if (cameraPermissionState.status.shouldShowRationale) {
        LaunchedEffect(key1 = true) {
            snackbarHostState.showSnackbar(
                message = "카메라 권한을 확인해주세요"
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = BasicHeight + 12.dp)
                .background(Color.Transparent),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = Dimens.BasicPadding)
                    .align(Alignment.BottomCenter)
                    .zIndex(1f)
            ) {
                CustomSnackBar(
                    snackbarHostState = snackbarHostState,
                    message = "카메라 권한을 확인해주세요"
                )
            }
            Log.e("qr", "no permission1111")

        }
    } else {
        SideEffect {
            cameraPermissionState.run { launchPermissionRequest() }
        }
        Log.e("qr", "no permission")
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.BasicPadding)
                .zIndex(1f)
        ) {
            CustomSnackBar(
                snackbarHostState = snackbarHostState,
                message = "카메라 권한을 확인해주세요"
            )
        }
    }
}