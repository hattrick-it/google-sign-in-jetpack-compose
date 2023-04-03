package com.hattrick.googlesignin.presentation.compose

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.hattrick.googlesignin.R
import com.hattrick.googlesignin.presentation.viewmodel.LoginViewModel
import com.hattrick.googlesignin.presentation.viewmodelstate.LoginStatus
import com.hattrick.googlesignin.util.AuthResultContract
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    val loginViewModel = getViewModel<LoginViewModel>()
    val state by loginViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val googleSignInClient = get<GoogleSignInClient>()

    Scaffold(
        content = {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(color = White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (state.loginStatus) {
                    LoginStatus.Initial, LoginStatus.Failure -> {
                        Text(
                            text = stringResource(R.string.welcome),
                            color = Black,
                            fontSize = 40.sp
                        )

                        Spacer(modifier = Modifier.height(40.dp))

                        ButtonGoogleSignIn(
                            onGoogleSignInCompleted = { loginViewModel.authenticateWithBackend(it) },
                            onError = { loginViewModel.onLoginError() },
                        )

                        if (state.loginStatus == LoginStatus.Failure) {
                            Toast.makeText(
                                context,
                                stringResource(R.string.toast_error),
                                Toast.LENGTH_SHORT
                            ).show()
                            //If the sign in fails the account is forgotten
                            // so the user could retry with the same account or a new one
                            googleSignInClient.signOut()
                            googleSignInClient.revokeAccess()
                        }
                    }
                    LoginStatus.Loading -> {
                        CircularProgressIndicator(
                            color = Black,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(60.dp)
                        )
                    }
                    LoginStatus.Success -> onLoginSuccess()
                }
            }
        }
    )
}

@Composable
fun ButtonGoogleSignIn(
    onGoogleSignInCompleted: (String) -> Unit,
    onError: () -> Unit,
    googleSignInClient: GoogleSignInClient = get(),
) {
    val coroutineScope = rememberCoroutineScope()
    val signInRequestCode = 1

    val authResultLauncher =
        rememberLauncherForActivityResult(contract = AuthResultContract(googleSignInClient)) {
            try {
                val account = it?.getResult(ApiException::class.java)
                if (account == null) {
                    onError()
                } else {
                    coroutineScope.launch {
                        onGoogleSignInCompleted(account.idToken!!)
                    }
                }
            } catch (e: ApiException) {
                onError()
            }
        }

    Button(
        onClick = { authResultLauncher.launch(signInRequestCode) },
        modifier = Modifier
            .width(300.dp)
            .height(45.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(White),
        elevation = ButtonDefaults.elevation(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = "Google icon",
                tint = Color.Unspecified,
            )
            Text(
                text = stringResource(R.string.sign_in_button),
                color = Black,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}