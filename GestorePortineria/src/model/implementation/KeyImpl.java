package model.implementation;

import java.util.Objects;
import java.util.Optional;

import model.Key;

public class KeyImpl implements Key {

	private final String tag;
	private Optional<String> lastAccess;
	private String holder;

	public KeyImpl (String tag, String holder, Optional<String> lastAccess ) {
		this.tag = tag;
		this.holder = holder;
		this.lastAccess = lastAccess;
	}
	
	@Override
	public String getTag() {
		return this.tag;
	}
	
	@Override
	public String getHolder() {
		return this.holder;
	}
	
	@Override
	public String getLastAccess() {
		return this.lastAccess.orElse(null);
	}

	@Override
	public void setHolder(String holder){
		this.holder = holder;
	}
	
	@Override
	public void setLastAccess(String lastAccess) {
		this.lastAccess = Optional.of(lastAccess);
	}

	@Override
	public int hashCode() {
		return Objects.hash(holder, lastAccess, tag);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KeyImpl other = (KeyImpl) obj;
		return Objects.equals(holder, other.holder) && Objects.equals(lastAccess, other.lastAccess)
				&& Objects.equals(tag, other.tag);
	}

}
