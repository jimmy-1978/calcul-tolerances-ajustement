package com.jimmy.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jimmy.classes.ClasseDeTolerance;

public class ClasseDeToleranceDaoImpl implements ClasseDeToleranceDao {

	private Connection connexion;

	@Override
	public ClasseDeTolerance getById(Connection connexion, int id) {
		String stmt = "SELECT code_classe_de_tolerance FROM classe_de_tolerance WHERE id = ?";

		try {

			PreparedStatement preparedStatement = connexion.prepareStatement(stmt);
			preparedStatement.setInt(1, id);
			ResultSet result = preparedStatement.executeQuery();
			ClasseDeTolerance classeDeTolerance = null;
			while (result.next()) {
				classeDeTolerance = new ClasseDeTolerance(id, result.getString("code_classe_de_tolerance"));
			}
			return classeDeTolerance;

		} catch (Exception e) {
			e.printStackTrace();

			return null;

		}
	}

	@Override
	public ClasseDeTolerance getByCodeClasseDeTolerance(Connection connexion, String codeClasseDeTolerance) {
		String stmt = "SELECT id, code_classe_de_tolerance FROM classe_de_tolerance WHERE BINARY code_classe_de_tolerance = ?";
		// BINARY : pour rendre case sensitive !!!

		try {
			PreparedStatement preparedStatement = connexion.prepareStatement(stmt);
			preparedStatement.setString(1, codeClasseDeTolerance);
			ResultSet result = preparedStatement.executeQuery();
			ClasseDeTolerance classeDeTolerance = null;
			while (result.next()) {
				classeDeTolerance = new ClasseDeTolerance(result.getInt("id"),
						result.getString("code_classe_de_tolerance"));
			}

			return classeDeTolerance;

		} catch (Exception e) {
			e.printStackTrace();

			return null;

		}
	}

	@Override
	public List<ClasseDeTolerance> getAll(Connection connexion) {
		String stmt = "SELECT id, code_classe_de_tolerance FROM classe_de_tolerance";

		try {

			Statement statement = connexion.createStatement();
			ResultSet result = statement.executeQuery(stmt);

			List<ClasseDeTolerance> listeClasseDeTolerance = new ArrayList<ClasseDeTolerance>();
			ClasseDeTolerance classeDeTolerance;

			while (result.next()) {
				classeDeTolerance = new ClasseDeTolerance(result.getInt("id"),
						result.getString("code_classe_de_tolerance"));
				listeClasseDeTolerance.add(classeDeTolerance);
			}

			return listeClasseDeTolerance;

		} catch (Exception e) {
			e.printStackTrace();

			return null;

		}
	}

	@Override
	public int create(Connection connexion, ClasseDeTolerance classeDeTolerance) {
		String stmt = "SELECT MAX(id) FROM classe_de_tolerance";

		try {
			Statement statement = connexion.createStatement();
			ResultSet result = statement.executeQuery(stmt);
			int id = 0;
			while (result.next()) {
				id = result.getInt(1) + 1; // Même si null, ça donne bien id 1 au final
			}
			stmt = "INSERT INTO classe_de_tolerance (id, code_classe_de_tolerance) VALUES ( ? , ? )";
			PreparedStatement preparedStatement = connexion.prepareStatement(stmt);
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, classeDeTolerance.getCodeClasseDeTolerance());
			preparedStatement.execute();

			classeDeTolerance.setId(id); // On met à jour l'id dans l'objet en paramètre

			return id;

		} catch (Exception e) {
			e.printStackTrace();

			return 0;

		}
	}

	@Override
	public int deleteAll(Connection connexion) {
		String stmt = "DELETE FROM classe_de_tolerance";

		try {
			Statement statement = connexion.createStatement();

			return statement.executeUpdate(stmt);

		} catch (Exception e) {
			e.printStackTrace();

			return 0;

		}
	}
}
