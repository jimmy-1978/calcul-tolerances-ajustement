package com.jimmy.enumerations;

public enum TypeAjustement {
	jeu("avec jeu"), serrage("avec serrage"), incertain("incertain");

	private String description;

	private TypeAjustement(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
