package it.unisa.filter;

import it.unisa.beans.Role;
import it.unisa.beans.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = "/amministratore/*", dispatcherTypes = {DispatcherType.REQUEST,DispatcherType.FORWARD})
public class AmministratoreFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        User user = (User)request1.getSession(false).getAttribute("userInSession");
        for(Role r : user.getRoles())
        {
            if(r.getRoleName().toString().equals("AMMINISTRATORE")) //provare equals con Role.AMMINISTRATORE
            {
                chain.doFilter(request, response);
            }
        }

        // else pagina di errore, permesso posseduto non sufficiente
    }
}
