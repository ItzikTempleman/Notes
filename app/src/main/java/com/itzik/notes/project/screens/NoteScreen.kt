import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
@SuppressLint("AutoboxingStateValueProperty")
@Composable
fun NoteScreen(
    navHostController: NavHostController,
    noteViewModel: NoteViewModel,
    coroutineScope: CoroutineScope,
) {
    var newChar by remember { mutableStateOf("") }


    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (
            backBtn,
            backText,
            fontSizeBox,
            contentTextField,
        ) = createRefs()




        Icon(
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 8.dp)
                .constrainAs(backBtn) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
                .clickable {
                    coroutineScope.launch {
                        if (newChar.isNotBlank()) {
                            saveNote(newChar, fontSize.value.toString() , noteViewModel)
                            newChar = ""
                        }
                        navHostController.navigate(HomeGraph.Notes.route)
                    }
                },
            contentDescription = stringResource(id = R.string.back),
            painter = painterResource(id = R.drawable.back),
        )


        Text(
            modifier = Modifier
                .padding( horizontal = 2.dp)
                .constrainAs(backText) {
                    start.linkTo(backBtn.end)
                    top.linkTo(backBtn.top)
                    bottom.linkTo(backBtn.bottom)
                },
            text = stringResource(id = R.string.notes),
            fontSize = 20.sp
        )


        Card(
            shape = RoundedCornerShape(6.dp), elevation = 4.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(8.dp)
                .constrainAs(fontSizeBox) {
                    top.linkTo(backText.bottom)
                }
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
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
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(30.dp),
                    text = fontSize.value.toString(),
                    textAlign = TextAlign.Center, fontSize = 14.sp, fontWeight = FontWeight.Bold
                )


                Text(
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
        }


        TextField(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .constrainAs(contentTextField) {
                    top.linkTo(fontSizeBox.bottom)
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
                cursorColor = colorResource(R.color.yellow),
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
