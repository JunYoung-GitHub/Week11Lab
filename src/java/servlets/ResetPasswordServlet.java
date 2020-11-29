package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.User;
import services.AccountService;

public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
            String uuid = request.getParameter("uuid");
            
            if(uuid == null) {
                request.setAttribute("sentEmail", "false");
                getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
            }
            
            else {
                request.setAttribute("uuid", uuid);
                request.setAttribute("sentEmail", "true");
                getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
            }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        AccountService as = new AccountService();
        
        switch(action) {
            
            case "sendConfirm": 
            String sendEmail = request.getParameter("sendEmail");
            String path = getServletContext().getRealPath("/WEB-INF");
            String url = request.getRequestURL().toString();
            as.sendReset(sendEmail, path, url);
            getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
        
            case "passwordChange":
                String changePassword = request.getParameter("newPassword");
                String uuid = request.getParameter("uuid");
                String confirm = as.changePassword(uuid, changePassword);
                if(confirm != null) {
                    response.sendRedirect("login");
                }
            }
            getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
        }
        
       // getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
    }
