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
        String requestURI = request.getServerName()+"; "+request.getProtocol()+"; "+request.getRemoteHost()+"; "+request.getContextPath()+"; "+request.getLocalName()+"; "+request.getRequestURL();
        
        System.out.println("=====================> "+requestURI);
        
     //   if (requestURI.contains("://localhost:8084")) {
        //    String toReplace = requestURI.substring(requestURI.indexOf("/Dir_My_App"), requestURI.lastIndexOf("/") + 1);
        //    String newURI = requestURI.replace(toReplace, "?Contact_Id=");
       //     req.getRequestDispatcher(newURI).forward(req, resp);
             chain.doFilter(req, resp);
       // } else {
            chain.doFilter(req, resp);
      //  }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
