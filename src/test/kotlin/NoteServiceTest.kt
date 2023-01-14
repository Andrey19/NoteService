import junit.framework.TestCase.assertEquals
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class NoteServiceTest {

    var noteService = Notes()

    @Before
    fun clearBeforeTest() {
        noteService.clear()
    }

    @Test
    fun delete_validNoteId_returnOne() {

        val note = Note(0,11,"r","y",10,0,0,"t")
        noteService.add(note)
        val note2 = Note(0,11,"r","y",10,0,0,"t")
        noteService.add(note2)

        // здесь код с вызовом функции, которая должна выкинуть PostNotFoundException
        assertEquals(1,noteService.delete(1))

    }

    @Test(expected = NoteNotFoundException::class)
    fun delete_invalidNoteId_throwNoteNotFoundException() {

        val note = Note(0,11,"r","y",10,0,0,"t")
        noteService.add(note)
        val note2 = Note(0,11,"r","y",10,0,0,"t")
        noteService.add(note2)

        // здесь код с вызовом функции, которая должна выкинуть PostNotFoundException
        noteService.delete(3)

    }
}