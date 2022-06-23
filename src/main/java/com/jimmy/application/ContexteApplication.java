package com.jimmy.application;

import java.util.Collections;
import java.util.List;

import com.jimmy.DB.ListeTolerance;
import com.jimmy.classes.ClasseDeTolerance;
import com.jimmy.enumerations.TypeClasseDeTolerance;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;

public abstract class ContexteApplication {

	public static void charger(HttpServlet httpServlet) {

		// On stocke les deux listes (alésage + arbre) des classes de tolérances dans le
		// contexte application

		List<ClasseDeTolerance> listeClasseDeToleranceAlesage = ListeTolerance
				.getListeClasseDeTolerance(TypeClasseDeTolerance.alesage);
		List<ClasseDeTolerance> listeClasseDeToleranceArbre = ListeTolerance
				.getListeClasseDeTolerance(TypeClasseDeTolerance.arbre);

		Collections.sort(listeClasseDeToleranceAlesage); // Utilise compareTo(Object o)
		Collections.sort(listeClasseDeToleranceArbre);

		ServletContext contexteApplication = httpServlet.getServletContext();

		contexteApplication.setAttribute("listeClasseDeToleranceAlesage", listeClasseDeToleranceAlesage);
		contexteApplication.setAttribute("listeClasseDeToleranceArbre", listeClasseDeToleranceArbre);

	}

}
