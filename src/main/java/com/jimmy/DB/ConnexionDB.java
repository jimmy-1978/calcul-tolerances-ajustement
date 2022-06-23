package com.jimmy.DB;

import java.sql.Connection;

public interface ConnexionDB {

	public Connection getConnexion();

	public void closeConnexion(Connection connexion);
}
