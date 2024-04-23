package app.compose_mvvm.network

import app.compose_mvvm.model.UserResponse
import app.compose_mvvm.utils.AppConstants.TODOS
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface ApiInterface {

    @GET(TODOS)
    suspend fun fetchUserData() :List<UserResponse>

}