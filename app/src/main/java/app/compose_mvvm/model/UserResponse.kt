package app.compose_mvvm.model

data class UserResponse(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)