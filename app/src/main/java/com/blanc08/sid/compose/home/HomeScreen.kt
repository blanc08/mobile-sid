package com.blanc08.sid.compose.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blanc08.sid.ui.theme.AppTheme
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun HomeScreen() {
    Column {
        Header()
        MainContent()
    }
}

@Composable
fun Header() {
    Surface(
        tonalElevation = 5.dp,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                GreetingText()
                UtilIcons()
            }
            ExpensesCard()
            Spacer(modifier = Modifier.height(16.dp))
            BalanceRow()
        }
    }
}

@Composable
fun UtilIcons() {
    Row {
        IconButton(
            onClick = {},
            modifier = Modifier.size(20.dp)
        ) {
            Icon(
                Icons.Filled.ChatBubble,
                contentDescription = "Chat",
                tint = MaterialTheme.colorScheme.background,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(
            onClick = {},
            modifier = Modifier.size(20.dp)
        ) {
            Icon(
                Icons.Filled.DarkMode,
                contentDescription = "Toggle Dark Mode",
                tint = MaterialTheme.colorScheme.background,
            )
        }
    }
}

@Composable
fun GreetingText() {
    Column {
        Text(
            "Halo, Bagus!",
            color = MaterialTheme.colorScheme.surface,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Selamat Malam",
            color = MaterialTheme.colorScheme.surface,
            fontSize = 16.sp
        )
    }
}

@Composable
fun MainContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        PremiumCard()
        Spacer(modifier = Modifier.height(16.dp))
        NewsCard()
    }
}

@Composable
fun ExpensesCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Pengeluaran bulan September", fontWeight = FontWeight.Bold)
            Text(
                "Tap untuk lihat",
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun BalanceRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BalanceItem("Total Saldo", "Rp*********", Icons.Rounded.Home)
        BalanceItem("Tabungan", "Mulai Nabung", Icons.Rounded.Home)
        BalanceItem("Dompet Kamu", "1 Dompet", Icons.Rounded.Home)
    }
}

@Composable
fun BalanceItem(title: String, value: String, icon: ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            icon,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = title,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(title, fontSize = 12.sp)
        Text(value, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun PremiumCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
//            Icon(
//                imageVector = Icons.Default.Add,
//                contentDescription = "Premium",
//                modifier = Modifier
//                    .size(48.dp)
//                    .clip(CircleShape)
//                    .padding(8.dp)
//            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text("Free", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(10.dp))
                Text("Dapatkan kemudahan atur keuangan")
            }
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "More"
            )
        }
    }
}

@Composable
fun NewsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Kabar", fontWeight = FontWeight.Bold)
            // Add news content here
        }
    }
}

@Preview()
@Composable()
fun HomeScreenPreview() {
    AppTheme(darkTheme = false) {
        Header()
    }
}
