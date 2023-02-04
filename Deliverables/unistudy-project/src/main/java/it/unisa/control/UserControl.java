package it.unisa.control;

import it.unisa.beans.Enrollment;
import it.unisa.beans.User;
import it.unisa.dao.EnrollmentManager;
import it.unisa.dao.UserManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Set;

@WebServlet(name = "UserControl", value = "/UserControl")
public class UserControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (request.getParameter("action"))
        {
            case "login":
                // verificare se è necessario crittografare la password
                User user = UserManager.loginUser(request.getParameter("email"), request.getParameter("password"));
                if(user == null)
                {
                    // pagina di errore con #throw della exception
                }
                Set<Enrollment> enrollments = EnrollmentManager.retrieveEnrollmentByUserId(user.getId());
                if(enrollments==null)
                {
                    // pagina di errore con #throw della exception
                }

                // indirizza alla homepage
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/partecipante/homepage.jsp"); //homepage.jsp
                request.getSession().setAttribute("userInSession", user);
                request.getSession().setAttribute("enrollments", enrollments);
                dispatcher.forward(request,response);
                break;

            case "signup":
                break;

            default:
                // pagina 404
        }
    }
}
