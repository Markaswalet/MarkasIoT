package com.example.markasiot.presentation.profile

import android.util.Log
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonColors
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.navigation.compose.rememberNavController
import com.example.markasiot.data.sampleData
import com.example.markasiot.data.sampleSwitchData
import com.example.markasiot.presentation.home.HomePager
import com.example.markasiot.presentation.home.SwitchPage
import com.example.markasiot.presentation.home.UserDisplay
import com.example.markasiot.presentation.nav.BottomNav
import com.example.markasiot.presentation.sign_in.UserData
import com.example.markasiot.R
import com.example.markasiot.ui.theme.MarkasIoTTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(userData: UserData?,onSignOutClick: () -> Unit){
    MarkasIoTTheme{
        Surface (
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 5.dp
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CirclePhoto(userData = userData)
                Spacer(modifier = Modifier.height(28.dp))
                ProfilePage(userData = userData)
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = onSignOutClick,
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = ButtonDefaults.buttonElevation(5.dp)){
                    Text(
                        text = "Sign Out",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }
    }

}

@Composable
fun ProfileScreenDark(userData: UserData?,onSignOutClick: () -> Unit){
    MarkasIoTTheme(useDarkTheme = true) {
        Surface (
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 5.dp
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CirclePhoto(userData = userData)
                Spacer(modifier = Modifier.height(28.dp))
                ProfilePage(userData = userData)
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = onSignOutClick,
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = ButtonDefaults.buttonElevation(5.dp)){
                    Text(
                        text = "Sign Out",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }
    }

}

@Composable
fun CirclePhoto(userData: UserData?){
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if(userData?.profilePictureUrl != null){
            Log.d("TAG", "Profile picture URL: ${userData.profilePictureUrl}")
            Card (
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .padding(top = 28.dp)
                    .clip(CircleShape)
                    .border(
                        width = 4.dp,
                        color = MaterialTheme.colorScheme.tertiary,
                        shape = CircleShape
                    )

            ){
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(userData.profilePictureUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (userData?.username != null){
            Text(
                text = "${userData?.username}",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )
        }
    }
}

@Composable
fun ProfilePage(userData: UserData?){
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(10))

    ) {
        if (userData?.userId != null) {
            ProfileDetails(
                image = R.drawable.user_id,
                title = "UID",
                details = userData.userId
            )
            
            //Spacer(modifier = Modifier.height(4.dp))
            Divider(
                thickness = 2.dp,
                color = getTonalElevation(baseColor = MaterialTheme.colorScheme.surface, elevation = 5.dp)
            )
        }

        if(userData?.email != null){
            ProfileDetails(
                image = R.drawable.email,
                title = "Email",
                details = userData.email
            )

            Divider(
                thickness = 2.dp,
                color = getTonalElevation(baseColor = MaterialTheme.colorScheme.surface, elevation = 5.dp)
            )
        }

        if(userData?.phoneNumber != null){
            ProfileDetails(
                image = R.drawable.bi_phone,
                title = "Phone Number",
                details = userData.phoneNumber
            )

        }

        
    }
}

@Composable
fun ProfileDetails(@DrawableRes image: Int, title: String, details: String){
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    Row (modifier= Modifier.padding(16.dp)){
        Image(
            painter = painterResource(id = image),
            contentDescription = "",
            modifier = Modifier.size(24.dp))

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(top = 2.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = details,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            maxLines = 1,
            modifier = Modifier.clickable {
                clipboardManager.setText(AnnotatedString(details))
                Toast.makeText(
                    context,
                    "$title copied to clipboard",
                    Toast.LENGTH_LONG
                ).show()
            }
        )

    }
}

fun getTonalElevation(baseColor: Color, elevation: androidx.compose.ui.unit.Dp): Color{
    val tonalElevationColor = baseColor.copy(alpha = 0.2f) // Adjust alpha as needed

    return tonalElevationColor
}

@Preview(showBackground = true)
@Composable
fun CirclePhotoPreview(){
    MarkasIoTTheme {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 5.dp,

            ){
            Column(modifier = Modifier.padding(16.dp)) {
                CirclePhoto(userData = UserData(username = "Arya Adikusuma", profilePictureUrl = "dfd"))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileBodyPreview(){
    MarkasIoTTheme {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 5.dp,

            ){
            Column(modifier = Modifier.padding(16.dp)) {
                ProfilePage(userData = UserData(userId = "22378728", email = "arya.adikusuma", phoneNumber = "0822458997"))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview(){
    MarkasIoTTheme {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 5.dp,
            modifier = Modifier.fillMaxSize()
            ){
            ProfileScreen(userData = UserData(username= "Arya Adikusuma", userId = "22378728", email = "arya.adikusuma", phoneNumber = "0822458997", profilePictureUrl = "jdd"), onSignOutClick ={})
        }
    }
}