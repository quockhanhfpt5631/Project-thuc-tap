package controller;

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
@WebServlet(name = "CreateQuestion", urlPatterns = {"/CreateQuestion"})
public class CreateQuestion extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int CollectionId = Integer.parseInt(request.getParameter("CollectionId"));  
        String Detail = request.getParameter("Detail");
        String AnswerA = request.getParameter("AnswerA");
        String AnswerB = request.getParameter("AnswerB");
        String AnswerC = request.getParameter("AnswerC");
        String AnswerD = request.getParameter("AnswerD");
        String TrueAnswer = request.getParameter("TrueAnswer");

        QuestionDAO dao = new QuestionDAO();
        if (Detail.equals("") || TrueAnswer.equals("") || AnswerA.equals("") || AnswerB.equals("") || AnswerC.equals("") || AnswerD.equals("")) {
            response.sendRedirect("CreateQuestion.jsp");
            
        }else {
            dao.CreateQuestion(Detail, AnswerA, AnswerB, AnswerC, AnswerD, TrueAnswer, CollectionId);
            response.sendRedirect("manageQuestion.jsp");
        }
    }
}