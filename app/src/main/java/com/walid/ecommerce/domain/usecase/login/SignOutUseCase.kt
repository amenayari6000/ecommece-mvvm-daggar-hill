package com.walid.ecommerce.domain.usecase.login

import com.walid.ecommerce.domain.repository.Authenticator
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authenticator: Authenticator
) {
    suspend operator fun invoke() = authenticator.signOut()
}