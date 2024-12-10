package com.itzik.notes_.project.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.itzik.notes_.R

import com.itzik.notes_.project.model.WallpaperResponse
import com.itzik.notes_.project.utils.gradientBrush
import com.itzik.notes_.project.viewmodels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("MutableCollectionMutableState")
@Composable
fun WallpaperScreen(
    modifier: Modifier,
    userViewModel: UserViewModel,
    onImageSelected: (image: String) -> Unit,
    resetDefault: () -> Unit,
    coroutineScope: CoroutineScope,
    onScreenExit: (isScreenClosed: Boolean) -> Unit,
) {
    var imagesList by remember {
        mutableStateOf(WallpaperResponse(emptyList()))
    }

    var searchParam by remember { mutableStateOf("") }
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (searchBar, imageGallery) = createRefs()

        Card(
            modifier = Modifier
                .constrainAs(searchBar) {
                    top.linkTo(parent.top)
                }
                .fillMaxWidth()
                .height(70.dp)
                .padding(8.dp)
                .border(
                    width = 0.4.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(8.dp)
                ),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize(),
            ) {
                val (searchField,searchBtn, divider, cancelBtn, clearAllBtn) = createRefs()

                TextField(
                    modifier= Modifier.constrainAs(searchField){
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(searchBtn.start)
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            coroutineScope.launch {
                                userViewModel.getWallpaperList(searchParam).collect {
                                    imagesList = it
                                }
                            }
                        }
                    ), colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    value = searchParam,
                    onValueChange = {
                        searchParam = it
                    }, placeholder = {
                        Text(text = stringResource(R.string.search_images), color = Color.Black)
                    }
                )

                IconButton(
                    modifier= Modifier.constrainAs(searchBtn){
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(searchField.end)
                        end.linkTo(divider.start)
                    },
                    onClick = {
                        coroutineScope.launch {
                            userViewModel.getWallpaperList(searchParam).collect {
                                imagesList = it
                            }
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color.Black)
                }

                VerticalDivider(modifier = Modifier.padding(12.dp).constrainAs(divider){
                    start.linkTo(searchBtn.end)
                    end.linkTo(cancelBtn.start)
                })

                IconButton(
                    modifier= Modifier.constrainAs(cancelBtn){
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(divider.end)
                        end.linkTo(clearAllBtn.start)
                    },
                    onClick = {
                        onScreenExit(false)
                    }

                ) {
                    Icon(imageVector = Icons.Default.Cancel, contentDescription = null, tint = Color.Black)
                }


                IconButton(
                    modifier= Modifier.constrainAs(clearAllBtn){
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
                    onClick = {
                        resetDefault()
                    }
                ) {
                    Icon(imageVector = Icons.Default.ClearAll, contentDescription = null, tint = colorResource(R.color.darker_blue))
                }
            }
        }



        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(imageGallery) {
                    top.linkTo(searchBar.bottom)
                },
            columns = GridCells.Fixed(3),
        ) {
            items(imagesList.hits) { imageItem ->
                ImageItem(
                    imageItem.largeImageURL,
                    modifier = Modifier.clickable {
                        onImageSelected(imageItem.largeImageURL)
                    }
                )
            }
        }
    }
}

@Composable
fun ImageItem(imageUrl: String, modifier: Modifier) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(0.dp),
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
        }
    }
}