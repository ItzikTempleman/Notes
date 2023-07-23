import android.annotation.SuppressLint
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.itzik.notes.R
import com.itzik.notes.project.models.Note
import com.itzik.notes.project.navigation.HomeGraph
import com.itzik.notes.project.viewmodels.NoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


val fontSize = mutableIntStateOf(16)

@SuppressLint("AutoboxingStateValueProperty", "UnrememberedMutableState")
@Composable
fun NoteScreen(
    noteArg: Note?,
    navHostController: NavHostController,
    noteViewModel: NoteViewModel,
    coroutineScope: CoroutineScope,
) {
    var newChar by remember { mutableStateOf("") }
    var isEditClicked =mutableStateOf(false)

    @Composable
    fun BackPressHandler(onBackPressed: () -> Unit) {
        val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

        val backCallback = remember {
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressed()
                }
            }
        }

        DisposableEffect(backDispatcher) {
            backDispatcher?.addCallback(backCallback)
            onDispose {
                backCallback.remove()
            }
        }
    }
    BackPressHandler {
        coroutineScope.launch {
            if (newChar.isNotEmpty()) {
                saveNote(newChar, fontSize.value.toString(), noteViewModel)
                navHostController.navigate(HomeGraph.Notes.route)
            }
        }
    }




    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.blue_green))
    ) {
        val (
            edit,
            backBtn,
            backText,
            fontSizeBox,
            contentTextField,
        text
        ) = createRefs()



        if (noteArg != null) {
            Text(
                modifier = Modifier
                    .padding(12.dp)
                    .constrainAs(edit) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .clickable {
                        isEditClicked.value = !isEditClicked.value
                        //navHostController.navigate(HomeGraph.NoteScreen.route)
                    },
                text = if (isEditClicked.value) stringResource(id = R.string.done) else stringResource(
                    id = R.string.edit
                ),
                color = colorResource(id = R.color.strong_yellow),
                fontSize = 14.sp
            )
        }




        Icon(
            tint = colorResource(id = R.color.strong_yellow),
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 8.dp)
                .constrainAs(backBtn) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
                .clickable {
                    coroutineScope.launch {
                        if (newChar.isNotBlank()) {
                            saveNote(newChar, fontSize.value.toString(), noteViewModel)
                            newChar = ""
                        }
                        navHostController.navigate(HomeGraph.Notes.route)
                    }
                },
            contentDescription = stringResource(id = R.string.back),
            painter = painterResource(id = R.drawable.back),
        )


        Text(
            color = colorResource(id = R.color.strong_yellow),
            modifier = Modifier
                .constrainAs(backText) {
                    start.linkTo(backBtn.end)
                    top.linkTo(backBtn.top)
                    bottom.linkTo(backBtn.bottom)
                },
            text = stringResource(id = R.string.notes),
            fontSize = 14.sp
        )



        Row(
            modifier = Modifier
                .height(56.dp)
                .padding(8.dp)
                .constrainAs(fontSizeBox) {
                    top.linkTo(backText.bottom)
                }
        ) {
            Text(
                color = colorResource(id = R.color.white),
                modifier = Modifier
                    .padding(top = 4.dp)
                    .width(30.dp)
                    .clickable {
                        fontSize.value--
                        if (fontSize.value % 2 != 0) fontSize.value--
                        if (fontSize.value < 16) fontSize.value = 16
                    },
                text = stringResource(id = R.string.font_size),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                color = colorResource(id = R.color.white),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .width(30.dp),
                text = fontSize.value.toString(),
                textAlign = TextAlign.Center, fontSize = 14.sp, fontWeight = FontWeight.Bold
            )


            Text(
                color = colorResource(id = R.color.white),
                modifier = Modifier
                    .width(30.dp)
                    .clickable {
                        fontSize.value++
                        if (fontSize.value % 2 != 0) fontSize.value++
                        if (fontSize.value > 42) fontSize.value = 42
                    },
                text = stringResource(id = R.string.font_size),
                textAlign = TextAlign.Center,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

        }

            Text(
                modifier = Modifier
                    .constrainAs(contentTextField) {
                        top.linkTo(fontSizeBox.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                text = noteArg.noteContent
            )

            TextField(
                modifier = Modifier
                    .padding(
                        top = 40.dp,
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .fillMaxSize()
                    .constrainAs(contentTextField) {
                        top.linkTo(fontSizeBox.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                value = newChar,
                onValueChange = {
                    newChar = it
                },
                textStyle = TextStyle.Default.copy(fontSize = fontSize.value.sp),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.content),
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = colorResource(R.color.black),
                    textColor = colorResource(R.color.black),
                    disabledTextColor = colorResource(R.color.white),
                    backgroundColor = colorResource(R.color.white),
                    focusedIndicatorColor = colorResource(R.color.white),
                    unfocusedIndicatorColor = colorResource(R.color.white),
                    disabledIndicatorColor = colorResource(R.color.white),
                    focusedLabelColor = colorResource(R.color.white)
                )
            )
        }
    }


suspend fun saveNote(newChar: String, fontSize: String, noteViewModel: NoteViewModel) {
    val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
    val note = Note(
        noteContent = newChar,
        timeStamp = time,
        fontSize = fontSize.toInt(),
        isInTrashBin = false
    )
    noteViewModel.saveNote(note)
}
