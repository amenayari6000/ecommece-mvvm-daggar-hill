package com.walid.ecommerce.presentation.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walid.ecommerce.common.Resource
import com.walid.ecommerce.domain.usecase.login.ForgotPasswordUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class ForgotPasswordViewModel @Inject constructor(private val forgotPasswordUseCase: ForgotPasswordUseCase ) :
    ViewModel() {

    private val _result = MutableLiveData<Resource<Void>>()
    val result: LiveData<Resource<Void>> = _result

    fun sendPasswordResetEmail(email: String) {
        viewModelScope.launch {
            _result.value = Resource.Loading
            _result.value = forgotPasswordUseCase(email)
        }
    }
}