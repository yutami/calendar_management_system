package controllers.calendar;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Calendar;
import utils.DBUtil;
@WebServlet("/calendar")
public class CalendarServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    public CalendarServlet() {
        super();

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        String s_year=request.getParameter("year");
        String s_month=request.getParameter("month");
        Calendar logic=new Calendar();
        Calendar mc=null;

        if(s_year != null && s_month != null) {
            int year =Integer.parseInt(s_year);
            int month=Integer.parseInt(s_month);
            if(month==0) {
                month=12;
                year--;
            }
            if(month==13) {
                month=1;
                year++;
            }

            mc=logic.createCalendar(year,month);
        }else {

            mc=logic.createCalendar(1, 1);
        }
        em.close();
        request.setAttribute("mc", mc);

        RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/view/calendear.jsp");
        rd.forward(request, response);
    }
}

