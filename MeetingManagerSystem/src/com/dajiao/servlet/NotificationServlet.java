package com.dajiao.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dajiao.model.Person;
import com.dajiao.model.User;
import com.dajiao.service.NotificationService;

/**
 * Servlet implementation class NotificationServlet
 */
@WebServlet("/NotificationServlet")
public class NotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NotificationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		User user = (User) request.getSession().getAttribute("person");

		if (user != null) {
			
			String agree = (String)request.getParameter("agree");
			if(agree != null){
				if(NotificationService.agreeInvite(Integer.parseInt(agree), user.getAccount()))
					System.out.println("agree invite " + agree);
			}
					
			String disagree = (String)request.getParameter("disagree");
			if(disagree != null){
				if(NotificationService.disagreeInvite(Integer.parseInt(disagree), user.getAccount()))
					System.out.println("ignore invite " + disagree);
			}

			// get the lists
			request.setAttribute("notifyList",
					NotificationService.getInviteMessage(user.getaccount()));
			request.setAttribute("changeList",
					NotificationService.getChangeMessage(user.getaccount()));
			request.setAttribute("summaryList",
					NotificationService.getSummaryMessage(user.getaccount()));

			request.getRequestDispatcher("./myNotification.jsp").forward(
					request, response);
		} else {
			response.sendRedirect("./meetingManager.jsp");
		}
	}

}
