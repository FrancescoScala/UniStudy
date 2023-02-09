package it.unisa.filter;

import it.unisa.beans.Role;
import it.unisa.beans.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebFilter(urlPatterns = "/amministratore/*", dispatcherTypes = {DispatcherType.REQUEST,DispatcherType.FORWARD})
public class AmministratoreFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        User user = (User)request.getAttribute("userInSession");
        for(Role r : user.getRoles())
        {
            if(r.getRoleName().equals("AMMINISTRATORE"))
            {
                chain.doFilter(request, response);
            }
        }

        // else pagina di errore, permesso posseduto non sufficiente
    }
}
