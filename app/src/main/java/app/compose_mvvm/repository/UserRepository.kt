package app.compose_mvvm.repository

import app.compose_mvvm.model.UserResponse
import app.compose_mvvm.network.ApiInterface
import app.compose_mvvm.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class UserRepository @Inject constructor(
    private val apiInterface: ApiInterface
) {
    suspend fun getUserResponse(): Resource<List<UserResponse>> {
        val response = try {
            apiInterface.fetchUserData()
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred: ${e.localizedMessage}")
        }
        return Resource.Success(response)
    }
}