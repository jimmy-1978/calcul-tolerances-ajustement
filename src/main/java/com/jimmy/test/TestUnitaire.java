package com.jimmy.test;

import java.math.BigDecimal;
import java.sql.Connection;

import com.jimmy.DB.ClasseDeToleranceDaoImpl;
import com.jimmy.DB.ConnexionDBMySql;
import com.jimmy.DB.ToleranceDaoImpl;
import com.jimmy.classes.ClasseDeTolerance;
import com.jimmy.classes.Tolerance;

public abstract class TestUnitaire {
	public static void executer() {
		ConnexionDBMySql connexionDBMysql = new ConnexionDBMySql();
		Connection connexion = connexionDBMysql.getConnexion();

		int id;

		// Tests Intervalle

//		System.out.println("*****  Tests Intervalle *****");
//
//		IntervalleDaoImpl intervalleDaoImpl = new IntervalleDaoImpl();
//
//		Intervalle intervalle = new Intervalle(0, 3);
//		id = intervalleDaoImpl.create(connexion, intervalle);
//
//		System.out.println("Intervalle crée.  Id retour = " + id + " id de l'objet = " + intervalle.getId());
//
//		intervalle = intervalleDaoImpl.getById(connexion, id);
//		System.out.println(
//				"GetById : " + intervalle.getId() + "/" + intervalle.getAuDela() + "-" + intervalle.getJusque());
//
//		intervalle = intervalleDaoImpl.getByAuDelaAndJusque(connexion, 0, 3);
//		System.out.println("getByAuDelaAndJusque : " + intervalle.getId() + "/" + intervalle.getAuDela() + "-"
//				+ intervalle.getJusque());
//
//		intervalle = new Intervalle(3, 6);
//		id = intervalleDaoImpl.create(connexion, intervalle);
//
//		intervalle = new Intervalle(6, 10);
//		id = intervalleDaoImpl.create(connexion, intervalle);
//
//		intervalle = new Intervalle(10, 15);
//		id = intervalleDaoImpl.create(connexion, intervalle);
//
//		for (Intervalle intervalleBoucle : intervalleDaoImpl.getAll(connexion)) {
//			System.out.println(
//					intervalleBoucle.getId() + "/" + intervalleBoucle.getAuDela() + "-" + intervalleBoucle.getJusque());
//		}
//
//		// Tests Ecart
//
//		System.out.println("*****  Tests Ecart *****");
//
//		EcartDaoImpl ecartDaoImpl = new EcartDaoImpl();
//
//		Ecart ecart = new Ecart(BigDecimal.valueOf(295), BigDecimal.valueOf(270));
//		id = ecartDaoImpl.create(connexion, ecart);
//
//		System.out.println("Ecart crée.  Id retour = " + id + " id de l'objet = " + ecart.getId());
//
//		ecart = ecartDaoImpl.getById(connexion, id);
//		System.out.println(
//				"GetById : " + ecart.getId() + "/" + ecart.getEcartSuperieur() + "-" + ecart.getEcartInferieur());
//
//		ecart = ecartDaoImpl.getByEcartSuperieurAndEcartInferieur(connexion, BigDecimal.valueOf(295),
//				BigDecimal.valueOf(270));
//		System.out.println("getByEcartSuperieurAndEcartInferieur : " + ecart.getId() + "/" + ecart.getEcartSuperieur()
//				+ "-" + ecart.getEcartInferieur());
//
//		ecart = new Ecart(BigDecimal.valueOf(295), BigDecimal.valueOf(270));
//		id = ecartDaoImpl.create(connexion, ecart);
//
//		ecart = new Ecart(BigDecimal.valueOf(20), BigDecimal.valueOf(10));
//		id = ecartDaoImpl.create(connexion, ecart);
//
//		ecart = new Ecart(BigDecimal.valueOf(10), BigDecimal.valueOf(-10));
//		id = ecartDaoImpl.create(connexion, ecart);
//
//		for (Ecart ecartBoucle : ecartDaoImpl.getAll(connexion)) {
//			System.out.println(ecartBoucle.getId() + "/" + ecartBoucle.getEcartSuperieur() + "-"
//					+ ecartBoucle.getEcartInferieur());
//		}
//
//		// Tests ClasseDeTolerance
//
//		System.out.println("*****  Tests ClasseDeTolerance *****");
//
//		ClasseDeToleranceDaoImpl classeDeToleranceDaoImpl = new ClasseDeToleranceDaoImpl();
//
//		ClasseDeTolerance classeDeTolerance = new ClasseDeTolerance("A9");
//		id = classeDeToleranceDaoImpl.create(connexion, classeDeTolerance);
//
//		System.out.println(
//				"ClasseDeTolerance crée.  Id retour = " + id + " id de l'objet = " + classeDeTolerance.getId());
//
//		classeDeTolerance = classeDeToleranceDaoImpl.getById(connexion, id);
//		System.out
//				.println("GetById : " + classeDeTolerance.getId() + "/" + classeDeTolerance.getCodeClasseDeTolerance());
//
//		classeDeTolerance = classeDeToleranceDaoImpl.getByCodeClasseDeTolerance(connexion, "A9");
//		System.out.println("getByCodeClasseDeTolerance A9 : " + classeDeTolerance.getId() + "/"
//				+ classeDeTolerance.getCodeClasseDeTolerance());
//
//		classeDeTolerance = new ClasseDeTolerance("A10");
//		id = classeDeToleranceDaoImpl.create(connexion, classeDeTolerance);
//
//		classeDeTolerance = new ClasseDeTolerance("a9");
//		id = classeDeToleranceDaoImpl.create(connexion, classeDeTolerance);
//
//		classeDeTolerance = new ClasseDeTolerance("a10");
//		id = classeDeToleranceDaoImpl.create(connexion, classeDeTolerance);
//
//		for (ClasseDeTolerance classeDeToleranceBoucle : classeDeToleranceDaoImpl.getAll(connexion)) {
//			System.out.println(
//					classeDeToleranceBoucle.getId() + "/" + classeDeToleranceBoucle.getCodeClasseDeTolerance());
//		}
//
//		classeDeTolerance = classeDeToleranceDaoImpl.getByCodeClasseDeTolerance(connexion, "a9");
//		System.out.println("getByCodeClasseDeTolerance a9 : " + classeDeTolerance.getId() + "/"
//				+ classeDeTolerance.getCodeClasseDeTolerance());
//
//		// Tests Tolerance
//
//		System.out.println("*****  Tests Tolerance *****");
//
//		ToleranceDaoImpl toleranceDaoImpl = new ToleranceDaoImpl();
//		Tolerance tolerance = new Tolerance(classeDeTolerance, intervalle, ecart);
//		id = toleranceDaoImpl.create(connexion, tolerance);
//		System.out.println(tolerance.toString());
//
//		tolerance = toleranceDaoImpl.getById(connexion, id);
//		System.out.println(tolerance.toString());
//
//		tolerance = new Tolerance(new ClasseDeTolerance("A10"), new Intervalle(0, 3),
//				new Ecart(BigDecimal.valueOf(290), BigDecimal.valueOf(250)));
//		toleranceDaoImpl.create(connexion, tolerance);
//
//		tolerance = new Tolerance(new ClasseDeTolerance("B10"), new Intervalle(10, 20),
//				new Ecart(BigDecimal.valueOf(-10), BigDecimal.valueOf(-25)));
//		toleranceDaoImpl.create(connexion, tolerance);
//
//		tolerance = new Tolerance(new ClasseDeTolerance("C5"), new Intervalle(11, 30),
//				new Ecart(BigDecimal.valueOf(10), BigDecimal.valueOf(-1)));
//		toleranceDaoImpl.create(connexion, tolerance);
//
//		for (Tolerance toleranceBoucle : toleranceDaoImpl.getAll(connexion)) {
//			System.out.println(toleranceBoucle.toString());
//		}
		ClasseDeToleranceDaoImpl classeDeToleranceDaoImpl = new ClasseDeToleranceDaoImpl();
		ClasseDeTolerance classeDeTolerance = classeDeToleranceDaoImpl.getByCodeClasseDeTolerance(connexion, "a9");
		ToleranceDaoImpl toleranceDaoImpl = new ToleranceDaoImpl();

		Tolerance tolerance = toleranceDaoImpl.getByIdClasseDeToleranceAndDimension(connexion,
				classeDeTolerance.getId(), BigDecimal.valueOf(3));
		System.out.println("recherche via getByIdClasseDeToleranceAndDimension = " + tolerance.toString());

	}
}
