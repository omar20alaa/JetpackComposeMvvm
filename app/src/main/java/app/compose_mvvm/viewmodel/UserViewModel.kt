package app.compose_mvvm.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.compose_mvvm.model.UserResponse
import app.compose_mvvm.repository.UserRepository
import app.compose_mvvm.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository : UserRepository
) : ViewModel(){

    var isLoading = mutableStateOf(false)
    private var _getUserData: MutableLiveData<List<UserResponse>> = MutableLiveData<List<UserResponse>>()
    var getUserData : LiveData<List<UserResponse>> = _getUserData

    suspend fun fetchUserData(): Resource<List<UserResponse>> {
        val result = userRepository.getUserResponse()
        if (result is Resource.Success) {
            isLoading.value = true
            _getUserData.value = result.data!!
        }
        return result
    }

}