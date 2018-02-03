package hello.repository;

import org.springframework.data.repository.CrudRepository;

import hello.model.Note;


public interface NoteRepository extends CrudRepository<Note, Long> {

}
