package com.example.markasiot.presentation.home

import android.os.Build
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.TopAppBarScrollBehavior

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.markasiot.R
import com.example.markasiot.data.IotData
import com.example.markasiot.data.SwitchData
import com.example.markasiot.data.sampleData
import com.example.markasiot.data.sampleSwitchData
import com.example.markasiot.presentation.nav.BottomNav
import com.example.markasiot.presentation.nav.HomeNavGraph
import com.example.markasiot.presentation.sign_in.Bottom
import com.example.markasiot.presentation.sign_in.MiddleScreen
import com.example.markasiot.presentation.sign_in.TopScreen
import com.example.markasiot.presentation.sign_in.UserData
import com.example.markasiot.ui.theme.MarkasIoTTheme
import com.example.markasiot.ui.theme.track_toggle_on
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavHostController,
    onSignOut: () -> Unit,
    modifier: Modifier = Modifier,
    ){

    MarkasIoTTheme {
        Surface (
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 5.dp
        ){
            Scaffold(
                bottomBar = {
                    BottomNav(navController = navController)
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                ) {
                    HomeNavGraph(navController = navController, onSignOut = onSignOut)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen2(
    viewModel: HomeViewModel,
    onNavigateToLoginScreen: () -> Unit = {}
){
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val userData by viewModel.userData.collectAsStateWithLifecycle()
    val homeDataList by viewModel.homeDataList.collectAsStateWithLifecycle()
    val homeSwitchList by viewModel.relayDataList.collectAsStateWithLifecycle()

    when(viewState){
        ViewState.Loading -> {
            LoadingScreen(modifier = Modifier.fillMaxSize())
        }

        ViewState.LoggedIn -> {
            HomeScreenContent(
                userData = userData,
                homeDataList = homeDataList.IoTDataList,
                homeSwitchList = homeSwitchList.switchDataList,
                onCheckedChange = {isChecked, index ->
                    viewModel.updateSwitchState(index+1, isChecked)
                }
            )
        }

        ViewState.NotLoggedIn -> {
            LoadingScreen(modifier = Modifier.fillMaxSize())
            LaunchedEffect(viewState){
                onNavigateToLoginScreen()
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreenContent(
    userData: UserData?,
    homeDataList: List<IotData>,
    homeSwitchList: List<SwitchData>,
    onCheckedChange: (Boolean, Int) -> Unit
    ){
    Column(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)

    ) {
        UserDisplay(userData = userData)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Rooms",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))

        HomePager(listData = homeDataList)
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Switches",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))

        SwitchPage(itemsList = homeSwitchList, onCheckedChange = onCheckedChange)

    }

}
@Composable
fun HomeBody(modifier: Modifier = Modifier, data: IotData, floor: Int){
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10))
    ) {
        Row (modifier = Modifier.padding(16.dp)){
            ItemDetails(
                image = R.drawable.potion,
                details = "Amonia Level",
                value = "${String.format("%.2f", data.amoniaGas)} ppm"
            )

            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Lantai ${data.floor}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier
                    .align(Alignment.Top)
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.tertiary)
                    .padding(bottom = 6.dp, start = 10.dp, end = 10.dp, top = 2.dp)
            )

        }

        Spacer(modifier = Modifier.width(4.dp))
        Divider(
            thickness = 1.dp,
            modifier = Modifier
                .clip(RoundedCornerShape(25))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Row (modifier = Modifier.padding(16.dp)){
            ItemDetails(
                image = R.drawable.humidity,
                details = "Kelembapan",
                value = "${String.format("%.2f", data.humidity)}%"
            )

            Spacer(modifier = Modifier.weight(1f))
            ItemDetails(
                image = R.drawable.suhu,
                details = "Suhu",
                value = "${String.format("%.2f", data.temperature)}°C"
            )
        }

    }
}

@Composable
fun HomeBody2(modifier: Modifier = Modifier, data: IotData, floor: Int){
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10))
    ) {
        Row (modifier = Modifier.padding(16.dp)){
            ItemDetails(
                image = R.drawable.potion,
                details = "Amonia Level",
                value = "${data.amoniaGas} ppm"
            )

            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Lantai $floor",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.tertiary)
                    .padding(horizontal = 10.dp, vertical = 4.dp)

            )

        }

        Spacer(modifier = Modifier.width(4.dp))
        Divider(
            thickness = 1.dp,
            modifier = Modifier
                .clip(RoundedCornerShape(25))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Row (modifier = Modifier.padding(16.dp)){
            ItemDetails(
                image = R.drawable.humidity,
                details = "Kelembapan",
                value = "${data.humidity}%"
            )

            Spacer(modifier = Modifier.weight(1f))
            ItemDetails(
                image = R.drawable.suhu,
                details = "Suhu",
                value = "${data.temperature}°C"
            )
        }

    }
}

@Composable
fun SwitchPage(itemsList: List<SwitchData>, onCheckedChange: (Boolean, Int) -> Unit){

    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        content = {
            itemsIndexed(itemsList) {index, item ->
                mySwitch(
                    number = index+1,
                    initialState = item.isActive,
                    onCheckedChange = { isChecked->
                            onCheckedChange(isChecked, index-1)
                    }
                )
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(title: String, scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
                },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}

@Composable
fun mySwitch(number: Int, initialState: Boolean, onCheckedChange: (Boolean) -> Unit, modifier: Modifier = Modifier){
   var state by remember { mutableStateOf(initialState) }
    Box (
        modifier = Modifier
            .padding(8.dp)
            .width(160.dp)
            //.height(800.dp)
            .clip(RoundedCornerShape(20))
            .background(if (initialState) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
    ){
        Column (modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
            Image(
                painter = painterResource(id = if(initialState) R.drawable.relay_on else R.drawable.relay_off),
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Relay $number",
                style = MaterialTheme.typography.labelLarge,
                color = if(initialState) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(20.dp))

            Switch(
                checked = initialState,
                onCheckedChange = { newValue ->
                    state = newValue
                    onCheckedChange(newValue)
                },
                colors = SwitchDefaults.colors(
                    uncheckedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                    checkedTrackColor = track_toggle_on

                ),
                modifier = Modifier.align(Alignment.End)
            )

        }
    }
}

@Composable
fun ItemDetails(@DrawableRes image: Int, details: String, value: String){
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Box (modifier = Modifier.padding(top = 6.dp)) {
            Box (
                modifier = Modifier
                    .clip(RoundedCornerShape(20))
                    .background(MaterialTheme.colorScheme.primary)
                    .size(40.dp)
                ,
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(id = image),
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                        .padding(8.dp)

                )
            }
        }
    }
    Spacer(modifier = Modifier.width(12.dp))

    Column (horizontalAlignment = Alignment.Start){
        Text(
            text = details,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.outline,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePager(modifier: Modifier = Modifier, listData : List<IotData>){
    val pageState = rememberPagerState(initialPage = 0, pageCount = { listData.size })
    
    HorizontalPager(state = pageState) { page ->
        when (page){
            page -> HomeBody(data = listData[page], floor = page+1)
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserDisplay(userData: UserData?, modifier: Modifier = Modifier){
    val currentDate = LocalDate.now()
    val dateFormat = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")
    Row (
        modifier = Modifier.padding(top = 16.dp)
    ){
        Column (){
            Text(
                text = "Hello ${userData?.username}!",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                modifier = Modifier.width(250.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = dateFormat.format(currentDate),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
            )

            Spacer(modifier = Modifier.height(12.dp))

//            Button(
//                onClick = onSignOutClick) {
//                Text(
//                    text = "Sign Out",
//                    color = MaterialTheme.colorScheme.onPrimary,
//                    style = MaterialTheme.typography.titleMedium
//
//                )
//            }
        }
        Spacer(modifier = Modifier.weight(1f))
        if(userData?.profilePictureUrl != null){
            Log.d("TAG", "Profile picture URL: ${userData.profilePictureUrl}")
            Card (
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier.padding(top = 8.dp)
            ){
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(userData.profilePictureUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(20))
                )
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePreview(){
    MarkasIoTTheme {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 5.dp,
            modifier = Modifier.fillMaxSize()

        ){
            //UserDisplay(userData = UserData("kk", "Sasmita", "https://example.com/image.jpg", null, "dd"))
            HomeScreenContent(
                userData = UserData("kkw", "Arya", "https://example.com/image.jpg", null, "dd"),
                homeDataList = sampleData,
                homeSwitchList = sampleSwitchData,
                onCheckedChange = {newState, index ->
                    // Mock behavior for preview
                    Log.d("Switch", "Switch $index is now $newState")
                }
            )
            //SwitchPage(itemsList = sampleSwitchData, onSwitchChange = {  })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BodyPreview(){
    MarkasIoTTheme {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 5.dp,

            ){
            Column(modifier = Modifier.padding(16.dp)) {
                HomePager(listData = sampleData)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SwitchPreview(){
    MarkasIoTTheme {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 5.dp,
        ){
            Row(modifier = Modifier.padding(16.dp)) {
                mySwitch(number = 1,initialState = false, onCheckedChange = {})
                Spacer(modifier = Modifier.width(12.dp))
                mySwitch(number = 2, initialState = true, onCheckedChange = {})
            }
        }
    }
}


