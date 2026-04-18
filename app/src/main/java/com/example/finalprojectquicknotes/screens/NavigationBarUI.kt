package com.example.finalprojectquicknotes.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalprojectquicknotes.viewmodel.NavigationBarViewModel

@Composable
fun QuickNotesDrawer(
    viewModel: NavigationBarViewModel = viewModel(),
    onClose: () -> Unit,
    onNavigate: (String) -> Unit
) {
    var foldersExpanded by remember { mutableStateOf(true) }
    val selectedItem by viewModel.selectedItem.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(280.dp)
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Quick Notes",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = { onClose() }) {
                Icon(Icons.Filled.Close, contentDescription = "Close")
            }
        }

        DrawerItem(
            icon = Icons.Filled.Description,
            label = "All notes",
            selected = selectedItem == "all_notes",
            onClick = {
                viewModel.onItemSelected("all_notes")
                onNavigate("all_notes")
            }
        )

        // Folders
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { foldersExpanded = !foldersExpanded }
                .padding(vertical = 12.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Folder,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "Folders", modifier = Modifier.weight(1f))
            Icon(
                imageVector = if (foldersExpanded)
                    Icons.Filled.KeyboardArrowUp
                else
                    Icons.Filled.KeyboardArrowDown,
                contentDescription = null
            )
        }

        if (foldersExpanded) {
            SubfolderItem(label = "Work", onClick = {
                viewModel.onItemSelected("work")
                onNavigate("work")
            })
            SubfolderItem(label = "Personal", onClick = {
                viewModel.onItemSelected("personal")
                onNavigate("personal")
            })
        }

        DrawerItem(
            icon = Icons.Filled.Group,
            label = "Shared notes",
            selected = selectedItem == "shared_notes",
            onClick = {
                viewModel.onItemSelected("shared_notes")
                onNavigate("shared_notes")
            }
        )

        DrawerItem(
            icon = Icons.Filled.Delete,
            label = "Trash",
            selected = selectedItem == "trash",
            onClick = {
                viewModel.onItemSelected("trash")
                onNavigate("trash")
            }
        )
    }
}

@Composable
fun DrawerItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val background = if (selected)
        Color(0xFFE8EAF6)
    else
        Color.Transparent

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(background)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = label)
    }
}

@Composable
fun SubfolderItem(label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(start = 40.dp, top = 8.dp, bottom = 8.dp, end = 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "• $label",
            color = Color.Gray
        )
    }
}