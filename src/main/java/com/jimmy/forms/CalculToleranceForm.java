package com.jimmy.forms;

import java.math.BigDecimal;
import java.sql.Connection;

import com.jimmy.DB.ClasseDeToleranceDaoImpl;
import com.jimmy.DB.ConnexionDBMySql;
import com.jimmy.classes.Ajustement;
import com.jimmy.classes.Alesage;
import com.jimmy.classes.Arbre;
import com.jimmy.classes.ClasseDeTolerance;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class CalculToleranceForm {
	public void calculer(HttpServletRequest request) {

		String dimensionNominaleAlesage;
		String codeClasseDeToleranceAlesageNom;

		String dimensionNominaleArbre;
		String codeClasseDeToleranceArbreNom;

		// On récupère les paramètres, dans la requête, saisis par l'utilisateur

		codeClasseDeToleranceAlesageNom = (String) request.getParameter("codeClasseDeToleranceAlesageNom");
		dimensionNominaleAlesage = (String) request.getParameter("dimensionNominaleAlesageNom");

		codeClasseDeToleranceArbreNom = (String) request.getParameter("codeClasseDeToleranceArbreNom");
		dimensionNominaleArbre = (String) request.getParameter("dimensionNominaleArbreNom");

		// On recherche en DB les tolérances alésage + arbre et on crée (et calcule) les
		// ajustements

		ConnexionDBMySql connexionDBMySql = new ConnexionDBMySql();
		Connection connexion = connexionDBMySql.getConnexion();

		ClasseDeTolerance classeDeTolerance;
		ClasseDeToleranceDaoImpl classeDeToleranceImpl = new ClasseDeToleranceDaoImpl();

		classeDeTolerance = classeDeToleranceImpl.getByCodeClasseDeTolerance(connexion,
				codeClasseDeToleranceAlesageNom);

		Alesage alesage = new Alesage(new BigDecimal(dimensionNominaleAlesage), classeDeTolerance);

		classeDeTolerance = classeDeToleranceImpl.getByCodeClasseDeTolerance(connexion, codeClasseDeToleranceArbreNom);

		Arbre arbre = new Arbre(new BigDecimal(dimensionNominaleArbre), classeDeTolerance);

		connexionDBMySql.closeConnexion(connexion);

		Ajustement ajustement = new Ajustement(alesage, arbre);
		request.setAttribute("ajustement", ajustement);

		HttpSession session = request.getSession();
		session.setAttribute("ajustement", ajustement);

	}
}
