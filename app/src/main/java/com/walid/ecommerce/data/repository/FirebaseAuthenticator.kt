package com.walid.ecommerce.data.repository


import com.walid.ecommerce.common.Constants.COLLECTION_PATH
import com.walid.ecommerce.common.Constants.E_MAIL
import com.walid.ecommerce.common.Constants.ID
import com.walid.ecommerce.common.Constants.NICKNAME
import com.walid.ecommerce.common.Constants.PHONE_NUMBER
import com.walid.ecommerce.data.model.User
import com.walid.ecommerce.domain.repository.Authenticator
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthenticator @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : Authenticator {

    override suspend fun getFirebaseUserUid(): String = firebaseAuth.currentUser?.uid.orEmpty()

    override suspend fun signUpWithEmailAndPassword(
        user: User,
        password: String
    ) {
        firebaseAuth.createUserWithEmailAndPassword(user.email, password).await()

        val userModel = hashMapOf(
            ID to getFirebaseUserUid(),
            E_MAIL to user.email,
            NICKNAME to user.nickname,
            PHONE_NUMBER to user.phoneNumber
        )

        firebaseFirestore.collection(COLLECTION_PATH).document(getFirebaseUserUid())
            .set(userModel).await()
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): AuthResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()

    override suspend fun sendPasswordResetEmail(email: String): Void =
        firebaseAuth.sendPasswordResetEmail(email).await()

    override suspend fun getCurrentUser(): User {
        val user =
            firebaseFirestore.collection(COLLECTION_PATH).document(getFirebaseUserUid())
                .get().await()

        return User(
            user[E_MAIL] as String,
            user[NICKNAME] as String,
            user[PHONE_NUMBER] as String,
        )
    }

    override suspend fun isCurrentUserExist() = firebaseAuth.currentUser != null

    override suspend fun signOut() = firebaseAuth.signOut()

}