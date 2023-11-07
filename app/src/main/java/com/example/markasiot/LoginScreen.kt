package com.example.markasiot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.markasiot.ui.theme.MarkasIoTTheme
import com.example.markasiot.ui.theme.PlusJakartaSans


class LoginScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarkasIoTTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(){
    MarkasIoTTheme {
            Column (
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 28.dp)
            ) {
                TopScreen(
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                MidleScreen(
                    modifier = Modifier
                        .padding(top = 40.dp, bottom = 48.dp)
                )
                Bottom(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {}
                )
            }
        }


}

@Composable
fun TopScreen(modifier: Modifier = Modifier){
    Column (
        modifier = modifier
    ){
        Text(
            text = "Selamat Datang!",
            fontSize = 24.sp,
            fontFamily = PlusJakartaSans,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text ="Sobat Walet",
            fontSize = 18.sp,
            fontFamily = PlusJakartaSans,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
@Composable
fun MidleScreen(modifier: Modifier = Modifier){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.loginn),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(290.dp)
        )
        Text(
            text = "We're Like Swallows in Teamwork's Grandeur",
            fontSize = 18.sp,
            fontFamily = PlusJakartaSans,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 28.dp)
                .align(Alignment.CenterHorizontally)

        )
    }
}

@Composable
fun Bottom(
    onClick: () -> Unit,
    modifier: Modifier = Modifier){
    Button(
        //elevation = ,
        onClick = { onClick },
        modifier = modifier
    ) {
        Image(painter = painterResource(id = R.drawable.google),
            contentDescription ="",
            modifier = Modifier.size(35.dp)
            )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "Sign in with Google account",
            fontSize = 16.sp,
            fontFamily = PlusJakartaSans,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    MarkasIoTTheme {
        Surface (
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background

        ){
            Column (
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 28.dp)
            ) {
                TopScreen(
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                MidleScreen(
                    modifier = Modifier
                        .padding(top = 40.dp, bottom = 48.dp)
                )
                Bottom(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {}
                )
            }
        }
    }
}
