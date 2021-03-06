package com.efsf.sf.util.filter;

import com.efsf.sf.bean.LoginMB;
import com.efsf.sf.util.Settings;
import java.io.IOException;
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

public class NoLoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        LoginMB loginBean = (LoginMB) ((HttpServletRequest) request).getSession().getAttribute("loginMB");

        if (loginBean != null && loginBean.isLogged() && loginBean.getType() != null)
        {
                if (loginBean.getType().equals(Settings.CLIENT_ACTIVE)) {
                    String contextPath = ((HttpServletRequest) request).getContextPath();
                    ((HttpServletResponse) response).sendRedirect(contextPath + "/client/clientMainPage.xhtml");
                }

                if (loginBean.getType().equals(Settings.CONSULTANT_ACTIVE)) {
                    String contextPath = ((HttpServletRequest) request).getContextPath();
                    ((HttpServletResponse) response).sendRedirect(contextPath + "/consultant/consultantMainPage.xhtml");
                }

                if (loginBean.getType().equals(Settings.ADMIN_ACTIVE)) {
                    String contextPath = ((HttpServletRequest) request).getContextPath();
                    ((HttpServletResponse) response).sendRedirect(contextPath + "/admin/adminMainPage.xhtml");
                }
        } else {
            chain.doFilter(request, response);
        }

    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
