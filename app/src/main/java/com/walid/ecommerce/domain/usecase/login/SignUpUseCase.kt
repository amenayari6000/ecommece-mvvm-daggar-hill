package com.walid.ecommerce.domain.usecase.login

import com.walid.ecommerce.common.Resource
import com.walid.ecommerce.data.model.User
import com.walid.ecommerce.domain.repository.Authenticator
import javax.inject.Inject
class SignUpUseCase @Inject constructor(
    private val authenticator: Authenticator
) {
    suspend operator fun invoke(
        user: User,
        password: String
    ): Resource<Unit> {
        return try {
            Resource.Loading
            Resource.Success(authenticator.signUpWithEmailAndPassword(user, password))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}