package com.walid.ecommerce.presentation.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walid.ecommerce.common.Resource
import com.walid.ecommerce.domain.usecase.login.SignInUseCase
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val signInUseCase: SignInUseCase) : ViewModel() {

    private val _result = MutableLiveData<Resource<FirebaseUser>>()
    val result: LiveData<Resource<FirebaseUser>> = _result

    fun signInWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            _result.value = Resource.Loading
            _result.value = signInUseCase(email, password)
        }
    }
}