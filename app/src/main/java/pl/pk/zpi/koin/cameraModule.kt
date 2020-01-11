package pl.pk.zpi.koin

import android.util.DisplayMetrics
import android.util.Rational
import android.util.Size
import androidx.camera.core.*
import org.koin.dsl.module

val cameraModule = module {
    single { DisplayMetrics() }
    single { Size(get<DisplayMetrics>().widthPixels, get<DisplayMetrics>().heightPixels) }
    single { Rational(get<DisplayMetrics>().widthPixels, get<DisplayMetrics>().heightPixels) }

    single {
        PreviewConfig.Builder().apply {
            setLensFacing(CameraX.LensFacing.BACK)
            setTargetResolution(get())
            setTargetAspectRatio(get())
        }.build()
    }

    single {
        ImageCaptureConfig.Builder().apply {
            setLensFacing(CameraX.LensFacing.BACK)
            setTargetAspectRatio(get())
            setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
            setTargetResolution(Size(480, 800))
        }.build()
    }
}
