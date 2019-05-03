package net.traininguniverse.javaee.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.traininguniverse.javaee.domain.Tree;
import net.traininguniverse.javaee.domain.TreeDB;
import net.traininguniverse.javaee.service.StorageService;

@WebServlet("/basket")
public class BasketServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// pobranie z kontekstu aplikacji interfejsu Map
		ServletContext servletContext = getServletContext();
		StorageService storage = (StorageService) servletContext.getAttribute("storageC");
		Map<String, TreeDB> db = storage.getAllTrees();

		// sprawdza czy został dodany jakiś element ze sklepu w formularzu
		if (request.getParameter("add") != null) {
			String choice = request.getParameter("add");
			TreeDB treeDB = db.get(choice);

			String treeDBName = treeDB.getTree().getName();
			boolean isLeafy = treeDB.getTree().isLeafy();
			Date dataSiewu = treeDB.getTree().getDataSiewu();
			double cena = treeDB.getTree().getCena();
			int qty = 1;

			// pobranie z kontekstu sesji interfejsu Map i dodanie do niego wybranego
			// elementu ze sklepu z kontekstu aplikacji w kontekście sesji w ilości równej
			// jeden
			HttpSession session = request.getSession();
			StorageService storageS = (StorageService) session.getAttribute("storageS");

			Tree treeS = new Tree(treeDBName, isLeafy, dataSiewu, cena);
			storageS.add(choice, treeS, qty);

			session.setAttribute("storageS", storageS);
		}

		// pobranie z kontekstu sesji interfejsu Map na wypadek usunięcia elementu z
		// listy koszyka lub modyfikacji ilości produktu
		HttpSession session = request.getSession();
		StorageService storageS = (StorageService) session.getAttribute("storageS");

		// usunięcie z kontekstu sesji z interfejsu Map konkretnego elementu
		String del = request.getParameter("del");
		if (del != null) {
			storageS.rm(del);
			session.setAttribute("storageS", storageS);
		}

		Map<String, TreeDB> cart = storageS.getAllTrees();

		response.setContentType("text/html;charset=UTF-8");

		PrintWriter out = response.getWriter();

		// wyświetlenie interfejsu Map z kontekstu sesji
		out.println("<html><head><link rel='stylesheet' type='text/css' href='" + request.getContextPath()
				+ "/assets/css/style.css' /></head><body><h1>Koszyk</h1><table><tr><th>Nazwa</th>"
				+ "<th>Cena</th><th>Ilość</th><th>Usuń</th></tr>");
		for (String x : cart.keySet()) {
			String iloscN = request.getParameter("qty" + x); // iloscN - nowa ilość wybranego produktu w koszyku
			int max = db.get(x).getQty(); // max - dostępna ilość wybranego produktu
			out.println("<tr>");
			out.println("<td>" + cart.get(x).getTree().getName() + "</td>");
			out.println("<td>" + cart.get(x).getTree().getCena() + "</td>");

			// sprawdza czy zmieniana była ilość w koszyku wybranego produktu na stronie,
			// jeżeli tak, to aktualizuje ilość w kontekście sesji
			if (iloscN != null) {
				TreeDB treeDB = cart.get(x);
				treeDB.setQty(Integer.parseInt(iloscN));
				cart.put(x, treeDB);
				out.println("<td>" + "<form action='basket' method='post'><input  type='number' name='qty" + x
						+ "' min='1' max='" + max + "' value='" + cart.get(x).getQty() + "'>" + " z dostępnych: " + max
						+ " <button type='submit'>OK</button></form></td>");
				storageS.add(x, treeDB);
				session.setAttribute("storageS", storageS);
			} else {
				out.println("<td>" + "<form action='basket' method='post'><input  type='number' name='qty" + x
						+ "' min='1' max='" + max + "' value='" + cart.get(x).getQty() + "'>" + " z dostępnych: " + max
						+ " <button type='submit'>OK</button></form></td>");
			}
			out.println("<td><form action='basket' method='post'><button type='submit' value='" + x
					+ "' name='del'>Usuń</button></form></td>");
			out.println("</tr>");
		}
		out.println(
				"</table><br/><br/><a href='shop'>Kontynuuj zakupy</a><br/><br/><a href='summary'>Podsumowanie zakupu</a></body></html>");

		out.close();
	}
}
