package model.implementation;

import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(creationDate, note);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		NoteImpl other = (NoteImpl) obj;
		return Objects.equals(creationDate, other.creationDate) && Objects.equals(note, other.note);
	}

}
