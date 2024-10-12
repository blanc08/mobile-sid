package com.blanc08.sid.feature.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.blanc08.sid.designsystem.theme.AppTypography

@Composable
fun AboutRoute() {
    AboutScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen() {
    Column {
        TopAppBar(
            title = {
                Text(
                    style = AppTypography.titleLarge,
                    text = "About"
                )
            },
        )

        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            Text(
                text = "Ciputri, Kec. Pacet, Kabupaten Cianjur, Jawa Barat 43253"
            )
        }
    }
}
