package net.traininguniverse.javaee.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/form")
public class FormServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		PrintWriter out = response.getWriter();
		out.println("<hmtl><body>"
				+ "<form action='data' method='post'><fieldset><legend>Dodawanie asortymentu:</legend>"
				+ "SKU: <br/><input type='text' name='sku' required/><br/>"
				+ "Nazwa drzewa: <br/><input type='text' name='treeName' required/><br/>"
				+ "Czy jest jest liściaste, czy iglaste: <br/><input type='radio' name='isLeafy' value='true' checked>Liściaste<br/>"
				+ "<input type='radio' name='isLeafy' value='false'>Iglaste<br/>"
				+ "Data siewu: <br/><input type='date' name='dataSiewu' required><br/>"
				+ "Cena: <br/><input type='text' name='cena' pattern='^[0-9]+(\\.[0-9]+)?$' required><br/>"
				+ "Ilość: <br/><input type='number' name='qty' min='1' value='1'><br/><br/>"
				+ "<input type='submit' value='Dodaj'></fieldset></form></body></html>");
		out.close();
	}
}
