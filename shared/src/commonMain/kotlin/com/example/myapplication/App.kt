package com.example.myapplication

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Data statis profil pengguna.
 * Dipisah dari composable agar mudah diganti/diambil dari sumber data lain.
 */
private object ProfileData {
    const val NAME = "Rakha Daffa Tama Truski"
    const val NIM = "124140196"
    const val BIO = "Mahasiswa Teknik Informatika ITERA | Mobile App Developer Enthusiast"
    const val EMAIL = "124140196@student.itera.ac.id"
    const val PHONE = "+62 812-3456-7890"
    const val LOCATION = "Lampung, Indonesia"

    /** Dipakai sebagai pengganti foto profil pada [ProfileHeader]. */
    const val INITIALS = "RT"
}

/** Model baris informasi kontak yang dirender oleh [InfoItem]. */
private data class ContactInfo(
    val icon: ImageVector,
    val label: String,
    val value: String
)

private val contactList = listOf(
    ContactInfo(Icons.Filled.Email, "Email", ProfileData.EMAIL),
    ContactInfo(Icons.Filled.Phone, "Phone", ProfileData.PHONE),
    ContactInfo(Icons.Filled.LocationOn, "Location", ProfileData.LOCATION)
)

/**
 * Komposable #1 —Bagian atas profil berisi avatar circular, nama, NIM, dan bio singkat.
 *
 * Avatar memakai `Box` dengan [CircleShape]. Karena asset foto belum tersedia,
 * inisial [initials] yang ditampilkan di dalam lingkaran (bisa diganti `Image`/`Icon`).
 */
@Composable
fun ProfileHeader(
    name: String,
    nim: String,
    bio: String,
    initials: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(112.dp)
                .shadow(elevation = 10.dp, shape = CircleShape, clip = false)
                .clip(CircleShape)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF3B82F6), Color(0xFF8B5CF6))
                    ),
                    shape = CircleShape
                )
                .border(width = 4.dp, color = Color.White, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initials,
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = name,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Surface(
            shape = RoundedCornerShape(50),
            color = MaterialTheme.colorScheme.secondaryContainer
        ) {
            Text(
                text = "NIM $nim",
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = bio,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Komposable #2 —Satu baris informasi kontak berisi Icon + Label + Value.
 */
@Composable
fun InfoItem(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label.uppercase(),
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

/**
 * Komposable #3 —Card pembungkus yang menampung daftar [InfoItem].
 */
@Composable
fun ProfileCard(
    contacts: List<ContactInfo>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Informasi Kontak",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            contacts.forEach { contact ->
                InfoItem(
                    icon = contact.icon,
                    label = contact.label,
                    value = contact.value
                )
            }
        }
    }
}

@Composable
fun App() {
    var isVisible by remember { mutableStateOf(true) }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ProfileHeader(
                    name = ProfileData.NAME,
                    nim = ProfileData.NIM,
                    bio = ProfileData.BIO,
                    initials = ProfileData.INITIALS
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { isVisible = !isVisible },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = if (isVisible) {
                            Icons.Filled.KeyboardArrowUp
                        } else {
                            Icons.Filled.KeyboardArrowDown
                        },
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isVisible) "Sembunyikan Detail" else "Tampilkan Detail",
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    ProfileCard(contacts = contactList)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Platform: ${getPlatform().name}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
