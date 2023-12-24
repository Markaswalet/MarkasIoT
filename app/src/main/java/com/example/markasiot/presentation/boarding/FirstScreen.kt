package com.example.markasiot.presentation.boarding


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.markasiot.R
import com.example.markasiot.ui.theme.MarkasIoTTheme
import com.example.markasiot.ui.theme.PlusJakartaSans

@Composable
fun BoardingPage() {
    MarkasIoTTheme {
        Surface(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            MainPage(
                modifier = Modifier
            )
        }
    }
}

@Composable
fun MainPage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color(0xfff5f6f8))
    ) {
        Image(
            painter = painterResource(id = R.drawable.burung),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )


            Box(
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(
                        x = 1.dp,
                        y = 522.dp
                    )
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                    .background(color = Color(0xfff5f6f8))
            )
            Text(
                text = "Monitoring rumah walet Anda!",
                fontSize = 24.sp,
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.scrim,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(
                        x = 75.dp,
                        y = 572.dp
                    )
                    .requiredWidth(width = 242.dp)
            )

            Text(
                text = "We're Like Swallows in Teamwork's Grandeur",
                fontSize = 14.sp,
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 17.dp, y = 652.dp)
                    .requiredWidth(width = 338.dp)
            )



            Box(
                modifier = Modifier
                    .align(alignment = Alignment.TopCenter)
                    .offset(
                        x = 0.dp,
                        y = 711.dp
                    )
                    .requiredWidth(width = 328.dp)
                    .requiredHeight(height = 52.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top)
                ) {
                    Box(
                        modifier = Modifier
                            .requiredWidth(width = 328.dp)
                            .requiredHeight(height = 52.dp)
                            .clip(shape = RoundedCornerShape(45.dp))
                            .background(color = Color(0xff006c4a))
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .offset(
                            x = 0.dp,
                            y = (-0.5).dp
                        )
                ) {
                    Text(
                        text = "Mulai sekarang!",
                        fontSize = 24.sp,
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

        }
    }




@Preview(showBackground = true)
@Composable
fun AppPreview() {
    BoardingPage()
}
