servlet:
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.controller;

import com.dao.QuestionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.util.List;
import com.entity.BankQuestion;
/**
*
* @author Thinh
*/
@WebServlet(name = "AddFile", urlPatterns = {"/AddFile"})
public class AddFile extends HttpServlet {

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       int CollectionId = Integer.parseInt(request.getParameter("id"));
       request.setAttribute("CollectionId", CollectionId);
       request.getRequestDispatcher("test.jsp").forward(request, response);

   }

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
       int CollectionId = Integer.parseInt(request.getParameter("CollectionId"));    
       String fileName = request.getParameter("upload");
       
	QuestionDAO dao = new QuestionDAO();
	String error = QuestionDAO.readFile(fileName,  CollectionId)
       
       int lastPage = dao.lastPagesP(1, CollectionId);
       List<BankQuestion> list = dao.getQuestionByID(CollectionId);
       request.setAttribute("list", list);
       request.setAttribute("idd", CollectionId);
       request.getRequestDispatcher("EditOption.jsp").forward(request, response);
   }
}