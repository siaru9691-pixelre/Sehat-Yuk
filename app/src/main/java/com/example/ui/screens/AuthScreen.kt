package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SDGBadge
import com.example.ui.theme.*

@Composable
fun AuthScreen(
    onLoginSuccess: () -> Unit
) {
    var isLoginTab by remember { mutableStateOf(true) }
    var email by remember { mutableStateOf("raditya.p@kampus.ac.id") }
    var name by remember { mutableStateOf("Raditya Pratama") }
    var password by remember { mutableStateOf("••••••••") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(true) }
    var showForgotPasswordDialog by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(scrollState)
            .padding(24.dp)
            .testTag("auth_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        // Center SehatYuk Logo
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(22.dp))
                .background(
                    Brush.linearGradient(listOf(Color(0xFF00D284), EmeraldPrimary, EmeraldDark))
                )
                .border(2.dp, Color.White, RoundedCornerShape(22.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Logo",
                tint = Color.White,
                modifier = Modifier.size(34.dp)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        SDGBadge(text = "SDG 3: Hidup Sehat & Sejahtera")

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = if (isLoginTab) "Masuk ke SehatYuk" else "Daftar Akun Baru",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.ExtraBold,
                color = TextCharcoal
            )
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Pantau progres kesehatanmu setiap hari bersama ribuan mahasiswa lainnya",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = TextSecondary,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Tab Selector Card
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFEDF2F7)
        ) {
            Row(
                modifier = Modifier.padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Masuk Tab
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { isLoginTab = true }
                        .testTag("login_tab_btn"),
                    shape = RoundedCornerShape(12.dp),
                    color = if (isLoginTab) SurfaceWhite else Color.Transparent,
                    shadowElevation = if (isLoginTab) 2.dp else 0.dp
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Login,
                            contentDescription = null,
                            tint = if (isLoginTab) EmeraldDark else TextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Masuk",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = if (isLoginTab) FontWeight.Bold else FontWeight.Medium,
                                color = if (isLoginTab) EmeraldDark else TextSecondary
                            )
                        )
                    }
                }

                // Daftar Tab
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { isLoginTab = false }
                        .testTag("register_tab_btn"),
                    shape = RoundedCornerShape(12.dp),
                    color = if (!isLoginTab) SurfaceWhite else Color.Transparent,
                    shadowElevation = if (!isLoginTab) 2.dp else 0.dp
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.PersonAdd,
                            contentDescription = null,
                            tint = if (!isLoginTab) EmeraldDark else TextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Daftar Akun",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = if (!isLoginTab) FontWeight.Bold else FontWeight.Medium,
                                color = if (!isLoginTab) EmeraldDark else TextSecondary
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Input Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
            border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (!isLoginTab) {
                    Column {
                        Text(
                            text = "Nama Lengkap",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = TextCharcoal)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            placeholder = { Text("Nama Mahasiswa") },
                            modifier = Modifier.fillMaxWidth().testTag("name_input"),
                            shape = RoundedCornerShape(14.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = AppBackground,
                                unfocusedContainerColor = AppBackground,
                                focusedBorderColor = EmeraldPrimary,
                                unfocusedBorderColor = CardBorder
                            )
                        )
                    }
                }

                // Email Field
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Email Mahasiswa / Umum",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextCharcoal
                            )
                        )
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = MintLight
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.School,
                                    contentDescription = null,
                                    tint = EmeraldDark,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Kampus Partner",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = EmeraldDark,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth().testTag("email_input"),
                        leadingIcon = {
                            Icon(Icons.Outlined.Mail, contentDescription = null, tint = TextMuted)
                        },
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = AppBackground,
                            unfocusedContainerColor = AppBackground,
                            focusedBorderColor = EmeraldPrimary,
                            unfocusedBorderColor = CardBorder
                        )
                    )
                }

                // Password Field
                Column {
                    Text(
                        text = "Kata Sandi",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextCharcoal
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.fillMaxWidth().testTag("password_input"),
                        leadingIcon = {
                            Icon(Icons.Outlined.Lock, contentDescription = null, tint = TextMuted)
                        },
                        trailingIcon = {
                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                Icon(
                                    imageVector = if (isPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                    contentDescription = "Toggle password"
                                )
                            }
                        },
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = AppBackground,
                            unfocusedContainerColor = AppBackground,
                            focusedBorderColor = EmeraldPrimary,
                            unfocusedBorderColor = CardBorder
                        )
                    )
                }

                // Remember Me & Forgot Password
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { rememberMe = !rememberMe }
                    ) {
                        Checkbox(
                            checked = rememberMe,
                            onCheckedChange = { rememberMe = it },
                            colors = CheckboxDefaults.colors(checkedColor = EmeraldPrimary)
                        )
                        Text(
                            text = "Ingat saya",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = TextCharcoal,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }

                    Text(
                        text = "Lupa Kata Sandi?",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = WaterBlue,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .clickable { showForgotPasswordDialog = true }
                            .testTag("forgot_password_btn")
                    )
                }

                // Student Streak Promo Banner
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = MintSoft
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(EmeraldPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Klaim Daily Streak Pelajar",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldDark
                                )
                            )
                            Text(
                                text = "Dapatkan badge hidrasi & voucher makan sehat kampus",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = TextSecondary,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }

                // Primary Button
                Button(
                    onClick = onLoginSuccess,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("auth_submit_btn"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldDark)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = if (isLoginTab) "Masuk Sekarang" else "Daftar & Lanjut",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Divider
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(modifier = Modifier.weight(1f), color = CardBorder)
            Text(
                text = "  ATAU MASUK DENGAN  ",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = TextMuted,
                    fontWeight = FontWeight.Bold
                )
            )
            Divider(modifier = Modifier.weight(1f), color = CardBorder)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Social Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onLoginSuccess,
                modifier = Modifier.weight(1f).height(48.dp).testTag("google_login_btn"),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = SurfaceWhite)
            ) {
                Icon(Icons.Default.AccountCircle, contentDescription = "Google", tint = Color(0xFFEA4335))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Google", style = MaterialTheme.typography.labelLarge.copy(color = TextCharcoal, fontWeight = FontWeight.SemiBold))
            }

            OutlinedButton(
                onClick = onLoginSuccess,
                modifier = Modifier.weight(1f).height(48.dp).testTag("apple_login_btn"),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = SurfaceWhite)
            ) {
                Icon(Icons.Default.PhoneIphone, contentDescription = "Apple", tint = TextCharcoal)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Apple ID", style = MaterialTheme.typography.labelLarge.copy(color = TextCharcoal, fontWeight = FontWeight.SemiBold))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Guest Mode Button
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onLoginSuccess)
                .testTag("guest_mode_btn"),
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFEEF2FF)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.VisibilityOff,
                    contentDescription = null,
                    tint = WaterBlue,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Lanjut sebagai Tamu (Mode Anonim)",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = WaterBlue
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Data kebiasaan tetap tersimpan lokal di perangkat ini tanpa sinkronisasi akun.",
            style = MaterialTheme.typography.labelSmall.copy(
                color = TextMuted,
                textAlign = TextAlign.Center,
                fontSize = 11.sp
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }

    if (showForgotPasswordDialog) {
        AlertDialog(
            onDismissRequest = { showForgotPasswordDialog = false },
            title = { Text("Atur Ulang Kata Sandi") },
            text = { Text("Tautan pemulihan kata sandi telah dikirim ke $email.") },
            confirmButton = {
                Button(
                    onClick = { showForgotPasswordDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                ) {
                    Text("Mengerti")
                }
            }
        )
    }
}
