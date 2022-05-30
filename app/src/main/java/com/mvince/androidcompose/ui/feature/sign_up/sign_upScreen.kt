package com.mvince.androidcompose.ui.feature.sign_up

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mvince.androidcompose.R
import com.mvince.androidcompose.components.ButtonForm
import com.mvince.androidcompose.ui.EntryPointActivity


@ExperimentalPermissionsApi
@Composable
fun sign_upScreen(
    entryPointActivity:  EntryPointActivity,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Inscription") },
                navigationIcon = if (navController.previousBackStackEntry != null) {
                    {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                } else {
                    null
                }

            )
        }
    ) {
        BoxExample(entryPointActivity, navController)
    }
}

@ExperimentalPermissionsApi
@Composable
fun BoxExample(entryPointActivity:  EntryPointActivity,
               navController: NavHostController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirm by remember { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }
    var messageError by rememberSaveable { mutableStateOf("") }

    fun sign_up() {
        isError = false
        if (email == "" || password == "" || passwordConfirm == "") {
            isError = true
            messageError = "all is required"
        }
        if (passwordConfirm != password) {
            isError = true
            messageError = "password and confirm passord"
        }
        var auth: FirebaseAuth = Firebase.auth
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(entryPointActivity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    navController.navigate("photo")
                } else {
                    if (task.exception != null) {
                        isError = true
                        messageError = "Error:" + task.exception
                    }
                }
            }

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF413A39))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier
                    .width(270.dp)
                    .padding(bottom = 30.dp)
            )

            TextField(
                modifier = Modifier
                    .padding(bottom = 15.dp)
                    .border(
                        BorderStroke(2.dp, SolidColor(Color(0xFFEBC4CB)))
                    )
                    .height(50.dp)
                    .width(300.dp),
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFFF1C4CB),
                    textColor = Color(0xFF3766B9),
                    disabledTextColor = Color.Green,
                    disabledLabelColor = Color.Gray
                )
            )

            TextField(
                modifier = Modifier
                    .padding(bottom = 15.dp)
                    .border(
                        BorderStroke(2.dp, SolidColor(Color(0xFFEBC4CB)))
                    )
                    .height(50.dp)
                    .width(300.dp),
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFFEBC4CB),
                    textColor = Color(0xFF3766B9),
                    disabledTextColor = Color.Green,
                    disabledLabelColor = Color.Gray
                )
            )

            TextField(
                isError = false,
                modifier = Modifier
                    .padding(bottom = 15.dp)
                    .border(
                        BorderStroke(2.dp, SolidColor(Color(0xFFEBC4CB)))
                    )
                    .height(50.dp)
                    .width(300.dp),
                value = passwordConfirm,
                onValueChange = { passwordConfirm = it },
                label = { Text("Confirm Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFFEBC4CB),
                    textColor = Color(0xFF3766B9),
                    disabledTextColor = Color.Green,
                    disabledLabelColor = Color.Gray
                )
            )

            Button(
                modifier = Modifier
                    .width(350.dp)
                    .height(60.dp)
                    .padding(top = 20.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6D95DB)),
                onClick = {
                    sign_up()
                }) {
                Text(
                    text = "Inscription")
            }

            if (isError) {
                Text(messageError,
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp, top = 10.dp),
                )
            }




            
        }
    }


}





