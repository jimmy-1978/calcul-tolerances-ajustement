package com.jimmy.classes;

import java.math.BigDecimal;

public class Alesage extends Dimension {

	public Alesage(BigDecimal dimensionNominale, ClasseDeTolerance classeDeTolerance) {
		super(dimensionNominale, classeDeTolerance);
	}

	public String seDecrire() {
		return "alésage de dimension nominale " + this.getDimensionNominale() + " de classe de tolérance "
				+ this.getClasseDeTolerance().getCodeClasseDeTolerance() + " ES=" + this.getEcart().getEcartSuperieur()
				+ " EI=" + this.getEcart().getEcartInferieur() + " max=" + this.getDimensionMaximum() + " min="
				+ this.getDimensionMinimum();
	}
}
