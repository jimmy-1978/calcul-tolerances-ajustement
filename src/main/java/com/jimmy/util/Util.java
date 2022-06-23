package com.jimmy.util;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public abstract class Util {

	public static List<String> decoupageChaine(String chaine) {
		List<String> listeString = new ArrayList<String>();
		char[] tabChar = chaine.toCharArray();
		int debut = 0;
		int fin = 0;
		for (int i = 0; i < tabChar.length; i++) {
			if (tabChar[i] == ' ' || tabChar[i] == '\n') {
				listeString.add(chaine.substring(debut, fin));
				debut = i + 1;
				fin = i + 1;
			} else {
				fin++;
			}
		}
		listeString.add(chaine.substring(debut, fin));
		return listeString;
	}

	public static String recherchePropriete(String nomDePropriete) {
		ResourceBundle bundle = ResourceBundle.getBundle("com.jimmy.properties.config"); // L'extension de config doit
																							// être .properties
		return bundle.getString(nomDePropriete);

	}
}
