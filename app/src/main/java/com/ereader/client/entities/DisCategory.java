package com.ereader.client.entities;

public class DisCategory {
	/*"name": "3-5元区",
            "min": 3,
            "max": 5
*/
	private String min;
	private String max;
	private String name;
	
	/**
	 * @return the min
	 */
	public String getMin() {
		return min;
	}
	/**
	 * @param min the min to set
	 */
	public void setMin(String min) {
		this.min = min;
	}
	/**
	 * @return the max
	 */
	public String getMax() {
		return max;
	}
	/**
	 * @param max the max to set
	 */
	public void setMax(String max) {
		this.max = max;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
