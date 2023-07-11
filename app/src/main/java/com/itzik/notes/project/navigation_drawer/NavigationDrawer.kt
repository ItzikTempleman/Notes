package com.itzik.notes.project.navigation_drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex


@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 64.dp),
        contentAlignment = Alignment.Center
    ) {
       // Text(text = "Header", fontSize = 60.sp)
    }
}

@Composable
fun DrawerBody(
    itemList: List<MenuItem>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onClick: () -> Unit,
) {
    LazyColumn(modifier) {
        items(items = itemList) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onClick()
                    }
                    .padding(16.dp)
            ) {
                Icon(imageVector = it.icon, contentDescription = it.contentDescription)
               Spacer(modifier = Modifier.width(16.dp))
//                Text(text = it.title, modifier = Modifier.weight(1f), style = itemTextStyle)
            }
        }
    }
}