package com.itzik.notes.project.ui.composable_elements.dates

enum class Format(val format: String) {
    MMDDYYYY("MM/dd/yyyy"),
    DDMMYYYY("dd/MM/yyyy"),
    YYYYMMDD("yyyy/MM/dd"),
    YYYYDDMM("yyyy/dd/MM")
}