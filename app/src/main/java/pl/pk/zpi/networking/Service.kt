package pl.pk.zpi.networking

import io.reactivex.Completable
import pl.pk.zpi.models.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface Service {

    @POST(REGISTER)
    fun register(@Body request: RegisterRequest): Completable

}