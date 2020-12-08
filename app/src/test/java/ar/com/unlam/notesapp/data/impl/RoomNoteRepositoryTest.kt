package ar.com.unlam.notesapp.data.impl

import ar.com.unlam.notesapp.R
import ar.com.unlam.notesapp.data.exceptions.RepositoryException
import ar.com.unlam.notesapp.data.room.NotesDao
import ar.com.unlam.notesapp.data.room.entities.NoteEntity
import ar.com.unlam.notesapp.data.room.repositories.RoomNoteRepositoryImpl
import ar.com.unlam.notesapp.domain.model.Note
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Before
import org.junit.Test

class RoomNoteRepositoryTest {

    lateinit var instance: RoomNoteRepositoryImpl

    @MockK
    lateinit var notesDao: NotesDao

    @Before
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    @ExperimentalCoroutinesApi
    fun `dado la respuesta de notas de un dao cuando se solicitan todas las notas entonces contesta con las notas del MODEL`() {

        runBlockingTest {
            instance = RoomNoteRepositoryImpl(notesDao)
            coEvery { notesDao.getAll() } returns listOf(
                NoteEntity(
                    1,
                    "test1",
                    "test1Comentario",
                    "",
                    "",
                    null,
                    1607142543835,
                    null,
                    null
                ),
                NoteEntity(
                    2,
                    "test2",
                    "test1Comentario2",
                    "",
                    "",
                    null,
                    1607142543835,
                    4,
                    null
                )
            )

            val result = instance.getMyNotes()
            assertThat(result.size).isEqualTo(2)
            assertThat(result[0].titulo).isEqualTo("test1")
            assertThat(result[1].comentario).isEqualTo("test1Comentario2")
            assertThat(result[1].modifidedTime).isEqualTo(4)
            assertThat(result[1].removeTime).isNull()
        }
    }


    @Test
    @ExperimentalCoroutinesApi
    fun `crear nota`() {

        runBlockingTest {

               var nota = NoteEntity(
                    1,
                    "test1",
                    "test1Comentario",
                    "",
                    "",
                    null,
                    1607142543835,
                    0,
                    null
                )
            assertThat(nota.titulo).isEqualTo("test1")
            assertThat(nota.comentario).isEqualTo("test1Comentario")
            assertThat(nota.modifidedTime).isEqualTo(0)
            assertThat(nota.removeTime).isNull()
        }
    }


    @Test
    @ExperimentalCoroutinesApi
    fun `error al intentar traer todas las notas getMyNotes()`() {

        runBlockingTest {
            instance = RoomNoteRepositoryImpl(notesDao)
            coEvery { notesDao.getAll() } throws RuntimeException("test_exception")

            assertThatThrownBy {
                runBlockingTest {
                    instance.getMyNotes()
                }
            }
                .isInstanceOf(RepositoryException::class.java)
                .hasMessage(R.string.error_getNotes_throw.toString())
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `el dao devuelve un error al ejecutar addNote()`() {
        runBlockingTest {
            instance = RoomNoteRepositoryImpl(notesDao)

            coEvery { notesDao.addNote(any()) } throws RuntimeException("test_exception")
            val note =  Note(
                2, "test2", "test1Comentario2", "",
                "", null, 1607142543835,
                1607142543835, null
            )
            assertThatThrownBy {
                runBlockingTest {
                    instance.addNote(note)
                }
            }
                .isInstanceOf(RepositoryException::class.java)
                .hasMessage(R.string.error_insert_throw.toString())
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `el dao devuelve un error al ejecutar updateNote()`() {
        runBlockingTest {
            instance = RoomNoteRepositoryImpl(notesDao)

            coEvery { notesDao.update(any()) } throws RuntimeException("test_exception")
            val note =  Note(
                2, "test2", "test1Comentario2", "",
                "", null, 1607142543835,
                0, null
            )
            assertThatThrownBy {
                runBlockingTest {
                    instance.updateNote(note)
                }
            }
                .isInstanceOf(RepositoryException::class.java)
                .hasMessage(R.string.error_update_throw.toString())
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `el dao devuelve un error al ejecutar deleteNote()`() {
        runBlockingTest {
            instance = RoomNoteRepositoryImpl(notesDao)

            coEvery { notesDao.deleteNote(any()) } throws RuntimeException("test_exception")
            val note =  Note(
                2, "test2", "test1Comentario2", "",
                "", null, 1607142543835,
                1607142543835, 1607142543835
            )
            assertThatThrownBy {
                runBlockingTest {
                    instance.deleteNote(note)
                }
            }
                .isInstanceOf(RepositoryException::class.java)
                .hasMessage(R.string.error_delete_throw.toString())
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `el dao devuelve un error al ejecutar undoDelete()`() {
        runBlockingTest {
            instance = RoomNoteRepositoryImpl(notesDao)

            coEvery { notesDao.deleteNote(any()) } throws RuntimeException("test_exception")
            val note =  Note(
                2, "test2", "test1Comentario2", "",
                "", null, 1607142543835,
                1607142543835, null
            )
            assertThatThrownBy {
                runBlockingTest {
                    instance.undoDeleteNote(note)
                }
            }
                .isInstanceOf(RepositoryException::class.java)
                .hasMessage(R.string.error_undodelete_throw.toString())
        }
    }
}