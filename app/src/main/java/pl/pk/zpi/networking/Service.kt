package pl.pk.zpi.networking

import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import pl.pk.zpi.models.AuthRequest
import pl.pk.zpi.models.LoginResponse
import retrofit2.http.*

interface Service {

    @POST(REGISTER)
    fun register(
        @Body request: AuthRequest,
        @Header(HEADER_API_KEY) apiKey: String = API_KEY
    ): Completable

    @POST(LOGIN)
    fun login(
        @Body request: AuthRequest,
        @Header(HEADER_API_KEY) apiKey: String = API_KEY
    ): Single<LoginResponse>

    @GET(PHOTOS)
    fun getPhotos(
        @Header(HEADER_AUTH_TOKEN) authToken: String?,
        @Header(HEADER_API_KEY) apiKey: String = API_KEY
    ): Single<List<String>>

    @Multipart
    @POST(PHOTOS)
    fun uploadPhoto(
        @Part file: MultipartBody.Part,
        @Header(HEADER_AUTH_TOKEN) authToken: String?,
        @Header(HEADER_API_KEY) apiKey: String = API_KEY
    ): Completable

}