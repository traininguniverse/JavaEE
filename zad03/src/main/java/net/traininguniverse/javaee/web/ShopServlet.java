package net.traininguniverse.javaee.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.traininguniverse.javaee.domain.TreeDB;
import net.traininguniverse.javaee.service.StorageService;

@WebServlet("/shop")
public class ShopServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// metoda zamieniająca wartość liczbową na polską nazwę miesiąca
	public String miesiac(int i) {
		String[] tab = { "Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień",
				"Październik", "Listopad", "Grudzień" };
		return tab[--i];
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// pobranie z kontekstu aplikacji interfejsu Map
		ServletContext servletContext = getServletContext();
		StorageService storage = (StorageService) servletContext.getAttribute("storageC");
		Map<String, TreeDB> db = storage.getAllTrees();

		response.setContentType("text/html;charset=UTF-8");

		PrintWriter out = response.getWriter();

		// wyświetlenie wszystkich elementów z interfejsu Map z opcją dodania do
		// koszyka
		out.println("<html><head><link rel='stylesheet' type='text/css' href='" + request.getContextPath()
				+ "/assets/css/style.css' /></head><body><h1>Sklep ogrodniczy z drzewami</h1>"
				+ "<table><tr><th>Nazwa</th><th>Rodzaj</th><th>Data siewu</th><th>Cena</th>"
				+ "<th><form action='basket' method='post'><input type='submit' value='Koszyk' /></th></tr>");
		for (String x : db.keySet()) {
			out.println("<tr>");
			out.println("<td>" + db.get(x).getTree().getName() + "</td>");
			if (db.get(x).getTree().isLeafy())
				out.println("<td>Liściaste</td>");
			else
				out.println("<td>Iglaste</td>");

			LocalDate localDate = db.get(x).getTree().getDataSiewu().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			int year = localDate.getYear();
			int month = localDate.getMonthValue();
			int day = localDate.getDayOfMonth();

			out.println("<td>" + day + " " + miesiac(month) + " " + year + "</td>");
			out.println("<td>" + db.get(x).getTree().getCena() + "</td>");
			out.println("<td><button type='submit' value='" + x + "' name='add'>Dodaj</button></td>");
			out.println("</tr>");
		}
		out.println("</form></table></body></html>");

		out.close();
	}
}
