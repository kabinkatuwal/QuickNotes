package com.example.finalprojectquicknotes.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    var searchText by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* open drawer */ }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menu")
                }
                Text(
                    text = "Quick Notes",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "More")
                }
            }

            // Search Bar
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Search your thoughts...") },
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = null)
                },
                shape = RoundedCornerShape(50),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF0F0F0),
                    focusedContainerColor = Color(0xFFF0F0F0),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Scrollable note list
            LazyColumn {
                item {
                    SectionHeader("Today")
                }//items in here are placeholders
                item {
                    NoteCard(
                        title = "Project Aurora Brainstorming",
                        time = "10:45 AM",
                        preview = "Consider utilizing a bento-grid layout for the new dashboard. Need to focus on tonal...",
                        tag = "DESIGN"
                    )
                }
                item {
                    NoteCard(
                        title = "Weekly Grocery Checklist",
                        time = "09:12 AM",
                        preview = "Almond milk, Sourdough bread, Avocados, Greek yogurt, Fresh basil, Sea salt, and da..."
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    SectionHeader("Yesterday")
                }
                item {
                    NoteCard(
                        title = "Digital Journal Entry",
                        time = "Mar 22",
                        preview = "Reflecting on the progre...",
                        showIcon = true
                    )
                }
                item {
                    NoteCard(
                        title = "Voice Memo: UI Rhythms",
                        time = "Mar 22",
                        preview = "Audio recording from th...",
                        showMicIcon = true
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = { },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add note")
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun NoteCard(
    title: String,
    time: String,
    preview: String,
    tag: String? = null,
    showIcon: Boolean = false,
    showMicIcon: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF7F7F9)
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            // Optional icon on the left
            if (showIcon || showMicIcon) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE8E4F4)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (showMicIcon) Icons.Filled.Mic else Icons.Filled.Description,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = Color(0xFF6C5FC7)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
            }

            // Title + preview + tag
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = preview,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 2
                )
                if (tag != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFFE8E4F4))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = tag,
                            fontSize = 10.sp,
                            color = Color(0xFF6C5FC7),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Time on the right
            Text(
                text = time,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.End
            )
        }
    }
}