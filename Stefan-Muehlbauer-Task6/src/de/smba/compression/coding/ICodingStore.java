package de.smba.compression.coding;

import java.util.Map;

public interface ICodingStore {

	public void addCoding(String identifier, Map<String, String> coding);

	public boolean contains(String identifier);

	public String getCurrent();

	public void setCurrent(String identifier);

	public Map<String, String> getCoding(String identifier);

	public Map<String, String> getAnticoding(String identifier);

	public String getPrintableCoding(String identifier);

}
