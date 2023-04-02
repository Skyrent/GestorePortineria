package model.implementation;

import java.util.Objects;
import java.util.Optional;

import model.Employee;

public class EmployeeImpl implements Employee {

	private final String name;
	private final String surname;
	private final String username;
	private final String password;
	private Optional<String> lastAccess;
	private boolean loggedIn = false,
					passwordAccess = true;

	public EmployeeImpl(String name, String surname, String username, String password, Optional<String> lastAccess) {
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
		this.lastAccess = lastAccess;
		this.loggedIn = false;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getSurname() {
		return this.surname;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public String getPassword() {
		if(passwordAccess) {
			this.passwordAccess = false;
			return this.password;
		}
		else {
			return null;
		}
	}

	@Override
	public String getLastAccess() {
		return this.lastAccess.orElse(null);
	}

	@Override
	public boolean logged() {
		return this.loggedIn;
	}

	@Override
	public void setLastAccess(String lastAccess) {
		this.lastAccess = Optional.of(lastAccess);
	}

	@Override
	public boolean canLogIn(String username, String password) {
	    if (this.getUsername().equals(username) && this.password.equals(password)) {
	    	this.loggedIn = true;
	    }
	   	return this.loggedIn;
	}

	@Override
	public int hashCode() {
		return Objects.hash(lastAccess, loggedIn, name, password, surname, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		EmployeeImpl other = (EmployeeImpl) obj;
		return Objects.equals(lastAccess, other.lastAccess) && loggedIn == other.loggedIn
				&& Objects.equals(name, other.name) && Objects.equals(password, other.password)
				&& Objects.equals(surname, other.surname) && Objects.equals(username, other.username);
	}



}