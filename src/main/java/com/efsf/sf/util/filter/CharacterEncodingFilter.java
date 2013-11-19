package com.efsf.sf.util.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/*")
public class CharacterEncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        
        HttpServletRequest request = (HttpServletRequest) req;
        String requestURI = request.getRequestURL().toString();
        
        //System.out.println("=====================> "+requestURI);
        
       // if (requestURI.contains("://spolecznoscfinansowa.pl")) {
         //   String newURI = requestURI.replace("://spolecznoscfinansowa.pl", "://www.spolecznoscfinansowa.pl");
       //     req.getRequestDispatcher(newURI).forward(req, resp);
       // } else {
            chain.doFilter(req, resp);
       // }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
