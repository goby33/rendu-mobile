package com.mvince.androidcompose.ui.feature.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mvince.androidcompose.R


@Composable
fun choiceAuthScreen(navController: NavController) {
    Scaffold(
    ) {
        BoxExample(navController)

    }
}


@Composable
fun BoxExample(navController: NavController) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF413A39))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier.padding(70.dp)
            )

            androidx.compose.material.Button(
                modifier = Modifier
                    .width(350.dp)
                    .height(60.dp)
                    .padding(top = 20.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6D95DB)),
                onClick = {
                    navController.navigate("sign_up")
                }) {
                Text(
                    text = "s'inscrire"
                )
            }

            androidx.compose.material.Button(
                modifier = Modifier
                    .width(350.dp)
                    .height(60.dp)
                    .padding(top = 20.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6D95DB)),
                onClick = {
                    navController.navigate("sign_in")
                }) {
                Text(
                    text = "se connecter"
                )
            }



        }
    }
}






