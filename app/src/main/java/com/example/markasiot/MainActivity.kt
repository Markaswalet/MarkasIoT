package com.example.markasiot

import android.hardware.lights.Light
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.markasiot.ui.theme.MarkasIoTTheme
import com.example.markasiot.ui.theme.black
import com.example.markasiot.ui.theme.gray
import com.example.markasiot.ui.theme.ivory
import com.example.markasiot.ui.theme.orange
import com.example.markasiot.ui.theme.orangeMute
import com.example.markasiot.ui.theme.satoshiFamily
import com.example.markasiot.ui.theme.surface_green
import com.example.markasiot.ui.theme.surface_variant
import com.example.markasiot.ui.theme.white
import com.example.markasiot.ui.theme.white2
import com.google.firebase.database.FirebaseDatabase
import java.nio.file.WatchEvent

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val ViewModel by viewModels<DatabaseListenerViewModel>()
        super.onCreate(savedInstanceState)

        setContent {
            MarkasIoTTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = ivory
                ) {
                    Column (
                        modifier = Modifier
                            .padding(8.dp)
                    ){
                        TopScreen()
                        Spacer(modifier = Modifier.height(32.dp))

                        Box {
                            mainPage()
                        }
                        Spacer(modifier = Modifier.height(25.dp))
                        Text(
                            text = "Control",
                            fontFamily = satoshiFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            modifier = Modifier
                                .padding(16.dp)
                        )
                        Row {
                            mySwitch(
                                number = 1,
                                Device = "Mistmaker",
                                database = ViewModel.database,
                                path = "Sensor/Relay1"
                                )
                            mySwitch(
                                number = 2,
                                Device= "Kipas",
                                database = ViewModel.database,
                                path = "Sensor/Relay2"
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun mySwitch(number: Int, Device: String, database: FirebaseDatabase, path: String,modifier: Modifier = Modifier){
    var toggleOn by remember{ mutableStateOf(false) }
    var bgColor by remember { mutableStateOf(white2) }


    Box (
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(25))
            .background(
                if (toggleOn) surface_green else surface_variant
            )
            .size(width = 190.dp, height = 50.dp)
            .clickable {
                toggleOn = !toggleOn
                val ref = database.getReference(path)

                if (toggleOn) {
                    ref.setValue(toggleOn)
                } else {
                    ref.setValue(toggleOn)
                }
            },
    ){
        Row (
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "$number",
                fontFamily = satoshiFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 19.sp,
                color = black,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Spacer(
                modifier = Modifier
                    .height(25.dp)
                    .width(2.dp)
                    .background(black)
                    .clip(RoundedCornerShape(40))
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "$Device",
                fontFamily = satoshiFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 19.sp,
                color = black,
                textAlign = TextAlign.Center
            )
        }

    }

}

@Composable
fun mySwitchPreview(number: Int, Device: String, database: String = "tes", path: String = "tes",modifier: Modifier = Modifier){
    var toggleOn by remember{ mutableStateOf(false) }
    var bgColor by remember { mutableStateOf(white2) }


    Box (
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(25))
            .background(
                if (toggleOn) surface_green else surface_variant
            )
            .size(width = 190.dp, height = 50.dp)
            .clickable {
                toggleOn = !toggleOn
//                val ref = database.getReference(path)
//
//                if (toggleOn) {
//                    ref.setValue(toggleOn)
//                } else {
//                    ref.setValue(toggleOn)
//                }
            }
    ){
        Row (
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "$number",
                fontFamily = satoshiFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 19.sp,
                color = black,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Spacer(
                modifier = Modifier
                    .height(25.dp)
                    .width(2.dp)
                    .background(black)
                    .clip(RoundedCornerShape(40))
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "$Device",
                fontFamily = satoshiFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 19.sp,
                color = black,
                textAlign = TextAlign.Center
            )
        }

    }

}

@Composable
fun TopScreen(modifier: Modifier = Modifier) {
    Column (
        modifier = Modifier
            .padding(20.dp)
    ){
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Welcome to Your",
            fontFamily = satoshiFamily,
            fontWeight = FontWeight.Normal,
            color = gray,
            fontSize = 18.sp,
            modifier = modifier

        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text ="Gedung Wallet 1",
            fontFamily = satoshiFamily,
            fontWeight = FontWeight.Black,
            fontSize = 24.sp,
            color = black
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun mainPage(){
    val ViewModel = viewModel{ DatabaseListenerViewModel() }
    val pagerState = rememberPagerState(pageCount = { 3 }, initialPage = 0)
    HorizontalPager(
        state = pagerState,
        userScrollEnabled = true
    ) {
            page ->
        when(page){
            0 -> PrimaryBox(
                id = 1,
                suhu = ViewModel.getData("Lantai1/Suhu").collectAsState().value,
                kelembapan = ViewModel.getData("Lantai1/Kelembapan").collectAsState().value,
                gas = ViewModel.getData("Lantai1/PPM").collectAsState().value
                )
            1 -> PrimaryBox(
                id = 2,
                suhu = ViewModel.getData("Lantai2/Suhu").collectAsState().value,
                kelembapan = ViewModel.getData("Lantai2/Kelembapan").collectAsState().value,
                gas = ViewModel.getData("Lantai2/PPM").collectAsState().value
                )
            2 -> PrimaryBox(
                id = 3,
                suhu = ViewModel.getData("Lantai3/Suhu").collectAsState().value,
                kelembapan = ViewModel.getData("Lantai3/Kelembapan").collectAsState().value,
                gas = ViewModel.getData("Lantai3/PPM").collectAsState().value
                )
        }

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun mainPagePreview(){

    val pagerState = rememberPagerState(pageCount = { 3 }, initialPage = 0)
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = true
        ) {
            page -> 
            when(page){
                0 -> PrimaryBox(id = 1, suhu = 32, kelembapan = 55, gas = 3)
                1 -> PrimaryBox(id = 2, suhu = 35, kelembapan = 45, gas = 4)
                2 -> PrimaryBox(id = 3, suhu = 22, kelembapan = 75, gas = 7)
            }

        }

}

@Composable
fun PrimaryBox(id : Any, suhu: Any, kelembapan: Any, gas: Any, modifier: Modifier= Modifier){
    Box (
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(20))
            .fillMaxWidth()
            .background(orange)
    ){

        Column (
            modifier = Modifier
                .padding(24.dp)
        ){
            Text(
                text = "Lantai $id",
                fontFamily = satoshiFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = white
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Row {
                    Box (
                        modifier = Modifier
                            //.padding(8.dp)
                            .clip(RoundedCornerShape(20))
                            .background(orangeMute)
                            .size(50.dp)
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.suhu),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column (
                        horizontalAlignment = Alignment.Start

                    ){
                        Text(
                            text = "$suhu°C",
                            fontFamily = satoshiFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = white,
                        )
                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "Temperature",
                            fontFamily = satoshiFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp,
                            color = white
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Box (
                    modifier = Modifier
                        //.padding(8.dp)
                        .clip(RoundedCornerShape(20))
                        .background(orangeMute)
                        .size(50.dp)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.water),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column (
                    horizontalAlignment = Alignment.Start

                ){
                    Text(
                        text = "$kelembapan%",
                        fontFamily = satoshiFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = white,
                    )
                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Humidity",
                        fontFamily = satoshiFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                        color = white
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Box (
                    modifier = Modifier
                        //.padding(8.dp)
                        .clip(RoundedCornerShape(20))
                        .background(orangeMute)
                        .size(50.dp)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.smoke),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column (
                    horizontalAlignment = Alignment.Start

                ){
                    Text(
                        text = "$gas Ppm",
                        fontFamily = satoshiFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = white,
                    )
                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Amonia Gas",
                        fontFamily = satoshiFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 15.sp,
                        color = white
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun IoT() {

    MarkasIoTTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = ivory
        ) {
            Column (
                modifier = Modifier
                    .padding(8.dp)
            ){
                TopScreen()
                Spacer(modifier = Modifier.height(32.dp))
                Box(){
                    mainPagePreview()
                }
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    text = "Control",
                    fontFamily = satoshiFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .padding(16.dp)
                )
                Row {
                    mySwitchPreview(
                        number = 1,
                        Device = "Mistmaker",
                    )
                    mySwitchPreview(
                        number = 2,
                        Device= "Kipas"
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Page(){

    // Buat variabel untuk menyimpan list composable
    val pages = listOf(
        Text("Halaman 1"),
        Text("Halaman 2"),
        Text("Halaman 3")
    )

    // Buat state pager
    val pagerState = rememberPagerState {
        pages.size
    }


    // Tampilkan horizontal pager
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) { page ->
        if (page == pagerState.currentPage){
            pages[pagerState.currentPage]
        }
    }


}