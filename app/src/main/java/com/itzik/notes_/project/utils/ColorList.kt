package com.itzik.notes_.project.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.itzik.notes_.R

@Composable
fun provideColorList(): List<Color> {
     return listOf(
          colorResource(id = R.color.black),
          colorResource(id = R.color.intermediate_red_1),
          colorResource(id = R.color.light_red),
          colorResource(id = R.color.intermediate_red_2),
          colorResource(id = R.color.red),
          colorResource(id = R.color.intermediate_red_3),
          colorResource(id = R.color.crimson),
          colorResource(id = R.color.intermediate_red_4),
          colorResource(id = R.color.fire_red),
          colorResource(id = R.color.intermediate_red_5),
          colorResource(id = R.color.deep_red),
          colorResource(id = R.color.intermediate_red_6),
          colorResource(id = R.color.cinnamon),
          colorResource(id = R.color.intermediate_red_7),
          colorResource(id = R.color.light_brown),
          colorResource(id = R.color.vibrant_orange),
          colorResource(id = R.color.intermediate_orange_2),
          colorResource(id = R.color.burnt_orange),
          colorResource(id = R.color.muted_yellow),
          colorResource(id = R.color.intermediate_yellow_1),
          colorResource(id = R.color.yellow_green),
          colorResource(id = R.color.intermediate_yellow_2),
          colorResource(id = R.color.bright_yellow),
          colorResource(id = R.color.very_light_green),
          colorResource(id = R.color.intermediate_green_1),
          colorResource(id = R.color.light_green),
          colorResource(id = R.color.intermediate_green_2),
          colorResource(id = R.color.spring_green),
          colorResource(id = R.color.intermediate_green_3),
          colorResource(id = R.color.lime_green),
          colorResource(id = R.color.intermediate_green_4),
          colorResource(id = R.color.pale_green),
          colorResource(id = R.color.intermediate_green_5),
          colorResource(id = R.color.green),
          colorResource(id = R.color.intermediate_green_6),
          colorResource(id = R.color.dark_green),
          colorResource(id = R.color.navy_blue),
          colorResource(id = R.color.intermediate_blue_1),
          colorResource(id = R.color.darker_blue),
          colorResource(id = R.color.intermediate_blue_2),
          colorResource(id = R.color.deep_blue),
          colorResource(id = R.color.intermediate_blue_3),
          colorResource(id = R.color.purple_blue),
          colorResource(id = R.color.intermediate_blue_4),
          colorResource(id = R.color.dark_steel_blue),
          colorResource(id = R.color.intermediate_blue_5),
          colorResource(id = R.color.blue_green),
          colorResource(id = R.color.intermediate_blue_6),
          colorResource(id = R.color.light_steel_blue),
          colorResource(id = R.color.lighter_blue),
          colorResource(id = R.color.intermediate_blue_8),
          colorResource(id = R.color.sky_blue),
          colorResource(id = R.color.intermediate_blue_9),
          colorResource(id = R.color.aqua_blue),
          colorResource(id = R.color.intermediate_blue_10),
          colorResource(id = R.color.semi_transparent_blue_green),
          colorResource(id = R.color.deeper_purple),
          colorResource(id = R.color.intermediate_purple_1),
          colorResource(id = R.color.deep_purple),
          colorResource(id = R.color.intermediate_purple_2),
          colorResource(id = R.color.dark_purple),
          colorResource(id = R.color.intermediate_purple_3),
          colorResource(id = R.color.purple),
          colorResource(id = R.color.intermediate_purple_4),
          colorResource(id = R.color.light_purple),
          colorResource(id = R.color.light_pink),
          colorResource(id = R.color.intermediate_pink_1),
          colorResource(id = R.color.rose_pink),
          colorResource(id = R.color.intermediate_pink_2),
          colorResource(id = R.color.peach_pink),
          colorResource(id = R.color.intermediate_pink_3),
          colorResource(id = R.color.coral_pink),
          colorResource(id = R.color.intermediate_pink_4),
          colorResource(id = R.color.pink),
          colorResource(id = R.color.charcoal_gray),
          colorResource(id = R.color.intermediate_gray_1),
          colorResource(id = R.color.dark_gray),
          colorResource(id = R.color.intermediate_gray_2),
          colorResource(id = R.color.gray),
          colorResource(id = R.color.intermediate_gray_3),
          colorResource(id = R.color.light_gray),
          colorResource(id = R.color.intermediate_gray_4),
          colorResource(id = R.color.medium_gray),
          colorResource(id = R.color.intermediate_gray_5),
          colorResource(id = R.color.cool_gray),
          colorResource(id = R.color.intermediate_gray_6),
          colorResource(id = R.color.slate_gray),
          colorResource(id = R.color.intermediate_gray_7),
          colorResource(id = R.color.smoky_gray)
     )
}



@Composable
fun gradientBrush(reverse: Boolean): Brush {
     return Brush.linearGradient(
          colors = if (!reverse) listOf(
               colorResource(R.color.sky_blue),
               colorResource(R.color.white),
               colorResource(R.color.white),
               colorResource(R.color.sky_blue),
          ) else listOf(
               colorResource(R.color.sky_blue),
               colorResource(R.color.sky_blue),
               colorResource(R.color.white),
          )
     )
}