package user.control;

import user.beans.Member;
import user.beans.Role;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter(filterName = "AmministratoreFilter", urlPatterns = "/course/amministratore/*", dispatcherTypes = {DispatcherType.REQUEST,DispatcherType.FORWARD})
public class AmministratoreFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        System.out.println("Sono in AMMMINISTRATORE FILTER");
        Member member = (Member)request1.getSession(false).getAttribute("memberInSession");
        System.out.println(member);
        for(Role r : member.getRoles())
        {
            if(r.getRoleName().toString().equals("AMMINISTRATORE")) //provare equals con Role.AMMINISTRATORE
            {
                System.out.println("TU puoi entrare");
                chain.doFilter(request, response);
            }
        }

        // else pagina di errore, permesso posseduto non sufficiente
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }
}
