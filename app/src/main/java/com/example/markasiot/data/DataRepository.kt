package com.example.markasiot.data

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.example.markasiot.presentation.sign_in.GoogleAuthUiClient
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class DataRepository (context: Context) {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://latihan-kirim-data-esp32-d08f3-default-rtdb.asia-southeast1.firebasedatabase.app")
    val googleAuthClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapclient = Identity.getSignInClient(context)
        )
    }

    val hasLoggedIn: Flow<Boolean> = flow<Boolean> {
        if(googleAuthClient.getSignedInUser() != null){
            emit(true)
        } else {
            emit(false)
        }
    }
    fun <T> getData(path: String, clazz: Class<T>): Flow<List<T>> = callbackFlow {
        val reference = database.getReference(path)

        val listener = object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataList = mutableListOf<T>()

                for(dataSnapshot in snapshot.children){
                    val ioTData = dataSnapshot.getValue(clazz)
                    ioTData?.let { dataList.add(it)}
                }
                trySend(dataList)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        reference.addValueEventListener(listener)

        awaitClose { reference.removeEventListener(listener) }
    }

    fun sendData(path: String, value: Any){
        val ref = database.getReference(path)
        ref.setValue(value)
    }

}