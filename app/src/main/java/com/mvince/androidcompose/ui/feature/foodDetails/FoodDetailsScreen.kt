package com.mvince.androidcompose.ui.feature.foodDetails

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.mvince.androidcompose.ui.feature.auth.BoxExample


@Composable
fun FoodDetailsScreen(
    state: FoodCategoryDetailsContract.State,
    navController: NavController
) {
    val scrollState = rememberLazyListState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Connexion") },
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

       Text(text = state.category)

    }
}