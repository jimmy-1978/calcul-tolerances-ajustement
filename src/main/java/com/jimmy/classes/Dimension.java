package com.jimmy.classes;

import java.math.BigDecimal;
import java.sql.Connection;

import com.jimmy.DB.ConnexionDBMySql;
import com.jimmy.DB.ToleranceDaoImpl;

public abstract class Dimension {

	private BigDecimal dimensionNominale; // En mm
	private ClasseDeTolerance classeDeTolerance;
	private Ecart ecart;
	private BigDecimal dimensionMaximum;
	private BigDecimal dimensionMinimum;

	public Dimension(BigDecimal dimensionNominale, ClasseDeTolerance classeDeTolerance) {
		this.dimensionNominale = dimensionNominale.setScale(3);
		this.classeDeTolerance = classeDeTolerance;

		ConnexionDBMySql connexionDBMySql = new ConnexionDBMySql();
		Connection connexion = connexionDBMySql.getConnexion();

		ToleranceDaoImpl toleranceDaoImpl = new ToleranceDaoImpl();
		Tolerance tolerance = toleranceDaoImpl.getByIdClasseDeToleranceAndDimension(connexion,
				classeDeTolerance.getId(), dimensionNominale);

		connexionDBMySql.closeConnexion(connexion);

		ecart = tolerance.getEcart();

		dimensionMaximum = dimensionNominale.add(ecart.getEcartSuperieur().movePointLeft(3));
		dimensionMinimum = dimensionNominale.add(ecart.getEcartInferieur().movePointLeft(3));

	}

	public BigDecimal getDimensionNominale() {
		return dimensionNominale;
	}

	public Ecart getEcart() {
		return ecart;
	}

	public BigDecimal getDimensionMaximum() {
		return dimensionMaximum;
	}

	public BigDecimal getDimensionMinimum() {
		return dimensionMinimum;
	}

	public ClasseDeTolerance getClasseDeTolerance() {
		return classeDeTolerance;
	}

	public abstract String seDecrire();

	public String getDescription() {
		return seDecrire();
	}
}
