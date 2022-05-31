package com.mvince.androidcompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.mvince.androidcompose.ui.NavigationKeys.Arg.FOOD_ID

import com.mvince.androidcompose.ui.feature.auth.choiceAuthScreen
import com.mvince.androidcompose.ui.feature.food.FoodCategoriesViewModel
import com.mvince.androidcompose.ui.feature.food.FoodScreen
import com.mvince.androidcompose.ui.feature.foodDetails.FoodCategoryDetailsViewModel
import com.mvince.androidcompose.ui.feature.foodDetails.FoodDetailsScreen
import com.mvince.androidcompose.ui.feature.photos.PhotosScreen
import com.mvince.androidcompose.ui.feature.photos.PhotosViewModel
import com.mvince.androidcompose.ui.feature.sign_in.sign_inScreen
import com.mvince.androidcompose.ui.feature.sign_up.sign_upScreen
import com.mvince.androidcompose.ui.theme.ComposeSampleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.receiveAsFlow

// Single Activity per app
@ExperimentalPermissionsApi
@AndroidEntryPoint
class EntryPointActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSampleTheme {
                FoodApp(this)
            }
        }
    }
}

@ExperimentalPermissionsApi
@Composable
private fun FoodApp(entryPointActivity: EntryPointActivity) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = NavigationKeys.Route.CHOICE_AUTH) {
        composable(route = NavigationKeys.Route.CHOICE_AUTH) {
            choiceAuthScreen(navController)
        }
        composable(route = NavigationKeys.Route.FOOD) {
            FoodDestination(navController)
        }
        composable(
            route = NavigationKeys.Route.FOOD_DETAILS,
            arguments = listOf(
                navArgument(NavigationKeys.Arg.FOOD_ID) {
                    type = NavType.StringType
                }
            )
        ) {
            FoodCategoryDetailsDestination(navController)
        }
        composable(route = NavigationKeys.Route.SIGN_UP) {
            sign_upScreen(entryPointActivity, navController)
        }
        composable(route = NavigationKeys.Route.SIGN_IN) {
            sign_inScreen(entryPointActivity, navController)
        }
        composable(route = NavigationKeys.Route.PHOTOS) {
            PhotoDestination(navController)
        }
    }
}



@Composable
private fun FoodDestination(navController: NavController) {
    val viewModel: FoodCategoriesViewModel = hiltViewModel()
    FoodScreen(
        viewModel = viewModel,
        effectFlow = viewModel.effects.receiveAsFlow(),
        onNavigationRequested = { itemId ->
            navController.navigate("${NavigationKeys.Route.FOOD}/${itemId}")
        }
    )
}

@Composable
private fun FoodCategoryDetailsDestination(navController: NavController) {
    val viewModel: FoodCategoryDetailsViewModel = hiltViewModel()
    FoodDetailsScreen(viewModel.state, navController)
}

@ExperimentalPermissionsApi
@Composable
private fun PhotoDestination(navController: NavHostController) {
    val viewModel: PhotosViewModel = hiltViewModel()
    PhotosScreen(viewModel = viewModel, navController = navController)
}






object NavigationKeys {

    object Arg {
        const val FOOD_ID = "foodCategoryName"
       const val FOOD_NOM = "food_nom"
       const val FOOD_DESCRIPTION = "food_description"
        const val FOOD_PRIX = "food_prix"
        const val FOOD_NOTE ="food_note"
        const val FOOD_URL_IMAGE = "food_image"
    }

    object Route {
        const val CHOICE_AUTH = "choice_auth"
        const val FOOD = "food"
        const val FOOD_DETAILS = "$FOOD/{$FOOD_ID}"


        const val SIGN_UP = "sign_up"
        const val SIGN_IN = "sign_in"
        const val PHOTOS = "photo"

    }
}

