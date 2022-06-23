package com.jimmy.classes;

import com.jimmy.enumerations.TypeClasseDeTolerance;

public class ClasseDeTolerance implements Comparable {
	private int id;
	private String codeClasseDeTolerance; // Ex. : F5, G10, f12, etc.
	private TypeClasseDeTolerance typeClasseDeTolerance;

	public ClasseDeTolerance(int id, String codeClasseDeTolerance) {
		this(codeClasseDeTolerance);
		this.id = id;
	}

	public ClasseDeTolerance(String codeClasseDeTolerance) {

		this.codeClasseDeTolerance = codeClasseDeTolerance;

		String chaine = codeClasseDeTolerance.toUpperCase();
		if (chaine.equals(codeClasseDeTolerance)) {
			typeClasseDeTolerance = TypeClasseDeTolerance.alesage;
		} else {
			typeClasseDeTolerance = TypeClasseDeTolerance.arbre;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodeClasseDeTolerance() {
		return codeClasseDeTolerance;
	}

	public TypeClasseDeTolerance getTypeClasseDeTolerance() {
		return typeClasseDeTolerance;
	}

	@Override
	public String toString() {
		return "ClasseDeTolerance id=" + id + " code=" + codeClasseDeTolerance;
	}

	@Override
	public int compareTo(Object o) {
		ClasseDeTolerance classeDeTolerance = (ClasseDeTolerance) o;
		String chaine1 = "";
		String chaine2 = "";
		Integer entier1 = 0;
		Integer entier2 = 0;
		char[] tabChar = null;

		tabChar = codeClasseDeTolerance.toCharArray();

		for (int i = 0; i < codeClasseDeTolerance.length(); i++) {
			if (tabChar[i] >= '1' && tabChar[i] <= '9') {
				chaine1 = codeClasseDeTolerance.substring(0, i);
				entier1 = Integer.valueOf(codeClasseDeTolerance.substring(i));
				break;
			}
		}

		tabChar = classeDeTolerance.codeClasseDeTolerance.toCharArray();

		for (int i = 0; i < classeDeTolerance.codeClasseDeTolerance.length(); i++) {
			if (tabChar[i] >= '1' && tabChar[i] <= '9') {
				chaine2 = classeDeTolerance.codeClasseDeTolerance.substring(0, i);
				entier2 = Integer.valueOf(classeDeTolerance.codeClasseDeTolerance.substring(i));
				break;
			}
		}
		if (chaine1.compareTo(chaine2) == 0) {
			return entier1.compareTo(entier2);
		} else {
			return chaine1.compareTo(chaine2);
		}
	}
}
