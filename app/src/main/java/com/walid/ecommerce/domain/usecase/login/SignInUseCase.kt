package com.walid.ecommerce.domain.usecase.login

import com.walid.ecommerce.common.Resource
import com.walid.ecommerce.domain.repository.Authenticator
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authenticator: Authenticator
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Resource<FirebaseUser> {
        return try {
            Resource.Loading
            Resource.Success(authenticator.signInWithEmailAndPassword(email, password).user!!)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}