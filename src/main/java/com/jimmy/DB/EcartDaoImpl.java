package com.jimmy.DB;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jimmy.classes.Ecart;

public class EcartDaoImpl implements EcartDao {

	@Override
	public Ecart getById(Connection connexion, int id) {

		String stmt = "SELECT ecart_superieur, ecart_inferieur FROM ecart WHERE id = ?";

		try {
			PreparedStatement preparedStatement = connexion.prepareStatement(stmt);
			preparedStatement.setInt(1, id);

			ResultSet result = preparedStatement.executeQuery();
			Ecart ecart = null;
			while (result.next()) {
				ecart = new Ecart(id, result.getBigDecimal("ecart_superieur"), result.getBigDecimal("ecart_inferieur"));
			}

			return ecart;

		} catch (Exception e) {
			e.printStackTrace();

			return null;

		}
	}

	@Override
	public Ecart getByEcartSuperieurAndEcartInferieur(Connection connexion, BigDecimal ecartSuperieur,
			BigDecimal ecartInferieur) {

		String stmt = "SELECT id, ecart_superieur, ecart_inferieur FROM ecart WHERE ecart_superieur = ? AND ecart_inferieur = ?";

		try {
			PreparedStatement preparedStatement = connexion.prepareStatement(stmt);
			preparedStatement.setBigDecimal(1, ecartSuperieur);
			preparedStatement.setBigDecimal(2, ecartInferieur);

			ResultSet result = preparedStatement.executeQuery();
			Ecart ecart = null;
			while (result.next()) {
				ecart = new Ecart(result.getInt("id"), result.getBigDecimal("ecart_superieur"),
						result.getBigDecimal("ecart_inferieur"));
			}

			return ecart;

		} catch (Exception e) {
			e.printStackTrace();

			return null;

		}
	}

	@Override
	public List<Ecart> getAll(Connection connexion) {
		String stmt = "SELECT id, ecart_superieur, ecart_inferieur FROM ecart";
		try {
			Statement statement = connexion.createStatement();
			ResultSet result = statement.executeQuery(stmt);

			List<Ecart> listeEcart = new ArrayList<Ecart>();
			Ecart ecart = null;
			while (result.next()) {
				ecart = new Ecart(result.getInt("id"), result.getBigDecimal("ecart_superieur"),
						result.getBigDecimal("ecart_inferieur"));
				listeEcart.add(ecart);
			}

			return listeEcart;

		} catch (Exception e) {
			e.printStackTrace();

			return null;

		}
	}

	@Override
	public int create(Connection connexion, Ecart ecart) {
		int id = 0;
		String stmt = "SELECT MAX(id) FROM ecart";
		try {
			Statement statement = connexion.createStatement();
			ResultSet result = statement.executeQuery(stmt);
			while (result.next()) {
				id = result.getInt(1) + 1; // Même si null, ça donne bien id 1 au final
			}

			stmt = "INSERT INTO ecart (id, ecart_superieur, ecart_inferieur) VALUES ( ? , ? , ?)";
			PreparedStatement preparedStatement = connexion.prepareStatement(stmt);
			preparedStatement.setInt(1, id);
			preparedStatement.setBigDecimal(2, ecart.getEcartSuperieur());
			preparedStatement.setBigDecimal(3, ecart.getEcartInferieur());
			preparedStatement.execute();

			ecart.setId(id);

			return id;

		} catch (Exception e) {
			e.printStackTrace();

			return 0;

		}
	}

}
