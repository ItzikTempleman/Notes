package com.itzik.notes_.project.ui.screen_sectionsimport android.graphics.Colorimport android.widget.Toastimport androidx.compose.foundation.isSystemInDarkThemeimport androidx.compose.foundation.layout.Columnimport androidx.compose.foundation.layout.fillMaxWidthimport androidx.compose.foundation.layout.paddingimport androidx.compose.foundation.layout.wrapContentWidthimport androidx.compose.foundation.shape.RoundedCornerShapeimport androidx.compose.material.icons.Iconsimport androidx.compose.material.icons.filled.CalendarMonthimport androidx.compose.material3.Buttonimport androidx.compose.material3.ButtonDefaultsimport androidx.compose.material3.DatePickerimport androidx.compose.material3.ExperimentalMaterial3Apiimport androidx.compose.material3.Textimport androidx.compose.material3.rememberDatePickerStateimport androidx.compose.runtime.Composableimport androidx.compose.runtime.getValueimport androidx.compose.runtime.mutableStateOfimport androidx.compose.runtime.rememberimport androidx.compose.runtime.setValueimport androidx.compose.ui.Alignmentimport androidx.compose.ui.Modifierimport androidx.compose.ui.platform.LocalContextimport androidx.compose.ui.res.colorResourceimport androidx.compose.ui.res.stringResourceimport androidx.compose.ui.unit.dpimport androidx.compose.ui.unit.spimport androidx.compose.ui.window.Popupimport java.time.Instantimport java.time.ZoneIdimport java.time.format.DateTimeFormatterimport com.itzik.notes_.Rimport com.itzik.notes_.project.main.NoteAppimport com.itzik.notes_.project.ui.composable_elements.CustomOutlinedTextFieldimport java.time.LocalDateimport java.time.temporal.ChronoUnit@OptIn(ExperimentalMaterial3Api::class)@Composablefun DatePickerDialog(    modifier: Modifier,    onDateSelected: (String) -> Unit,) {    var isDatePickerVisible by remember { mutableStateOf(false) }    val datePickerState = rememberDatePickerState()    val selectedDate = datePickerState.selectedDateMillis?.let {        convertMillisToDate(it)    } ?: ""var isError by remember {    mutableStateOf(false)}    var ageErrorMessage =stringResource(R.string.too_young_error)    var noDateErrorMessage= stringResource(R.string.no_date_selected)    var errorMessage by remember { mutableStateOf("") }    CustomOutlinedTextField(        value = selectedDate,        onValueChange = {},        label = if (!isError) {            stringResource(R.string.date_of_birth)        } else {            errorMessage        },        modifier = modifier.fillMaxWidth(),        readOnly = true,        leftImageVector = Icons.Default.CalendarMonth,        isClickableIcon = true,        invokedFunction = {            isDatePickerVisible = true        },                isError=isError,    )    if (isDatePickerVisible) {        Popup(            onDismissRequest = {                isDatePickerVisible = false                val selectedMillis = datePickerState.selectedDateMillis                if (selectedMillis != null) {                    if (isAtLeast12YearsOld(selectedMillis)) {                        onDateSelected(convertMillisToDate(selectedMillis))                        isError = false                    } else {                        isError = true                        errorMessage = ageErrorMessage                    }                } else {                    isError = true                    errorMessage = noDateErrorMessage                }            }        ) {            Column(                modifier = modifier                    .wrapContentWidth()                    .padding(16.dp),                horizontalAlignment = Alignment.CenterHorizontally            ) {                DatePicker(                    state = datePickerState,                    showModeToggle = false,                )                Button(                    modifier = Modifier.fillMaxWidth(),                    shape = RoundedCornerShape(0.dp),                    colors = ButtonDefaults.buttonColors(colorResource(R.color.deep_purple_2)),                    onClick = {                        val selectedMillis= datePickerState.selectedDateMillis                        if(selectedMillis!=null){                            if (isAtLeast12YearsOld(selectedMillis)) {                                onDateSelected(convertMillisToDate(selectedMillis))                                isDatePickerVisible = false                                isError = false                            }                        }else {                            isDatePickerVisible=false                            isError = true                            return@Button                        }                    }                ) {                    Text(                        text = stringResource(R.string.Continue),                        fontSize = 22.sp,                        color = if(isSystemInDarkTheme()) androidx.compose.ui.graphics.Color.White else androidx.compose.ui.graphics.Color.Black                    )                }            }        }    }}fun convertMillisToDate(rawDate: Long): String {    val localDate = Instant.ofEpochMilli(rawDate).atZone(ZoneId.systemDefault()).toLocalDate()    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")    return localDate.format(formatter)}fun isAtLeast12YearsOld(selectedMillis: Long): Boolean {    val selectedDate = Instant.ofEpochMilli(selectedMillis).atZone(ZoneId.systemDefault()).toLocalDate()    val today = LocalDate.now()    val age = ChronoUnit.YEARS.between(selectedDate, today)    return age >= 12}