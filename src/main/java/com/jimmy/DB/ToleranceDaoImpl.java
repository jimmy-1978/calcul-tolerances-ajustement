package com.jimmy.DB;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jimmy.classes.ClasseDeTolerance;
import com.jimmy.classes.Ecart;
import com.jimmy.classes.Intervalle;
import com.jimmy.classes.Tolerance;

public class ToleranceDaoImpl implements ToleranceDao {

	@Override
	public Tolerance getById(Connection connexion, int id) {
		String stmt = "SELECT id, id_classe_de_tolerance, id_intervalle, id_ecart FROM tolerance WHERE id = ?";
		try {
			PreparedStatement preparedStatement = connexion.prepareStatement(stmt);
			preparedStatement.setInt(1, id);
			ResultSet result = preparedStatement.executeQuery();

			ClasseDeToleranceDaoImpl classeDeToleranceDaoImpl = new ClasseDeToleranceDaoImpl();
			IntervalleDaoImpl intervalleDaoImpl = new IntervalleDaoImpl();
			EcartDaoImpl ecartDaoImpl = new EcartDaoImpl();

			ClasseDeTolerance classeDeTolerance = null;
			Intervalle intervalle = null;
			Ecart ecart = null;
			Tolerance tolerance = null;

			while (result.next()) {
				classeDeTolerance = classeDeToleranceDaoImpl.getById(connexion,
						result.getInt("id_classe_de_tolerance"));
				intervalle = intervalleDaoImpl.getById(connexion, result.getInt("id_intervalle"));
				ecart = ecartDaoImpl.getById(connexion, result.getInt("id_ecart"));

				tolerance = new Tolerance(result.getInt("id"), classeDeTolerance, intervalle, ecart);
			}

			return tolerance;

		} catch (Exception e) {
			e.printStackTrace();

			return null;

		}
	}

	@Override
	public Tolerance getByIdClasseDeToleranceAndDimension(Connection connexion, int idClasseDeTolerance,
			BigDecimal dimensionNominale) {

		String stmt = """
				SELECT tolerance.id, tolerance.id_classe_de_tolerance, tolerance.id_intervalle, tolerance.id_ecart
				FROM tolerance , classe_de_tolerance, intervalle
				WHERE tolerance.id_intervalle = intervalle.id
				AND tolerance.id_classe_de_tolerance = classe_de_tolerance.id
				AND id_classe_de_tolerance = ?
				AND intervalle.audela < ?
				AND intervalle.jusque >= ?
				""";

		try {
			PreparedStatement preparedStatement = connexion.prepareStatement(stmt);
			preparedStatement.setInt(1, idClasseDeTolerance);
			preparedStatement.setBigDecimal(2, dimensionNominale);
			preparedStatement.setBigDecimal(3, dimensionNominale);
			ResultSet result = preparedStatement.executeQuery();

			ClasseDeToleranceDaoImpl classeDeToleranceDaoImpl = new ClasseDeToleranceDaoImpl();
			IntervalleDaoImpl intervalleDaoImpl = new IntervalleDaoImpl();
			EcartDaoImpl ecartDaoImpl = new EcartDaoImpl();

			ClasseDeTolerance classeDeTolerance = null;
			Intervalle intervalle = null;
			Ecart ecart = null;
			Tolerance tolerance = null;

			while (result.next()) {
				classeDeTolerance = classeDeToleranceDaoImpl.getById(connexion,
						result.getInt("tolerance.id_classe_de_tolerance"));
				intervalle = intervalleDaoImpl.getById(connexion, result.getInt("tolerance.id_intervalle"));
				ecart = ecartDaoImpl.getById(connexion, result.getInt("tolerance.id_ecart"));

				tolerance = new Tolerance(result.getInt("tolerance.id"), classeDeTolerance, intervalle, ecart);
			}

			return tolerance;

		} catch (Exception e) {
			e.printStackTrace();

			return null;

		}
	}

	@Override
	public List<Tolerance> getAll(Connection connexion) {
		String stmt = "SELECT id, id_classe_de_tolerance, id_intervalle, id_ecart FROM tolerance";
		try {
			Statement statement = connexion.createStatement();
			ResultSet result = statement.executeQuery(stmt);

			ClasseDeToleranceDaoImpl classeDeToleranceDaoImpl = new ClasseDeToleranceDaoImpl();
			IntervalleDaoImpl intervalleDaoImpl = new IntervalleDaoImpl();
			EcartDaoImpl ecartDaoImpl = new EcartDaoImpl();

			ClasseDeTolerance classeDeTolerance = null;
			Intervalle intervalle = null;
			Ecart ecart = null;

			Tolerance tolerance = null;
			List<Tolerance> listeTolerance = new ArrayList<Tolerance>();

			while (result.next()) {
				classeDeTolerance = classeDeToleranceDaoImpl.getById(connexion,
						result.getInt("id_classe_de_tolerance"));
				intervalle = intervalleDaoImpl.getById(connexion, result.getInt("id_intervalle"));
				ecart = ecartDaoImpl.getById(connexion, result.getInt("id_ecart"));

				tolerance = new Tolerance(result.getInt("id"), classeDeTolerance, intervalle, ecart);

				listeTolerance.add(tolerance);
			}

			return listeTolerance;

		} catch (Exception e) {
			e.printStackTrace();

			return null;

		}
	}

	@Override
	public int create(Connection connexion, Tolerance tolerance) {

		// On part du principe que si :
		// - les id existent dans ClasseDeTolerance, Intervalle et Ecart (attributs de
		// Tolerance)
		// --> ils existent donc on ne les crée pas
		// - les id sont vides
		// --> on les crée

		if (tolerance.getClasseDeTolerance().getId() == 0) {
			ClasseDeToleranceDaoImpl classeDeToleranceDaoImpl = new ClasseDeToleranceDaoImpl();
			classeDeToleranceDaoImpl.create(connexion, tolerance.getClasseDeTolerance()); // id sera mis à jour
		}

		if (tolerance.getIntervalle().getId() == 0) {
			IntervalleDaoImpl intervalleDaoImpl = new IntervalleDaoImpl();
			intervalleDaoImpl.create(connexion, tolerance.getIntervalle()); // id sera mis à jour
		}

		if (tolerance.getEcart().getId() == 0) {
			EcartDaoImpl ecartDaoImpl = new EcartDaoImpl();
			ecartDaoImpl.create(connexion, tolerance.getEcart()); // id sera mis à jour
		}

		String stmt = "SELECT MAX(id) FROM tolerance";
		try {
			Statement statement = connexion.createStatement();
			ResultSet result = statement.executeQuery(stmt);
			int id = 0;
			while (result.next()) {
				id = result.getInt(1) + 1; // Même si null, ça donne bien id 1 au final
			}

			stmt = "INSERT INTO tolerance (id, id_classe_de_tolerance, id_intervalle, id_ecart) VALUES ( ? , ? , ? , ? )";
			PreparedStatement preparedStatement = connexion.prepareStatement(stmt);
			preparedStatement.setInt(1, id);
			preparedStatement.setInt(2, tolerance.getClasseDeTolerance().getId());
			preparedStatement.setInt(3, tolerance.getIntervalle().getId());
			preparedStatement.setInt(4, tolerance.getEcart().getId());
			preparedStatement.execute();

			tolerance.setId(id);

			return id;

		} catch (Exception e) {
			e.printStackTrace();

			return 0;

		}
	}
}
