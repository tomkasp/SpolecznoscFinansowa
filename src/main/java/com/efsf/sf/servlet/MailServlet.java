package com.efsf.sf.servlet;

import com.efsf.sf.sql.dao.UserDAO;
import com.efsf.sf.sql.entity.User;
import com.efsf.sf.util.Security;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    //Read payment Status
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)  {

        try{
            
        String email = request.getParameter("email");
        String token = request.getParameter("token");
        UserDAO udao = new UserDAO();
        User u = udao.read(email);
        String salt = Security.sha1(u.getEmail() + u.getPassword());

        if (salt == token) {
            System.out.println("TRUE");
            response.sendRedirect("index.xhtml");

        } else {
            System.out.println("FALSE");
            response.sendRedirect("login.xhtml");
        }
        
        }catch(Exception e){}

    }
}
