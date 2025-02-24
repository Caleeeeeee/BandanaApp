package com.example.bandana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bandana.ui.theme.BandanaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BandanaTheme {
                LoginScreen()
            }
        }
    }
}

@Composable
fun LoginScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoggedIn by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    if (isLoggedIn) {
        SidebarMenuScreen()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Login", fontSize = 24.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") }
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {
                if (username == "admin" && password == "password") {
                    isLoggedIn = true
                } else {
                    errorMessage = "Invalid Credentials"
                }
            }) {
                Text("Login")
            }

            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = errorMessage, color = Color.Red)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SidebarMenuScreen() {
    var menuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("UE BANDANA", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Red)
            )
        },
        content = { paddingValues ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (menuExpanded) {
                    SidebarMenu { menuExpanded = false }
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = { menuExpanded = !menuExpanded }) {
                        Text("Toggle Menu")
                    }
                }
            }
        }
    )
}

@Composable
fun SidebarMenu(onClose: () -> Unit) {
    Column(
        modifier = Modifier
            .width(200.dp)
            .fillMaxHeight()
            .background(Color.LightGray)
            .padding(10.dp)
    ) {
        val menuItems = listOf(
            "User Reports", "Guidance Counsellors",
            "Freedom Wall", "Diary", "Mental Health Tips",
            "Notifications", "Sign Out"
        )

        menuItems.forEach { title ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable { onClose() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(title, fontSize = 16.sp, color = Color.Black)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SidebarMenuPreview() {
    BandanaTheme {
        SidebarMenuScreen()
    }
}
