package model.implementation;

import model.Note;


public class NoteImpl implements Note {
	
	private final String note;
	private final String creationDate;
	
	public NoteImpl(String note, String creationDate) {
		this.note = note;
		this.creationDate = creationDate;
	}
	
	@Override
    public String getCreationDate() {
		return creationDate;
	}

	@Override
    public String getNote() {
    	return this.note;
    }

}
