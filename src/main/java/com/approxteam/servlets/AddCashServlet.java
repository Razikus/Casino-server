/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.servlets;

import com.approxteam.casino.entities.CasinoSetting;
import com.approxteam.casino.init.PredefinedCasinoSetting;
import com.approxteam.casino.interfaces.CasinoSettingsManager;
import com.approxteam.casino.interfaces.Exchanger;
import com.approxteam.casino.interfaces.WalletInterface;
import com.approxteam.casino.interfaces.exchanger.Currency;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rafal
 */
@WebServlet("/view/addcash")
public class AddCashServlet extends HttpServlet {
    
    @EJB
    private Exchanger exchanger;
    
    @EJB
    private WalletInterface walletInterface;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddCashServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddCashServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Currency[] currency = Currency.values();
        int i = 0;
       // for(Currency curr : currency){
       //     settings[i] = settingsManager.getSettingFor(pcs.getSettingName()).get();
            ++i;
       // }
        request.setAttribute("currencies",currency);      
        request.getRequestDispatcher("/WEB-INF/view/addcash.jsp").forward(request,response);
    }
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * 
     * @TODO null reference checker
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       String settingName = request.getParameter("setting");
       Double howMany = Double.valueOf(request.getParameter("value"));
       String login = request.getParameter("login");
        doGet(request,response);
       
        
       
       Double rate = exchanger.getLastestExchangeFromDatabase().get().getActualFor(Currency.valueOf(settingName)).get();
       System.out.println(rate);
       walletInterface.increaseAccountWalletBy(login, howMany / rate, "adminPanel-add");

        
    }


}
