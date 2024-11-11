package com.itzik.notes_.project.ui.composable_elements.dates

import com.itzik.notes_.R

internal sealed class DateField {
    abstract val length: Int
    abstract val placeholderRes: Int

    data object Day: DateField() {
        override val length = 2
        override val placeholderRes = R.string.D
    }
    data object Month: DateField() {
        override val length = 2
        override val placeholderRes = R.string.M
    }
    data object Year: DateField() {
        override val length = 4
        override val placeholderRes = R.string.Y
    }
}
