package net.traininguniverse.javaee.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.traininguniverse.javaee.domain.TreeDB;
import net.traininguniverse.javaee.service.StorageService;

@WebServlet("/summary")
public class SummaryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// pobranie interfesju Map z kontekstu sesji
		HttpSession session = request.getSession();
		StorageService storageS = (StorageService) session.getAttribute("storageS");
		Map<String, TreeDB> cart = storageS.getAllTrees();
		double zaplata = 0; // łączna suma za wszystkie zakupy

		response.setContentType("text/html;charset=UTF-8");

		PrintWriter out = response.getWriter();

		// wyświetlenie podsumowania koszyka w kontekście sesji
		out.println("<html><head><link rel='stylesheet' type='text/css' href='" + request.getContextPath()
				+ "/assets/css/style.css' /></head><body><h1>Podsumowanie zakupu</h1>"
				+ "<table><tr><th>Nazwa</th><th>Cena</th><th>Ilość</th><th>Suma</th></tr>");
		for (String x : cart.keySet()) {
			int ilosc = cart.get(x).getQty();
			double cenaX = cart.get(x).getTree().getCena();
			double suma = cenaX * ilosc;
			zaplata += suma;
			out.println("<tr>");
			out.println("<td>" + cart.get(x).getTree().getName() + "</td>");
			out.println("<td>" + cenaX + "</td>");
			out.println("<td>" + cart.get(x).getQty() + "</td>");
			out.println("<td>" + suma + "</td>");
			out.println("</tr>");
		}
		out.println("</table>Razem do zapłaty: " + zaplata);
		out.println("<br/><a href='close'>Potwierdzam zakup</a></body></html>");

		out.close();
	}
}
