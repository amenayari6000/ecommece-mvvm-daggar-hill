package com.walid.ecommerce.domain.usecase.login

import com.walid.ecommerce.common.Resource
import com.walid.ecommerce.data.model.User
import com.walid.ecommerce.domain.repository.Authenticator
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authenticator: Authenticator
) {
    suspend operator fun invoke(): Resource<User> {

        return try {
            Resource.Loading
            Resource.Success(authenticator.getCurrentUser())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}