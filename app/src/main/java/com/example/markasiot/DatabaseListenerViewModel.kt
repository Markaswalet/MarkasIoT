package com.example.markasiot

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DatabaseListenerViewModel: ViewModel() {
     val database = FirebaseDatabase.getInstance("https://latihan-kirim-data-esp32-d08f3-default-rtdb.asia-southeast1.firebasedatabase.app")
     private val dataMap = mutableMapOf<String, MutableStateFlow<Float>>()


    fun getData(path: String): StateFlow<Float>{
        if (!dataMap.containsKey(path)){
            var newDataFlow = MutableStateFlow(0.0f)
            dataMap[path] = newDataFlow

            val reference = database.getReference(path)
            reference.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.getValue<Float>()
                    data ?. let { newDataFlow.value = it }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

        return dataMap[path] ?: error("Path not found")
    }

    fun sendData(path: String, variable : Any){
        val ref = database.getReference(path)

        ref.setValue(variable)
    }

}
