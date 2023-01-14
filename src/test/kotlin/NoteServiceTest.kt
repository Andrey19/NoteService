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
    fun editComment_validComment_returnOne() {

        val note = Note(0,1,"r","y",10,0,0,"t")
        noteService.add(note)
        val note2 = Note(0,1,"r","y",10,0,0,"t")
        noteService.add(note2)
        var comment = Comment(1,1,1,1,"ttt",true)
        noteService.createComment(comment)
        assertEquals(1,noteService.editComment(1,"ttt"))
    }

    @Test(expected = CommentNotFoundException::class)
    fun editComment_invalidNoteId_throwCommentNotFoundException() {
        val note = Note(0,11,"r","y",10,0,0,"t")
        noteService.add(note)
        val note2 = Note(0,11,"r","y",10,0,0,"t")
        noteService.add(note2)

        noteService.editComment(3,"ttt")
    }

    @Test(expected = NoteNotFoundException::class)
    fun getById_invalidNoteId_throwNoteNotFoundException() {
        val note = Note(0,11,"r","y",10,0,0,"t")
        noteService.add(note)
        val note2 = Note(0,11,"r","y",10,0,0,"t")
        noteService.add(note2)

        noteService.getById(3)
    }
    @Test
    fun edit_validComment_returnOne() {

        val note = Note(0,1,"r","y",10,0,0,"t")
        noteService.add(note)
        val note2 = Note(0,1,"r","y",10,0,0,"t")
        noteService.add(note2)

        assertEquals(1,noteService.edit(1,"ttt","yyy"))

    }

    @Test(expected = NoteNotFoundException::class)
    fun edit_invalidNoteId_throwNoteNotFoundException() {
        val note = Note(0,11,"r","y",10,0,0,"t")
        noteService.add(note)
        val note2 = Note(0,11,"r","y",10,0,0,"t")
        noteService.add(note2)

        noteService.edit(3,"rrr","RRR")
    }

    @Test
    fun createComment_validComment_returnGuidId() {

        val note = Note(0,1,"r","y",10,0,0,"t")
        noteService.add(note)
        val note2 = Note(0,1,"r","y",10,0,0,"t")
        noteService.add(note2)
        var comment = Comment(1,1,13,1,"t")


        assertEquals(1,noteService.createComment(comment))

    }
    @Test(expected = NoteNotFoundException::class)
    fun createComment_invalidNoteId_throwNoteNotFoundException() {

        var comment = Comment(1,11,13,1,"t")

        noteService.createComment(comment)

    }

    @Test
    fun delete_validNoteId_returnOne() {

        val note = Note(0,11,"r","y",10,0,0,"t")
        noteService.add(note)
        val note2 = Note(0,11,"r","y",10,0,0,"t")
        noteService.add(note2)

        assertEquals(1,noteService.delete(1))

    }

    @Test(expected = NoteNotFoundException::class)
    fun delete_invalidNoteId_throwNoteNotFoundException() {
        val note = Note(0,11,"r","y",10,0,0,"t")
        noteService.add(note)
        val note2 = Note(0,11,"r","y",10,0,0,"t")
        noteService.add(note2)

        noteService.delete(3)
    }
}