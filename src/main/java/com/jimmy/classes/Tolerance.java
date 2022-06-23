package com.jimmy.classes;

public class Tolerance {
	int id;
	private ClasseDeTolerance classeDeTolerance;
	private Intervalle intervalle;
	private Ecart ecart;

	public Tolerance(int id, ClasseDeTolerance classeDeTolerance, Intervalle intervalle, Ecart ecart) {
		this(classeDeTolerance, intervalle, ecart);
		this.id = id;
	}

	public Tolerance(ClasseDeTolerance classeDeTolerance, Intervalle intervalle, Ecart ecart) {
		this.classeDeTolerance = classeDeTolerance;
		this.intervalle = intervalle;
		this.ecart = ecart;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ClasseDeTolerance getClasseDeTolerance() {
		return classeDeTolerance;
	}

	public Intervalle getIntervalle() {
		return intervalle;
	}

	public Ecart getEcart() {
		return ecart;
	}

	@Override
	public String toString() {
		return "Tolerance id=" + id + " " + classeDeTolerance + " " + intervalle + " " + ecart;
	}

}
