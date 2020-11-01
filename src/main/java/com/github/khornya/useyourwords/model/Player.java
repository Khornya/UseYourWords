package com.github.khornya.useyourwords.model;

import com.github.khornya.useyourwords.exception.InvalidPlayerException;

public class Player {

	private String name;
	private String sessionId;

	public Player() {
	}

	public Player(String name, String sessionId) {
		if (name == null || name.isEmpty() || name.isBlank()) {
			throw new InvalidPlayerException();
		};
		this.name = name;
		this.sessionId = sessionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null || name.isEmpty() || name.isBlank()) {
			throw new InvalidPlayerException();
		};
		this.name = name;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", sessionId=" + sessionId + "]";
	}

}
