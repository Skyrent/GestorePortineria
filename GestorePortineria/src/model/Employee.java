package model;

public interface Employee {

	String getName();

	String getSurname();

	String getUsername();

	String getLastAccess();

	boolean logged();

	void setLastAccess(String lastAccess);

	boolean canLogIn(String username, String password);

	String getPassword();

}
