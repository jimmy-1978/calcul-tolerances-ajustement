package com.jimmy.DB;

import java.sql.Connection;
import java.util.List;

import com.jimmy.classes.ClasseDeTolerance;

public interface ClasseDeToleranceDao {

	public ClasseDeTolerance getById(Connection connexion, int id);

	public ClasseDeTolerance getByCodeClasseDeTolerance(Connection connexion, String codeClasseDeTolerance);

	public List<ClasseDeTolerance> getAll(Connection connexion);

	public int create(Connection connexion, ClasseDeTolerance classeDeTolerance);

	public int deleteAll(Connection connexion);

}
