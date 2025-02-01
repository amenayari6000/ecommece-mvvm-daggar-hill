package com.walid.ecommerce.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walid.ecommerce.common.Resource
import com.walid.ecommerce.data.model.User
import com.walid.ecommerce.domain.usecase.login.CheckCurrentUserUseCase
import com.walid.ecommerce.domain.usecase.login.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val checkCurrentUserUseCase: CheckCurrentUserUseCase
) : ViewModel() {

    private val _result = MutableLiveData<Resource<Unit>>()
    val result: LiveData<Resource<Unit>> = _result

    private val _checkCurrentUser = MutableLiveData<Boolean>()
    val checkCurrentUser: LiveData<Boolean> = _checkCurrentUser

    init {
        checkCurrentUser()
    }

    fun signUpWithEmailAndPassword(user: User, password: String) {
        viewModelScope.launch {
            _result.value = Resource.Loading
            _result.value = signUpUseCase(user, password)
        }
    }

    private fun checkCurrentUser() {
        viewModelScope.launch {
            _checkCurrentUser.value = checkCurrentUserUseCase()
        }
    }
}