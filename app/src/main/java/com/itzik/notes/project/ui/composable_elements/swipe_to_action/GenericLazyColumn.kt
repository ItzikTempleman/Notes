package com.itzik.notes.project.ui.composable_elements.swipe_to_action

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.itzik.notes.project.ui.composable_elements.swipe_to_action.SwipeToDeleteOrRetrieve
import com.itzik.notes.project.ui.composable_elements.swipe_to_action.SwipeToOptions
import com.itzik.notes.project.ui.composable_elements.swipe_to_action.SwipeToUnlike
import com.itzik.notes.project.ui.screen_sections.NoteListItem
import com.itzik.notes.project.viewmodels.NoteViewModel
import com.itzik.notes.project.model.Note

@Composable
fun GenericLazyColumn(
    modifier: Modifier,
    items: List<Note>,
    onPin: ((Note) -> Unit)? = null,
    onStar: ((Note) -> Unit)? = null,
    onDelete: ((Note) -> Unit)? = null,
    onRemoveStar: ((Note) -> Unit)? = null,
    onRetrieve: ((Note) -> Unit)? = null,
    noteViewModel: NoteViewModel,
    onNoteClick: ((Note) -> Unit) ?=null,
) {
    Column(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(items) { note ->
                when {
                    onPin != null && onStar != null && onDelete != null -> {
                        SwipeToOptions(
                            note,
                            onPin = { onPin(note) },
                            onStar = { onStar(note) },
                            onDelete = { onDelete(note) }
                        ) {
                            NoteListItem(
                                noteViewModel = noteViewModel,
                                note = note,
                                modifier = Modifier
                                    .clickable {
                                        if (onNoteClick != null) {
                                            onNoteClick(note)
                                        }
                                    }
                            )
                        }
                    }

                    onRemoveStar != null -> {
                        SwipeToUnlike(note, onRemoveStar = { onRemoveStar(note) }) {
                            NoteListItem(
                                noteViewModel = noteViewModel,
                                note = note,
                                modifier = Modifier,
                            )
                        }
                    }

                    onRetrieve != null && onDelete != null -> {
                        SwipeToDeleteOrRetrieve(
                            note,
                            onRetrieve = { onRetrieve(note) },
                            onDelete = { onDelete(note) }
                        ) {
                            NoteListItem(
                                noteViewModel = noteViewModel,
                                note = note,
                                modifier = Modifier,
                            )
                        }
                    }
                }
            }
        }
    }
}


