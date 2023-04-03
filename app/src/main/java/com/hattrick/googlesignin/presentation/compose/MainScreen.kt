package com.hattrick.googlesignin.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hattrick.googlesignin.R


@Composable
fun MainScreen() {
    Scaffold(
        content = {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .background(color = Color.White),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.hello),
                    fontSize = 40.sp,
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = stringResource(R.string.lorem_ipsum),
                    fontSize = 25.sp,
                    textAlign = TextAlign.Start
                )
            }
        }
    )
}


