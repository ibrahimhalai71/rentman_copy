package com.rentmen.app.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "moderator")
public class Moderator extends User {
	private String accessLevel;

	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}

}
