package model;

public interface Key {

	public String getTag();

	public String getLastAccess();

	public String getHolder();

	void setHolder(String holder);

	void setLastAccess(String lastAccess);

}
