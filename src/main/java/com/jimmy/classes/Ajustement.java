package com.jimmy.classes;

import com.jimmy.enumerations.TypeAjustement;

public class Ajustement {
	private Alesage alesage;
	private Arbre arbre;
	private TypeAjustement typeAjustement;

	public Ajustement(Alesage alesage, Arbre arbre) {
		this.alesage = alesage;
		this.arbre = arbre;
		if (alesage.getDimensionMinimum().compareTo(arbre.getDimensionMaximum()) > 0) {
			typeAjustement = TypeAjustement.jeu;

		} else if (arbre.getDimensionMinimum().compareTo(alesage.getDimensionMaximum()) > 0) {
			typeAjustement = typeAjustement.serrage;
		} else {
			typeAjustement = TypeAjustement.incertain;
		}
	}

	public Alesage getAlesage() {
		return alesage;
	}

	public Arbre getArbre() {
		return arbre;
	}

	public TypeAjustement getTypeAjustement() {
		return typeAjustement;
	}
}
