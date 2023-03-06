package user.control;

import course.beans.Enrollment;
import user.beans.Member;
import course.manager.EnrollmentManager;
import user.manager.MemberManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;


@WebServlet(name = "MemberControl", value = "/user/MemberControl")
public class MemberControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Sono in MEMBERCONTROL "+request.getParameter("action"));
        switch (request.getParameter("action")) {
            case "signup":
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                boolean check = MemberManager.signupMember(request.getParameter("email"), request.getParameter("password"),
                        request.getParameter("name"), request.getParameter("surname"));
                String mex;
                if (check) {
                    mex = "OK";
                } else {
                    mex = "Registrazione fallita, si prega di riprovare";
                }
                JSONObject json = new JSONObject();
                json.put("result", mex);
                out.print(json.toString());
                break;

            case "login":
                // verificare se è necessario crittografare la password
                System.out.println("Sono in Login");
                Member member = MemberManager.loginMember(request.getParameter("email"), request.getParameter("password"));
                if (member == null) {
                    // pagina di errore con #throw della exception
                }
                Set<Enrollment> enrollments = EnrollmentManager.retrieveEnrollmentsByMemberId(member.getId());

                if (enrollments == null) {
                    // pagina di errore con #throw della exception
                }

                // indirizza alla homepage
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/course/homepage.jsp");
                request.getSession().setAttribute("memberInSession", member);
                request.getSession().setAttribute("enrollments", enrollments);
                System.out.println(member+" , "+enrollments+" , "+dispatcher);
                dispatcher.forward(request, response);
                break;

            case "logout":
                System.out.println("Sono in logout");
                request.getSession().removeAttribute("memberInSession");
                request.getSession().removeAttribute("enrollments");
                System.out.println(request.getContextPath());
                RequestDispatcher dispatcher1 = this.getServletContext().getRequestDispatcher("/user/login.jsp");
                dispatcher1.forward(request, response);
                break;

            default:
                // pagina 404
        }
    }
}
