package com.jimmy.classes;

public class Intervalle implements Comparable {
	private int id;
	private int auDela;
	private int jusque;

	public Intervalle(int id, int auDela, int jusque) {
		this(auDela, jusque);
		this.id = id;
	}

	public Intervalle(int auDela, int jusque) {
		this.auDela = auDela;
		this.jusque = jusque;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAuDela() {
		return auDela;
	}

	public int getJusque() {
		return jusque;
	}

	@Override
	public String toString() {
		return "Intervalle id=" + id + " audela=" + auDela + " jusque=" + jusque;
	}

	@Override
	public int compareTo(Object o) {
		Intervalle intervalle = (Intervalle) o;
		if (this.auDela == intervalle.auDela && this.jusque == intervalle.jusque) {
			return 0;
		} else {
			if (this.jusque <= intervalle.auDela) {
				return -1;
			} else {
				return 1;
			}
		}
	}
}
