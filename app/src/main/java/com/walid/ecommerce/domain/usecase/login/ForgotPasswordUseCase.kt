package com.walid.ecommerce.domain.usecase.login

import com.walid.ecommerce.common.Resource
import com.walid.ecommerce.domain.repository.Authenticator
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val authenticator: Authenticator
) {
    suspend operator fun invoke(email: String): Resource<Void> {

        return try {
            Resource.Loading
            Resource.Success(authenticator.sendPasswordResetEmail(email))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}