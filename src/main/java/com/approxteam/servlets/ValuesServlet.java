/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.servlets;

import com.approxteam.casino.entities.CasinoSetting;
import com.approxteam.casino.init.PredefinedCasinoSetting;
import com.approxteam.casino.interfaces.CasinoSettingsManager;
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
@WebServlet("/view/values")
public class ValuesServlet extends HttpServlet {

    @EJB
    private CasinoSettingsManager settingsManager;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PredefinedCasinoSetting[] defaultSettings = PredefinedCasinoSetting.values();
        CasinoSetting[] settings = new CasinoSetting[defaultSettings.length];
        int i = 0;
        for(PredefinedCasinoSetting pcs : defaultSettings){
            settings[i] = settingsManager.getSettingFor(pcs.getSettingName()).get();
            ++i;
        }
        request.setAttribute("settings",settings);              
        request.getRequestDispatcher("/WEB-INF/view/valueslist.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		doGet(request, response);    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
