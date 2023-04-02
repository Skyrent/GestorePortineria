package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Note;
import model.NoteManager;
import model.implementation.NoteImpl;
import model.implementation.NoteManagerImpl;

class NoteTest {

    private NoteManager noteManager;
    private Note note1, note2;

    @BeforeEach
    void setUp() {
        if(DatabaseTools.isPresent())
            DatabaseTools.delete();
        DatabaseTools.create();
        noteManager = new NoteManagerImpl();
        note1 = new NoteImpl("Test note 1", "23/03 11:15");
        note2 = new NoteImpl("Test note 2", "23/03 11:20");
    }

    @Test
    void testGetList() {
        assertTrue(noteManager.getList().isEmpty());
        try {
            noteManager.add(note1);
        } catch (SQLException e) {
            fail("Error adding notes to database");
        }
        assertFalse(noteManager.getList().isEmpty());
        assertEquals(1, noteManager.getList().size());
    }

    @AfterEach
    void testAdd() {
        try {
            noteManager.add(note1);
            noteManager.add(note2);
        } catch (SQLException e) {
            fail("Error adding note to database");
        }
        assertTrue(noteManager.getList().contains(note1));
        Set<Note> expected = new HashSet<>(noteManager.getList());
        assertTrue(expected.contains(note2));
    }

}
