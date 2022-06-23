package com.jimmy.DB;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

import com.jimmy.classes.Ecart;

public interface EcartDao {
	public Ecart getById(Connection connexion, int id);

	public Ecart getByEcartSuperieurAndEcartInferieur(Connection connexion, BigDecimal ecartSuperieur,
			BigDecimal ecartInferieur);

	public List<Ecart> getAll(Connection connexion);

	public int create(Connection connexion, Ecart ecart);
}
