package com.mvince.androidcompose.ui.feature.food

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.mvince.androidcompose.R
import com.mvince.androidcompose.model.Articletem
import com.mvince.androidcompose.model.Categories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import androidx.compose.foundation.Image


@ExperimentalCoilApi
@Composable
fun FoodScreen(
    viewModel: FoodCategoriesViewModel,
    effectFlow: Flow<FoodCategoriesContract.Effect>?,
    onNavigationRequested: (itemId: String) -> Unit
) {
    val state = viewModel.state;
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    LaunchedEffect(effectFlow) {
        effectFlow?.onEach { effect ->
            if (effect is FoodCategoriesContract.Effect.DataWasLoaded)
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Food categories are loaded.",
                    duration = SnackbarDuration.Short
                )
        }?.collect()
    }

    Scaffold(
        scaffoldState = scaffoldState,

        ) {


        Box {
            MenuCategories(viewModel = viewModel, foodItems = state.test) { itemId ->
                onNavigationRequested(itemId)
            }
            if (state.isLoading)
                LoadingBar()
        }


    }
}

@Composable
private fun CategoriesAppBar() {
    TopAppBar(
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Home,
                modifier = Modifier.padding(horizontal = 12.dp),
                contentDescription = "Action icon"
            )
        },
        title = { Text(stringResource(R.string.app_name)) },
        backgroundColor = MaterialTheme.colors.background
    )
}


@Composable
fun LoadingBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}


@Composable
fun ItemMenu(value: String, viewModel: FoodCategoriesViewModel) {

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MenuCategories(
    viewModel: FoodCategoriesViewModel,
    foodItems: Categories,
    onItemClicked: (id: String) -> Unit = { }
) {
    var messageError by rememberSaveable { mutableStateOf("Menu") }
    Column() {
        val list = listOf("Plat", "Menu", "Boisson", "Dessert")
        Text(text = messageError);
        LazyRow(
            modifier = Modifier.padding(bottom = 20.dp, top = 30.dp)
        ) {
            items(items = list, itemContent = { item ->
                Card(
                    shape = RoundedCornerShape(3.dp),
                    backgroundColor = Color(0xFFDDCEA1),
                    contentColor = Color(0xFFA71A33),
                    elevation = 12.dp,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(end = 10.dp),
                    onClick = {
                        messageError = item
                    },
                ) {
                    Text(
                        text = item,
                        modifier = Modifier,
                        textAlign = TextAlign.Center,
                    )
                }
            })
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (messageError) {
                "Plat" -> {
                    items(foodItems.plats) { item ->
                        ItemFood(item, onItemClicked)
                    }
                }
                "Menu" -> {
                    items(foodItems.menus) { item ->
                        ItemFood(item, onItemClicked)
                    }

                }
                "Boisson" -> {
                    items(foodItems.boissons) { item ->
                        ItemFood(item, onItemClicked)
                    }

                }
                "Dessert" -> {
                    items(foodItems.desserts) { item ->
                        ItemFood(item, onItemClicked)
                    }

                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemFood(item: Articletem, onItemClicked: (id: String) -> Unit = { }) {
    Card(
        onClick = {
            onItemClicked(item.id)
        },
        elevation = 4.dp,
        modifier = Modifier
            .size(150.dp)
            .padding(end = 10.dp)
    ) {
        Row() {
            val painter = rememberImagePainter(data = "https://burgerkingfrance.twic.pics/img/products/d/3a/d3afad28-0bbf-462f-899a-ef4fe22d1380_?twic=v1/contain=700x700")
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp)),
                //Use painter in Image composable
                painter = painter,
                contentDescription = "Cat"
            )
            Column() {
                Text(text = item.nom)
                Text(text = item.prix)
            }
        }

    }
}


