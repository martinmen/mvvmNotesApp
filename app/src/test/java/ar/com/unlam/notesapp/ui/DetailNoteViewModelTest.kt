package ar.com.unlam.notesapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.com.unlam.notesapp.CorutinesTestRule
import ar.com.unlam.notesapp.data.retrofit.repositories.LocationRepository
import ar.com.unlam.notesapp.data.room.repositories.NoteRepository
import ar.com.unlam.notesapp.domain.model.Note
import ar.com.unlam.notesapp.ui.viewModels.AddNoteViewModel
import ar.com.unlam.notesapp.ui.viewModels.DetailNoteViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception
import java.lang.RuntimeException

class DetailNoteViewModelTest {

    lateinit var instance: DetailNoteViewModel

    @MockK
    lateinit var noteRepository: NoteRepository

    @Before
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @get:Rule
    val instanExecutionRule = InstantTaskExecutorRule()

    @get:Rule
    val corutinesTestRule = CorutinesTestRule()

    @ExperimentalCoroutinesApi
    @Test
    fun `eliminar una Nota con exito `() {
        corutinesTestRule.testDispacher.runBlockingTest {
            instance = DetailNoteViewModel(noteRepository)

            coJustRun { noteRepository.deleteNote(any()) }
            var nota = Note(
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

            instance.status.observeForever {
                Assertions.assertThat(it).isEqualTo(DetailNoteViewModel.Status.SUCCES)
            }
            instance.deleteNote(nota)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `error al eliminar una nota`() {
        corutinesTestRule.testDispacher.runBlockingTest {
            instance = DetailNoteViewModel(noteRepository)

            coEvery { noteRepository.deleteNote(any()) } throws Exception(
                "test_exception",
                RuntimeException("test")
            )
            var nota = Note(
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
            instance.status.observeForever {
                Assertions.assertThat(it).isEqualTo(DetailNoteViewModel.Status.ERROR)
            }
            instance.deleteNote(nota)
        }
    }
}