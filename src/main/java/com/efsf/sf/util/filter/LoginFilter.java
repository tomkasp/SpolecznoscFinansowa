package com.efsf.sf.util.filter;

import com.efsf.sf.bean.LoginMB;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author WR1EI1
 */
public class LoginFilter implements Filter {

	/**
         * 
	 * Checks if user is logged in. If not it redirects to the login.xhtml page.
	 */
    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// Get the loginBean from session attribute
		LoginMB loginBean = (LoginMB)((HttpServletRequest)request).getSession().getAttribute("loginMB");
		// For the first application request there is no loginBean in the session so user needs to log in
		// For other requests loginBean is present but we need to check if user has logged in successfully
		if ( loginBean == null || !loginBean.isIsLogged() ) {
			String contextPath = ((HttpServletRequest)request).getContextPath();
			((HttpServletResponse)response).sendRedirect(contextPath + "/login.xhtml");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("NO LOGIN!");
		}
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("LOGIN!");
		chain.doFilter(request, response);	
	}

    @Override
	public void init(FilterConfig config) throws ServletException {
		// Nothing to do here!
	}

    @Override
	public void destroy() {
		// Nothing to do here!
	}	
	
}

