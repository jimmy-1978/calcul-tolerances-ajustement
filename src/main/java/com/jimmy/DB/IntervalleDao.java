package com.jimmy.DB;

import java.sql.Connection;
import java.util.List;

import com.jimmy.classes.Intervalle;

public interface IntervalleDao {

	public Intervalle getById(Connection connexion, int id);

	public Intervalle getByAuDelaAndJusque(Connection connexion, int auDela, int jusque);

	public List<Intervalle> getAll(Connection connexion);

	public int create(Connection connexion, Intervalle intervalle);

}
