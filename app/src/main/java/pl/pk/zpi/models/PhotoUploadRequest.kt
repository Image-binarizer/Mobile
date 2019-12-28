package pl.pk.zpi.models

data class PhotoUploadRequest(
    val token: String?,
    val image_name: String,
    val image: String,
    val force: Int = 0
)
