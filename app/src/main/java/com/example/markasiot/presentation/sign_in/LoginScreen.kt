package com.example.markasiot.presentation.sign_in

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.markasiot.R
import com.example.markasiot.presentation.nav.Graph
import com.example.markasiot.presentation.nav.Screens
import com.example.markasiot.ui.theme.MarkasIoTTheme
import com.example.markasiot.ui.theme.PlusJakartaSans
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch


class LoginScreen(val navController: NavController) : ComponentActivity() {
    private val googleAuthClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapclient = Identity.getSignInClient(applicationContext)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarkasIoTTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    val viewModel = viewModel<SignInViewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()

                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartIntentSenderForResult(),
                        onResult = { result ->
                            if (result.resultCode == RESULT_OK){
                                lifecycleScope.launch {
                                    val signInResult = googleAuthClient.signWithIntent(
                                        intent = result.data ?: return@launch
                                    )
                                    viewModel.onSignInResult(signInResult)
                                    Log.d("result", "${result.data}")
                                }
                            }
                        }
                    )

                    LaunchedEffect(key1 = state.isSignInSuccessful){
                        if(state.isSignInSuccessful){
                            Toast.makeText(
                                applicationContext,
                                "Sign In Successfully",
                                Toast.LENGTH_LONG
                            ).show()

                            navController.navigate(Graph.Home){
                                popUpTo(route = Graph.Authtentication){
                                    inclusive = true
                                }
                            }
                            viewModel.resetState()
                        }
                    }

                    AuthScreen(
                        state = state,
                        onSignInClick = {
                            lifecycleScope.launch {
                                val signInIntentSender = googleAuthClient.SignIn()

                                launcher.launch(
                                    IntentSenderRequest.Builder(
                                        signInIntentSender ?: return@launch
                                    ).build()
                                )
                            }
                        }

                    )
                }
            }
        }
    }
}

@Composable
fun AuthScreen(state: SignInState, onSignInClick: () -> Unit, modifier: Modifier = Modifier){
    MarkasIoTTheme {
        val context = LocalContext.current
        LaunchedEffect(key1 = state.signInError) {
            state.signInError?.let { error ->
                Toast.makeText(
                    context,
                    error,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background

        ) {
            Column(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 28.dp)
            ) {
                TopScreen(
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                MiddleScreen(
                    modifier = Modifier
                        .padding(top = 40.dp, bottom = 48.dp)
                )
                Bottom(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = onSignInClick
                )
            }
        }
    }

}

@Composable
fun TopScreen(modifier: Modifier = Modifier){
    Column (
        modifier = modifier
    ){
        Text(
            text = "Welcome!",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text ="Our Farmer",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
@Composable
fun MiddleScreen(modifier: Modifier = Modifier){
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
        onClick =  onClick,
        modifier = modifier
    ) {
        Image(painter = painterResource(id = R.drawable.google),
            contentDescription ="",
            modifier = Modifier.size(35.dp)
            )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "Continue with Google",
            fontSize = 16.sp,
            fontFamily = PlusJakartaSans,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .align(Alignment.Top)
                .padding(top = 3.dp)
        )
        
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
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

                MiddleScreen(
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
