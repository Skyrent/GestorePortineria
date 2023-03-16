package model;

import java.sql.SQLException;

public interface Key {

	public String getTag();
	
	public String getLastAccess();

	public String getHolder();
		
	void setHolder(String holder) throws SQLException;

	void setLastAccess(String lastAccess);

}
