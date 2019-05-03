package net.traininguniverse.javaee.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.traininguniverse.javaee.domain.TreeDB;
import net.traininguniverse.javaee.service.StorageService;

@WebServlet("/close")
public class CloseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletContext servletContext = getServletContext();
		StorageService storage = (StorageService) servletContext.getAttribute("storageC");
		Map<String, TreeDB> db = storage.getAllTrees();

		HttpSession session = request.getSession();
		StorageService storageS = (StorageService) session.getAttribute("storageS");
		Map<String, TreeDB> cart = storageS.getAllTrees();

		// aktualizuje dane w kontekście aplikacji, po dokonaniu zakupu
		for (String x : cart.keySet()) {

			int qtyS = cart.get(x).getQty();
			int qtyA = db.get(x).getQty();
			if (qtyS == qtyA) {
				storage.rm(x);
			} else {
				int qtyN = qtyA - qtyS;
				TreeDB treeDB = cart.get(x);
				treeDB.setQty(qtyN);
				storage.add(x, treeDB);
			}
		}
		servletContext.setAttribute("storageC", storage);

		session.invalidate();
		response.sendRedirect(request.getContextPath() + "/shop");

	}
}
