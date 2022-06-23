package com.jimmy.servlets;

import java.io.IOException;

import com.jimmy.forms.CalculToleranceForm;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AffichageTolerance extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CalculToleranceForm calculToleranceForm = new CalculToleranceForm();
		calculToleranceForm.calculer(request);

		this.getServletContext().getRequestDispatcher("/WEB-INF/affichageTolerance.jsp").forward(request, response);
	}

}
