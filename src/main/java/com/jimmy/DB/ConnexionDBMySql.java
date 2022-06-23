package com.jimmy.DB;

import java.sql.Connection;
import java.sql.DriverManager;

import com.jimmy.util.Util;

public class ConnexionDBMySql implements ConnexionDB {

	@Override
	public Connection getConnexion() {

		Connection connexion;
		try {
			Class.forName("com.mysql.jdbc.Driver"); // Charge le driver dans la VM

			// On recherche les paramètres de connexion à la DB MySQL

			String urlMySql = Util.recherchePropriete("urlMySql");
			String utilisateurMySql = Util.recherchePropriete("utilisateurMySql");
			String motDePasseMySql = Util.recherchePropriete("motDePasseMySql");

			connexion = DriverManager.getConnection(urlMySql, utilisateurMySql, motDePasseMySql);
			System.out.println("Connexion établie sur " + urlMySql + " avec utilisateur " + utilisateurMySql);

			return connexion;

		} catch (Exception e) {
			System.out.println(e);

			return null;

		}
	}

	@Override
	public void closeConnexion(Connection connexion) {
		try {
			connexion.close();
		} catch (Exception e) {
			System.out.println("Erreur fermeture connexion...");
			e.printStackTrace();
		}

	}
}
