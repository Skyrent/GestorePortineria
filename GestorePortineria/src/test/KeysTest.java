package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.Key;
import model.KeysManager;
import model.implementation.KeyImpl;
import model.implementation.KeysManagerImpl;

class KeysTest {

    private KeysManager keysManager;
    private Key key1, key2;

    @BeforeEach
    void setUp() {
    	if(DatabaseTools.isPresent())
    		DatabaseTools.delete();
    	DatabaseTools.create();
        keysManager = new KeysManagerImpl();
        key1 = new KeyImpl("tag1", "holder1", Optional.of("21/03 11:15"));
        key2 = new KeyImpl("tag2", "holder2", Optional.empty());
    }

    @Test
    void testKey() {
		assertTrue(key1.getTag().equals("tag1"));
		assertEquals("holder2", key2.getHolder());
		assertEquals(null, key2.getLastAccess());
		assertEquals("21/03 11:15", key1.getLastAccess());		
    }
    
    @Test
    void testAdd() {
        try {
            keysManager.add(key1);
        } catch (SQLException e) {
            fail("Errore in KeysManager in testAdd");
        }
        Set<Key> expected = new HashSet<>(keysManager.getList());
        assertTrue(expected.contains(key1));
        boolean b = false;
        try {
            keysManager.getList().add(key2);
        } catch (UnsupportedOperationException e) {
            b = true;
        }
        assertTrue(b);
    }

    @Test
    void testUpdate() {
        try {
            keysManager.add(key1);
        } catch (SQLException e) {
            fail("Errore in KeysManager in testUpdate");
        }
        Key test = keysManager.getList().stream().filter(k -> k.getTag().equals("tag1")).findFirst().get();
        assertEquals(test, key1);
        String holder = test.getHolder();
        key1.setHolder("holder3");
        key1.setLastAccess("21/03 14:02");
        try {
            keysManager.update(key1, key1.getLastAccess());
        } catch (SQLException e) {
            fail("Errore in KeysManager in testUpdate");
        }
        assertFalse(holder.equals(keysManager.getList()
        									 .stream()
        									 .filter(k -> k.getTag().equals("tag1"))
        									 .map(k -> k.getHolder())
        									 .findFirst()
        									 .get()));
        test = keysManager.getList().stream().filter(k -> k.getTag().equals("tag1")).findFirst().get();
        assertEquals(test.getHolder(), "holder3");
        assertEquals(test.getLastAccess(), "21/03 14:02");
    }

    @Test
    void testRemove() {
        try {
            keysManager.add(key1);
            keysManager.remove("tag1");
        } catch (SQLException e) {
        	  fail("Errore in KeysManager in testRemove");
        }
        Set<Key> expected = new HashSet<>(keysManager.getList());
        assertFalse(expected.contains(key1));
    }
        
}
