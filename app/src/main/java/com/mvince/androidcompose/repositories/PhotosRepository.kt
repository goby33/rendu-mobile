package com.mvince.androidcompose.repositories

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.ui.graphics.asImageBitmap
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mvince.androidcompose.model.Photos
import com.mvince.androidcompose.model.State
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosRepository  @Inject constructor()  {
    private val mPostsCollection = Firebase.firestore.collection("utilisateurs")
    private val auth = Firebase.auth;
    fun addPost(post: Photos): Flow<State<Photos>> = callbackFlow {
        trySend(State.Loading())
        val postDocument = mPostsCollection.document("post_id_0").set(post, SetOptions.merge())
        //val postDocument = mPostsCollection.add(post)

        postDocument.addOnCompleteListener {
            if (it.isSuccessful) {
                trySend(State.Success(post)).isSuccess
            } else if (it.isComplete && !it.isSuccessful) {
                trySend(State.Failed(it.exception?.message.toString()))
                cancel(it.exception?.message.toString())
            }
        }

        awaitClose { postDocument.isComplete }
    }



    fun addPhotoStorage(data: Bitmap) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val storage = Firebase.storage
            val storageRef = storage.reference
            val mountainsRef = storageRef.child(currentUser.uid+".jpg")
            val baos = ByteArrayOutputStream()
            data.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
            var uploadTask = mountainsRef.putBytes(data)
            uploadTask.addOnFailureListener {
                // Handle unsuccessful uploads
            }.addOnSuccessListener { taskSnapshot ->
                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                // ...
            }
        }

    }
}