package net.traininguniverse.javaee.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.traininguniverse.javaee.domain.Tree;
import net.traininguniverse.javaee.domain.TreeDB;
import net.traininguniverse.javaee.service.StorageService;

@WebServlet("/data")
public class DataServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		// pobieranie danych z formularza
		String sku = request.getParameter("sku");
		String treeName = request.getParameter("treeName");
		boolean isLeafy = Boolean.parseBoolean(request.getParameter("isLeafy"));
		String dataSiewuS = request.getParameter("dataSiewu");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dataSiewu = null;
		try {
			dataSiewu = format.parse(dataSiewuS);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		double cena = Double.parseDouble(request.getParameter("cena"));
		int qty = Integer.parseInt(request.getParameter("qty"));

		// dodawanie do interfejsu Map o unikalnym kluczu SKU (jednostka magazynowa,
		// ang. Stock Keeping Unit) wartości obiekt drzewo z ilością w magazynie w kontekście aplikacji
		ServletContext servletContext = getServletContext();
		StorageService storage = (StorageService) servletContext.getAttribute("storageC");
		Tree tree = new Tree(treeName, isLeafy, dataSiewu, cena);
		storage.add(sku, tree, qty);
		servletContext.setAttribute("storageC", storage);

		// wyswietlenie dodanego elementu
		Map<String, TreeDB> db = storage.getAllTrees();
		PrintWriter out = response.getWriter();
		out.println("<html><head><link rel='stylesheet' type='text/css' href='" + request.getContextPath()
				+ "/assets/css/style.css' /></head><body><h2>Dodano asortyment:</h2>"
				+ "<table><tr><th>SKU</th><th>Nazwa drzewa</th><th>Czy liściaste</th><th>Data siewu</th><th>Cena</th><th>Ilość</th></tr>"
				+ "<tr><td>" + sku + "</td><td>" + db.get(sku).getTree().getName() + "</td><td>" + db.get(sku).getTree().isLeafy()
				+ "</td><td>" + db.get(sku).getTree().getDataSiewu() + "</td><td>" + db.get(sku).getTree().getCena() + "</td><td>"
				+ db.get(sku).getQty() + "</td></table><br/><br/><a href='form'>Dodaj kolejne drzewo</a><br/><br/>"
				+ "<a href='showAllTrees'>Pokaż wszystkie drzewa</a></body></html>");
		out.close();
	}
}
