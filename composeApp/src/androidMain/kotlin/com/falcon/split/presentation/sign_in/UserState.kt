package com.falcon.split.presentation.sign_in

sealed class UserState {
    object Loading : UserState()
    data class Success(val user: UserData) : UserState()
    data class Error(val error: String) : UserState()
}