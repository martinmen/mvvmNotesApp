package ar.com.unlam.notesapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ar.com.unlam.notesapp.CorutinesTestRule
import ar.com.unlam.notesapp.R
import ar.com.unlam.notesapp.data.exceptions.RepositoryException
import ar.com.unlam.notesapp.data.retrofit.repositories.LocationRepository
import ar.com.unlam.notesapp.data.room.entities.NoteEntity
import ar.com.unlam.notesapp.data.room.repositories.NoteRepository
import ar.com.unlam.notesapp.domain.model.Location
import ar.com.unlam.notesapp.domain.model.Note
import ar.com.unlam.notesapp.ui.exception.UiRepositoryException
import ar.com.unlam.notesapp.ui.viewModels.AddNoteViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception
import java.lang.RuntimeException

class AddNoteViewModelTest {

    lateinit var instance: AddNoteViewModel

    @MockK
    lateinit var noteRepository: NoteRepository

    @MockK
    lateinit var locationRepository: LocationRepository

    @Before
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @get:Rule
    val instanExecutionRule = InstantTaskExecutorRule()

    @get:Rule
    val corutinesTestRule = CorutinesTestRule()

    @ExperimentalCoroutinesApi
    @Test
    fun `que una nota se cree correctamente`() {
        corutinesTestRule.testDispacher.runBlockingTest {
            instance = AddNoteViewModel(noteRepository, locationRepository)

            coJustRun { noteRepository.addNote(any()) }
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
                assertThat(it).isEqualTo(AddNoteViewModel.Status.SUCCES)
            }
            instance.addNote(nota)
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `al validar nota sea valido`() {
        corutinesTestRule.testDispacher.runBlockingTest {
            instance = AddNoteViewModel(noteRepository, locationRepository)

            coEvery { noteRepository.addNote(any()) }
            var nota = Note(
                1,
                "test",
                "test1Comentario",
                "",
                "",
                null,
                1607142543835,
                0,
                null
            )
            instance.status.observeForever {
                assertThat(it).isEqualTo(AddNoteViewModel.Status.VALID)
            }
            instance.verifyRequeried(nota)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `al validar que el campo title no este completo el status sea NOT_VALID`() {
        corutinesTestRule.testDispacher.runBlockingTest {
            instance = AddNoteViewModel(noteRepository, locationRepository)

            coEvery { noteRepository.addNote(any()) }
            var nota = Note(
                1,
                "",
                "test1Comentario",
                "",
                "",
                null,
                1607142543835,
                0,
                null
            )
            instance.status.observeForever {
                assertThat(it).isEqualTo(AddNoteViewModel.Status.NOT_VALID)
            }
            instance.verifyRequeried(nota)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `error al agregar una nota`() {
        corutinesTestRule.testDispacher.runBlockingTest {
            instance = AddNoteViewModel(noteRepository, locationRepository)

            coEvery { noteRepository.addNote(any()) } throws Exception(
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
                assertThat(it).isEqualTo(AddNoteViewModel.Status.ERROR)
            }
            instance.addNote(nota)

        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `que al actualizar un nota con exito updateNote()`() {
        corutinesTestRule.testDispacher.runBlockingTest {
            instance = AddNoteViewModel(noteRepository, locationRepository)

            coJustRun { noteRepository.updateNote(any()) }
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
                assertThat(it).isEqualTo(AddNoteViewModel.Status.SUCCES)
            }
            instance.updateNote(nota)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `error al actualizar una nota`() {
        corutinesTestRule.testDispacher.runBlockingTest {
            instance = AddNoteViewModel(noteRepository, locationRepository)

            coEvery { noteRepository.updateNote(any()) } throws Exception(
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
                assertThat(it).isEqualTo(AddNoteViewModel.Status.ERROR)
            }
            instance.updateNote(nota)

        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `obtener nota por Id con exito getNoteById() y verificar campos`() {
        corutinesTestRule.testDispacher.runBlockingTest {
            instance = AddNoteViewModel(noteRepository, locationRepository)
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
            coEvery { noteRepository.getNoteById(nota.id) } returns nota
            instance.noteLiveData.value = nota

            instance.status.observeForever {

                assertThat(it).isEqualTo(AddNoteViewModel.Status.SUCCES)
            }
            instance.getNoteById(nota.id)
            assertThat(instance.noteLiveData.value?.id).isEqualTo(1)
            assertThat(instance.noteLiveData.value!!.comentario).isEqualTo(nota.comentario)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `error al intentar obtener una nota por id`() {
        corutinesTestRule.testDispacher.runBlockingTest {
            instance = AddNoteViewModel(noteRepository, locationRepository)
            coEvery { noteRepository.getNoteById(any()) } throws Exception(
                "test_exception",
                RuntimeException("test")
            )
            instance.status.observeForever {
                assertThat(it).isEqualTo(AddNoteViewModel.Status.ERROR)
            }
            instance.getNoteById(1)
        }
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `en cheackAddOrUpdate() validar que al NO tener id en la nota se haga un insert AddNote()`() {
        corutinesTestRule.testDispacher.runBlockingTest {
            instance = AddNoteViewModel(noteRepository, locationRepository)

            var nota = Note(
                0,
                "test",
                "test1Comentario",
                "",
                "",
                null,
                1607142543835,
                0,
                null
            )
            instance.routingActivity.observeForever {
                assertThat(it).isEqualTo(AddNoteViewModel.Status.TO_ADD_NOTE)
            }
            instance.checkAddOrUpdate(null, nota)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `en cheackAddOrUpdate() validar que al tener id en la nota se haga un update UpdateNote()`() {
        corutinesTestRule.testDispacher.runBlockingTest {
            instance = AddNoteViewModel(noteRepository, locationRepository)

            var nota = Note(
                1,
                "test",
                "test1Comentario",
                "",
                "",
                null,
                1607142543835,
                0,
                null
            )
            instance.routingActivity.observeForever {
                assertThat(it).isEqualTo(AddNoteViewModel.Status.TO_UPDATE_NOTE)
            }
            instance.checkAddOrUpdate(1, nota)
        }
    }

}