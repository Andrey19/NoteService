import kotlin.streams.toList

fun main(args: Array<String>) {
    val note = Note(0,11,"r","y",10,0,0,"t")
    val notes = Notes()
    var number = notes.add(note)
    println(number)
    val note2 = Note(0,11,"r","y",10,0,0,"t")
    number = notes.add(note2)
    println(number)
    val note3 = Note(0,11,"r","y",10,0,0,"t")
    notes.add(note3)
    println("+++++++++++++++++++++++++++++++")
    var checkget = notes.get(11,3)
    checkget.stream().forEach {  nextNote : Note -> println(nextNote)}
    println("+++++++++++++++++++++++++++++++")
    var comment = Comment(0,1,13,1,"t")
    var num =  notes.createComment(comment)
    println(num)
    println(note)
    var one = notes.delete(2)
    println(one)


    try {
        notes.delete(2)
    } catch (e: NoteNotFoundException) {
        println("catch mistake : " + e.message)
    }
}


class Comment(
    var guid: Int,
    var noteId: Int,
    var ownerId: Int,
    var replyTo: Int,
    var message: String,
    var isAvailable: Boolean = true
){}

class Note(
    var id: Int,
    var ownerId: Int,
    var title: String,
    var text: String,
    var date: Int,
    var comments: Int,
    var readComments: Int,
    var viewURL: String,
){
    override fun toString(): String {
        return "Note(id=$id, ownerId=$ownerId, title='$title', text='$text', date=$date, comments=$comments, readComments=$readComments, viewURL='$viewURL')"
    }
}
class NoteNotFoundException(message: String) : RuntimeException(message)
class CommentNotFoundException(message: String) : RuntimeException(message)
class Notes() {
    var comments = mutableListOf<Comment>()
    var notes = mutableListOf<Note>()
    var nextNotesId: Int = 1
    var nextCommentsId: Int = 1

    fun clear() {
        comments = emptyList<Comment>().toMutableList()
        notes = emptyList<Note>().toMutableList()
        nextNotesId = 1
        nextCommentsId = 1
    }

    fun restoreComment(commentId: Int): Int{
        var comment = comments.find { nextComment : Comment -> ( commentId == nextComment.guid && nextComment.isAvailable ) }
        if (comment == null){
            throw CommentNotFoundException("Comment  $commentId doesn't exist ")
        }
        comment.isAvailable = true
        return 1
    }

    fun getComments(noteId: Int,
                    count: Long
                    ) : List<Comment>{
        return comments.stream().filter { nextComment : Comment -> (nextComment.noteId == noteId && nextComment.isAvailable ) }.limit(count).toList()
    }

    fun getById(noteId: Int,
                ): Note{
        var findNoteById = notes.find { nextNote : Note -> (noteId == nextNote.id) }
        if (findNoteById == null){
            throw NoteNotFoundException("Note $noteId doesn't exist ")
        }
        return findNoteById
    }

    fun get(userId: Int,
            count: Long
            ): List<Note>{
        return notes.stream().filter{nextNote : Note -> (userId == nextNote.ownerId)}.limit(count).toList()
    }

    fun editComment(commentId: Int,
                    message: String
    ) : Int{
        var findComment = comments.find { nextComment : Comment -> (commentId == nextComment.guid) }
        if (findComment == null){
            throw CommentNotFoundException("Comment  $commentId doesn't exist ")
        }
        findComment.message = message
        return 1
    }

    fun edit(noteId: Int,title: String,text: String): Int{
        var findNote = notes.find { nextNote : Note -> (nextNote.id == noteId) }
        if (findNote == null){
            throw NoteNotFoundException("Note $noteId doesn't exist ")
        }
        findNote.title = title
        findNote.text = text
        return 1
    }

    fun deleteComment(commentId: Int): Int{
       var comment = comments.find { nextComment : Comment -> ( commentId == nextComment.guid && nextComment.isAvailable ) }
        if (comment == null){
            throw CommentNotFoundException("Comment  $commentId doesn't exist ")
        }
        comment.isAvailable = false
        return 1
    }

    fun delete(noteId: Int): Int {
        var note = notes.find { nextNoteD : Note -> (nextNoteD.id == noteId) }
        if (note == null){
            throw NoteNotFoundException("Note $noteId doesn't exist ")
        }
        notes.remove(note)
        comments.removeAll{nextComment : Comment -> (nextComment.noteId == noteId)}
        return 1
    }
    fun add(
     note: Note
    ): Int {
        note.id = nextNotesId++
        notes.add(note)
        return note.id
    }
    fun createComment(comment: Comment): Int{
        var note = notes.find{nextNote: Note -> (nextNote.id == comment.noteId)}
        if (note == null){
            throw NoteNotFoundException("Note ${comment.noteId} doesn't exist ")
        }
        note.comments++
        comment.guid = nextCommentsId++
        comments.add(comment)
        return comment.guid
    }
}

