package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.EmployeesManager;
import model.KeysManager;
import model.NoteManager;
import model.implementation.EmployeeImpl;
import model.implementation.EmployeesManagerImpl;
import model.implementation.KeysManagerImpl;
import model.implementation.NoteManagerImpl;

public class DatabaseTest {

    @BeforeEach
    void setUp() {
    	if(DatabaseTools.isPresent())
    		DatabaseTools.delete();
    }

    // Creazione e controllo del database, esso appena creato deve contenere un account admin
    @Test
    void testCreateDatabase() {
    	assertFalse(DatabaseTools.isPresent());
    	DatabaseTools.create();
    	assertTrue(DatabaseTools.isPresent());
    	EmployeesManager test = new EmployeesManagerImpl();
    	assertEquals(test.getList().size(), 1);
    	assertEquals(test.getList().stream().findFirst().get(), new EmployeeImpl("admin", "admin", "admin", "admin", Optional.empty()));
    }

    //Creazione e controllo del database, esso non deve contenere alcuna chiave o nota alla creazione
    @Test
    void testFeatures() {
    	assertFalse(DatabaseTools.isPresent());
    	DatabaseTools.create();
    	assertTrue(DatabaseTools.isPresent());
    	KeysManager keyTest = new KeysManagerImpl();
    	NoteManager noteTest = new NoteManagerImpl();
    	assertTrue(keyTest.getList().isEmpty());
    	assertTrue(noteTest.getList().isEmpty());
    }

}
