package com.jimmy.classes;

import java.math.BigDecimal;

public class Arbre extends Dimension {

	public Arbre(BigDecimal dimensionNominale, ClasseDeTolerance classeDeTolerance) {
		super(dimensionNominale, classeDeTolerance);
	}

	public String seDecrire() {
		return "arbre de dimension nominale " + this.getDimensionNominale() + " de classe de tolérance "
				+ this.getClasseDeTolerance().getCodeClasseDeTolerance() + " ES=" + this.getEcart().getEcartSuperieur()
				+ " EI=" + this.getEcart().getEcartInferieur() + " max=" + this.getDimensionMaximum() + " min="
				+ this.getDimensionMinimum();
	}
}
