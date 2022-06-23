package com.jimmy.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jimmy.classes.Intervalle;

public class IntervalleDaoImpl implements IntervalleDao {

	@Override
	public Intervalle getById(Connection connexion, int id) {

		String stmt = "SELECT audela, jusque FROM intervalle WHERE id = ?";

		try {
			PreparedStatement preparedStatement = connexion.prepareStatement(stmt);
			preparedStatement.setInt(1, id);
			ResultSet result = preparedStatement.executeQuery();
			Intervalle intervalle = null;
			while (result.next()) {
				intervalle = new Intervalle(id, result.getInt("audela"), result.getInt("jusque"));
			}

			return intervalle;

		} catch (Exception e) {
			e.printStackTrace();

			return null;

		}
	}

	@Override
	public Intervalle getByAuDelaAndJusque(Connection connexion, int auDela, int jusque) {
		String stmt = "SELECT id, audela, jusque FROM intervalle WHERE audela = ? AND jusque = ?";

		try {
			PreparedStatement preparedStatement = connexion.prepareStatement(stmt);
			preparedStatement.setInt(1, auDela);
			preparedStatement.setInt(2, jusque);
			ResultSet result = preparedStatement.executeQuery();
			Intervalle intervalle = null;
			while (result.next()) {
				intervalle = new Intervalle(result.getInt("id"), result.getInt("audela"), result.getInt("jusque"));
			}

			return intervalle;

		} catch (Exception e) {
			e.printStackTrace();

			return null;

		}
	}

	@Override
	public List<Intervalle> getAll(Connection connexion) {

		String stmt = "SELECT id, audela, jusque FROM intervalle";

		try {

			Statement statement = connexion.createStatement();
			ResultSet result = statement.executeQuery(stmt);

			List<Intervalle> listeIntervalle = new ArrayList<Intervalle>();
			Intervalle intervalle;

			while (result.next()) {
				intervalle = new Intervalle(result.getInt("id"), result.getInt("audela"), result.getInt("jusque"));
				listeIntervalle.add(intervalle);
			}

			return listeIntervalle;

		} catch (Exception e) {
			e.printStackTrace();

			return null;

		}

	}

	@Override
	public int create(Connection connexion, Intervalle intervalle) {

		int id = 0;

		// D'abord on recherche la valeur du prochain identifiant disponible

		String stmt = "SELECT MAX(id) FROM intervalle";
		try {
			Statement statement = connexion.createStatement();
			ResultSet result = statement.executeQuery(stmt);
			while (result.next()) {
				id = result.getInt(1) + 1; // Même si null, ça donne bien id 1 au final
			}

			stmt = "INSERT INTO intervalle (id, audela, jusque) VALUES ( ? , ? , ? )";
			PreparedStatement preparedStatement = connexion.prepareStatement(stmt);
			preparedStatement.setInt(1, id);
			preparedStatement.setInt(2, intervalle.getAuDela());
			preparedStatement.setInt(3, intervalle.getJusque());
			preparedStatement.execute();

			intervalle.setId(id);

			return id;

		} catch (Exception e) {
			e.printStackTrace();

			return 0;

		}
	}

	@Override
	public int deleteAll(Connection connexion) {
		String stmt = "DELETE FROM intervalle";

		try {
			Statement statement = connexion.createStatement();

			return statement.executeUpdate(stmt);

		} catch (Exception e) {
			e.printStackTrace();

			return 0;

		}
	}
}
