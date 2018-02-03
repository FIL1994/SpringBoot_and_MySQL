package hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import hello.model.Note;
import hello.repository.NoteRepository;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "note")
public class NoteController {
    @Autowired
    private NoteRepository noteRepository;

    @PostMapping("")
    public Note createNote(@Valid @RequestBody Note note) {
        return noteRepository.save(note);
    }

    @GetMapping(path = "/add")
    public @ResponseBody
    String addNewNote(@RequestParam String message) {

        Note n = new Note();
        n.setMessage(message);
        noteRepository.save(n);
        return "Saved";
    }

    // allow get on /note and /note/all
    @GetMapping(path = {"", "/all"})
    public @ResponseBody
    Iterable<Note> getAllNotes() {
        // This returns a JSON or XML with the users
        return noteRepository.findAll();
    }

    // get a single note
    @GetMapping("{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable(value = "id") Long noteId) {
        Note note =  noteRepository.findOne(noteId);
        if(note == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(note);
    }

    // post a json object with Content-Type = application/json in header
    @PutMapping("{id}")
    public ResponseEntity<Note> updateNote(@PathVariable(value = "id") Long noteId,
                                           @Valid @RequestBody Note noteDetails) {
        Note note  = noteRepository.findOne(noteId);
        if(note == null) {
            return ResponseEntity.notFound().build();
        }

        note.setMessage(noteDetails.getMessage());
        Note updatedNote = noteRepository.save(note);
        return ResponseEntity.ok(updatedNote);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Note> deleteNote(@PathVariable(value = "id") Long noteId) {
        Note note = noteRepository.findOne(noteId);
        if(note == null) {
            return ResponseEntity.notFound().build();
        }

        noteRepository.delete(note);
        return ResponseEntity.ok().build();
    }
}
