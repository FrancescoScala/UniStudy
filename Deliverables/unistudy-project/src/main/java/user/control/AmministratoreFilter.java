package user.control;

import user.beans.Member;
import user.beans.Role;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

// to verify that the user has administrator permission
@WebFilter(filterName = "AmministratoreFilter", urlPatterns = "/course/amministratore/*", dispatcherTypes = {DispatcherType.REQUEST,DispatcherType.FORWARD})
public class AmministratoreFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        Member member = (Member)request1.getSession(false).getAttribute("memberInSession");
        for(Role r : member.getRoles())
        {
            if(r.getRoleName().toString().equals("AMMINISTRATORE"))
            {
                chain.doFilter(request, response);
            }
        }

        throw new RuntimeException("Permesso negato. L'utente in sessione non e' un Amministratore");
    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }
}
