package pl.pk.zpi.networking

import io.reactivex.Completable
import io.reactivex.Single
import pl.pk.zpi.models.AuthRequest
import pl.pk.zpi.models.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

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
        @Header(HEADER_AUTH_TOKEN) authToken: String,
        @Header(HEADER_API_KEY) apiKey: String = API_KEY
    ): Single<List<String>>

}