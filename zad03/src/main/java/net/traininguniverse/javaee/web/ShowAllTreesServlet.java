package net.traininguniverse.javaee.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.traininguniverse.javaee.domain.TreeDB;
import net.traininguniverse.javaee.service.StorageService;

@WebServlet("/showAllTrees")
public class ShowAllTreesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		// pobranie z kontekstu aplikacji interfejsu Map
		ServletContext servletContext = getServletContext();
		StorageService storage = (StorageService) servletContext.getAttribute("storageC");
		Map<String, TreeDB> db = storage.getAllTrees();

		PrintWriter out = response.getWriter();

		// wyświetlenie wszystkich elementów z interfejsu Map
		out.println("<html><link rel='stylesheet' type='text/css' href='" + request.getContextPath()
				+ "/assets/css/style.css' /></head><body><h2>Spis asortymentu w <a href='shop'>sklepie:</a></h2>"
				+ "<table><tr><th>SKU</th><th>Nazwa drzewa</th>"
				+ "<th>Rodzaj drzewa</th><th>Data siewu</th><th>Cena</th><th>Ilość</th></tr>");
		
		Set<Map.Entry<String,TreeDB>> xSet = db.entrySet();
		for(Map.Entry<String,TreeDB> x: xSet) {
			out.println("<tr>");
			out.println("<td>" + x.getKey() + "</td>");
			out.println("<td>" + x.getValue().getTree().getName() + "</td>");
			if (x.getValue().getTree().isLeafy())
				out.println("<td>Liściaste</td>");
			else
				out.println("<td>Iglaste</td>");
			out.println("<td>" + x.getValue().getTree().getDataSiewu() + "</td>");
			out.println("<td>" + x.getValue().getTree().getCena() + "</td>");
			out.println("<td>" + x.getValue().getQty() + "</td>");
			out.println("</tr>");
		}		
		out.println("</table></body></html>");
		out.close();
	}
}
