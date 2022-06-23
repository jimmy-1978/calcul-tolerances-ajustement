package com.jimmy.DB;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

import com.jimmy.classes.Tolerance;

public interface ToleranceDao {
	public Tolerance getById(Connection connexion, int id);

	public Tolerance getByIdClasseDeToleranceAndDimension(Connection connexion, int idClasseDeTolerance,
			BigDecimal dimensionNominale);

	public List<Tolerance> getAll(Connection connexion);

	public int create(Connection connexion, Tolerance tolerance);

	public int deleteAll(Connection connexion);
}
