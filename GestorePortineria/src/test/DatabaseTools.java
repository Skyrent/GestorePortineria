package test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import model.implementation.CreateDatabase;

public class DatabaseTools {
	
	public static void delete() {
		Path databasePath = new File("database.db").toPath();
		try {
			Files.deleteIfExists(databasePath);
		} catch (Exception e) {
			System.out.println("Errore durante l'eliminazione del database: " + e.getMessage());
		}
	}
	
    public static boolean isPresent() {
        File file = new File("database.db");
        return file.exists();
    }
    
    public static void create() {
    	CreateDatabase.create();
    }
}
