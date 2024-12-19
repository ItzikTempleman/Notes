package com.itzik.notes_.project.ui.composable_elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.itzik.notes_.project.utils.provideColorList
import com.itzik.notes_.R

@Composable
fun ColorPickerDialog(
    modifier: Modifier = Modifier,
    onColorSelected: (Color) -> Unit,
    onDismiss: () -> Unit
) {
    val colorList = listOf(
        colorResource(R.color.deep_blue),
        colorResource(R.color.muted_yellow),
        colorResource(R.color.dark_purple),
        colorResource(R.color.intermediate_green_2),
        colorResource(R.color.red),
        colorResource(R.color.sunset_orange),
        colorResource(R.color.black),
    )


    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp),
        colors = CardDefaults.cardColors(Color(0xff191c26)),
        shape = RoundedCornerShape(30.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            for(color in colorList){
                Box(
                    modifier = Modifier.size(38.dp)
                        .background(color, shape = CircleShape)
                        .clickable {
                            onColorSelected(color)
                            onDismiss()
                        }.padding(8.dp),

                )
            }
        }
    }
}