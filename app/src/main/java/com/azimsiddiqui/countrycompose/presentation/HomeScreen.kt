package com.azimsiddiqui.countrycompose.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: CountryViewModelCompose,
    onItemClick: (country: String) -> Unit
) {

    val countryState = viewModel.countryState

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            "Country List",
            modifier = Modifier.padding(10.dp),
            onBackPress = { })
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(10.dp)
        ) {

            var searchState = remember {
                mutableStateOf("")
            }

            if (countryState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            SearchView(
                searchState
            ) { query ->
                searchState.value = query
                viewModel.onSearchQueryChanged(searchState.value)
            }

            Spacer(modifier = Modifier.height(20.dp))
            countryState.countryList?.let {
                LazyColumn {
                    items(countryState.countryList) { item ->
                        ItemComposable(
                            item = item,
                            onclick = {
                                onItemClick(item)
                            }, backgroundColor = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Blue.copy(0.5f),
                                    Color.Red.copy(0.5f)
                                )
                            )
                        )
                    }
                }
            }
        }
    }


    if (countryState.errorMessage.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = countryState.errorMessage)
        }
    }


}

@Composable
fun SearchView(state: MutableState<String>, onSearch: (query: String) -> Unit) {

    TextField(
        value = state.value,
        onValueChange = { value ->
            onSearch(value)
        },
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (state.value != "") {
                IconButton(
                    onClick = {
                        state.value = ""// Remove text from TextField when you press the 'X' icon
                        onSearch("")
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = CircleShape, // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = Color.Blue,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

