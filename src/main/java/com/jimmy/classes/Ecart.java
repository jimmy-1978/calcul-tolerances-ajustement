package com.jimmy.classes;

import java.math.BigDecimal;

public class Ecart implements Comparable {

	private int id;
	private BigDecimal ecartSuperieur;
	private BigDecimal ecartInferieur;

	public Ecart(int id, BigDecimal ecartSuperieur, BigDecimal ecartInferieur) {
		this(ecartSuperieur, ecartInferieur);
		this.id = id;
	}

	public Ecart(BigDecimal ecartSuperieur, BigDecimal ecartInferieur) {
		this.ecartSuperieur = ecartSuperieur;
		this.ecartInferieur = ecartInferieur;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getEcartSuperieur() {
		return ecartSuperieur;
	}

	public BigDecimal getEcartInferieur() {
		return ecartInferieur;
	}

	@Override
	public String toString() {
		return "Ecart id=" + id + " ES=" + ecartSuperieur + " EI=" + ecartInferieur;
	}

	@Override
	public int compareTo(Object o) {
		Ecart ecart = (Ecart) o;
		if (this.ecartSuperieur.compareTo(ecart.ecartSuperieur) == 0
				&& this.ecartInferieur.compareTo(ecart.ecartInferieur) == 0) {
			return 0;
		} else {
			return -1;
		}
	}
}
