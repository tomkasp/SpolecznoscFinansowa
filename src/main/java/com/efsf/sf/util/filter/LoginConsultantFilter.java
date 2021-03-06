package com.efsf.sf.util.filter;

import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.sql.dao.SubscriptionDAO;
import com.efsf.sf.util.Settings;
import java.io.IOException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author WR1EI1
 */
public class LoginConsultantFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        LoginMB loginBean = (LoginMB) ((HttpServletRequest) request).getSession().getAttribute("loginMB");
        

        
        if (loginBean == null || !loginBean.isLogged() || !loginBean.getType().equals(Settings.CONSULTANT_ACTIVE))
        {
            String contextPath = ((HttpServletRequest) request).getContextPath();
            ((HttpServletResponse) response).sendRedirect(contextPath + "/login.xhtml");
        }
        else if (loginBean != null )
        {
            loginBean.updateAccountSubscriptionData();
           
            HttpServletRequest req = ((HttpServletRequest) request);
            
            List lista = new SubscriptionDAO().getAllSubscriptionForConsultant(loginBean.getConsultant());
            if (lista.isEmpty() && !req.getRequestURL().toString().contains("consultantMainPage"))
            {
                            String contextPath = req.getContextPath();
                            ((HttpServletResponse) response).sendRedirect(contextPath + "/consultant/consultantMainPage.xhtml");
            }
            else
            {
                 chain.doFilter(request, response);
            }
        }

    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
