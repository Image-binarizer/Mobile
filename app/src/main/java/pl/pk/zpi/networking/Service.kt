package pl.pk.zpi.networking

import io.reactivex.Completable
import io.reactivex.Single
import pl.pk.zpi.BuildConfig
import pl.pk.zpi.models.AuthRequest
import pl.pk.zpi.models.LoginResponse
import pl.pk.zpi.models.PhotoUploadRequest
import retrofit2.http.*

interface Service {

    @POST(REGISTER)
    fun register(
        @Body request: AuthRequest,
        @Header(HEADER_API_KEY) apiKey: String = BuildConfig.API_KEY
    ): Completable

    @POST(LOGIN)
    fun login(
        @Body request: AuthRequest,
        @Header(HEADER_API_KEY) apiKey: String = BuildConfig.API_KEY
    ): Single<LoginResponse>

    @GET(PHOTOS)
    fun getPhotos(
        @Header(HEADER_API_KEY) apiKey: String = BuildConfig.API_KEY
    ): Single<List<String>>

    @POST(PHOTOS)
    fun uploadPhoto(
        @Body request: PhotoUploadRequest,
        @Header(HEADER_API_KEY) apiKey: String = BuildConfig.API_KEY
    ): Completable

}