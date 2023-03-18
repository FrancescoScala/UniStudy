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
import java.sql.SQLException;
import java.util.Set;


@WebServlet(name = "MemberControl", value = "/user/MemberControl")
public class MemberControl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (request.getParameter("action")) {
            case "signup":
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                boolean check = false;
                String mex = "";
                try {
                    check = MemberManager.signupMember(request.getParameter("email"), request.getParameter("password"),
                            request.getParameter("name"), request.getParameter("surname"));
                    mex = "OK";
                } catch (SQLException | RuntimeException e) {
                    e.printStackTrace();
                    mex = "Registrazione fallita, si prega di riprovare";

                } finally {
                    JSONObject json = new JSONObject();
                    json.put("result", mex);
                    out.print(json.toString());
                }
                break;

            case "login":
                // verificare se è necessario crittografare la password
                System.out.println("Sono in Login");
                Member member = null;
                try {
                    member = MemberManager.loginMember(request.getParameter("email"), request.getParameter("password"));
                    Set<Enrollment> enrollments = EnrollmentManager.retrieveEnrollmentsByMemberId(member.getId());
                    RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/course/homepage.jsp");
                    request.getSession().setAttribute("memberInSession", member);
                    request.getSession().setAttribute("enrollments", enrollments);
                    System.out.println(member + " , " + enrollments + " , " + dispatcher);
                    dispatcher.forward(request, response);

                } catch (SQLException | RuntimeException e) {
                    e.printStackTrace();
                    RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/user/login.jsp?msg=loginError");
                    dispatcher.forward(request, response);
                }
                break;

            case "modify":
                response.setContentType("application/json");
                PrintWriter out1 = response.getWriter();
                boolean check1 = false;
                String mex1 = "";
                try {
                    check1 = MemberManager.modifyInfoMember((Member) request.getSession().getAttribute("memberInSession"), request.getParameter("name"), request.getParameter("surname"), request.getParameter("newPassword"), request.getParameter("oldPassword"));
                    mex1 = "OK";
                } catch (SQLException | RuntimeException e) {
                    e.printStackTrace();
                    mex1 = "Modifica fallita, si prega di riprovare";

                } finally {
                    JSONObject json = new JSONObject();
                    json.put("result", mex1);
                    out1.print(json.toString());
                }
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
