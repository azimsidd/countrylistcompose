package com.azimsiddiqui.countrycompose.presentation

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import androidx.compose.ui.unit.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CityComposable(
    viewModelCompose: CountryViewModelCompose,
    onBackPress: () -> Boolean,
    openBottomSheet: () -> Unit
) {

    val countryState = viewModelCompose.countryState
    val context = LocalContext.current

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.Expanded }
    )

    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = { BottomSheet() },
        modifier = Modifier.fillMaxSize(),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            TopBar(
                "City List",
                modifier = Modifier.padding(10.dp),
                onBackPress = { onBackPress() })
            Spacer(modifier = Modifier.height(20.dp))
            if (countryState.cityList != null) {
                DropDownSpinner(countryState.cityList) { selectedCity ->
                    Toast.makeText(context, selectedCity, Toast.LENGTH_SHORT).show()
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { coroutineScope.launch { sheetState.show() } },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Open bottomsheet", modifier = Modifier.padding(10.dp))
            }
        }
    }
}

@Composable
fun TopBar(title: String, onBackPress: () -> Unit, modifier: Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "back",
            modifier = Modifier
                .padding(end = 5.dp)
                .clickable { onBackPress() }
        )
        Text(text = title)
    }
}

@Composable
fun ItemComposable(onclick: () -> Unit, backgroundColor: Brush, item: String) {
    Row(
        modifier = Modifier
            .padding(5.dp)
            .background(
                brush = backgroundColor, shape = RoundedCornerShape(5.dp)
            )
            .clickable { onclick() }
            .fillMaxWidth(),
    ) {
        Text(
            text = item,
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier
                .padding(10.dp)
        )
    }
}

@Composable
fun BottomSheet() {
    Column(
        modifier = Modifier
            .padding(32.dp)
            .background(shape = RoundedCornerShape(8.dp, 8.dp), color = Color.White),
    ) {
        Text(
            text = "Bottom sheet",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Click outside the bottom sheet to hide it",
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun DropDownSpinner(dataList: List<String>, onItemSelected: (item: String) -> Unit) {
    var selectedItem by remember { mutableStateOf("select city") }
    var (isExpanded, setExpanded) = remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(listOf(Color.Red, Color.Blue)),
                shape = RoundedCornerShape(5.dp)
            )
            .background(Color.White)
            .clickable(
                onClick = {
                    setExpanded(!isExpanded)
                }
            )
        ) {
            Text(
                text = selectedItem,
                modifier = Modifier.padding(16.dp)
            )
        }

        DropdownMenu(
            expanded = isExpanded, // Set this to true to show the dropdown menu
            onDismissRequest = {
                // Close the dropdown menu
                setExpanded(false)
            },
            modifier = Modifier
                .height(150.dp)
        ) {
            dataList.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        selectedItem = item
                        setExpanded(false)
                        onItemSelected(item)
                        // Close the dropdown menu
                    }
                ) {
                    Text(text = item)
                }
            }
        }
    }
}
