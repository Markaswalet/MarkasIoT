package com.example.markasiot.presentation.sign_in

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.markasiot.R
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapclient: SignInClient
) {
    private val auth = Firebase.auth

    suspend fun SignIn(): IntentSender?{
        val result = try {
            oneTapclient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception){
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun signWithIntent(intent: Intent): SignInResult{
        val credential = oneTapclient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        username = displayName,
                        profilePictureUrl = photoUrl.toString(),
                        email = email,
                        phoneNumber = phoneNumber
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }



    suspend fun signOut(){
        try {
            oneTapclient.signOut().await()
            auth.signOut()

        } catch (e: Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
    }

    fun getSignedInUser(): UserData? = auth.currentUser?.let {
        UserData(
            userId = it.uid,
            username= it.displayName,
            profilePictureUrl = it.photoUrl.toString(),
            email = it.email,
            phoneNumber = it.phoneNumber
        )
    }

    private fun  buildSignInRequest(): BeginSignInRequest{
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string. web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}