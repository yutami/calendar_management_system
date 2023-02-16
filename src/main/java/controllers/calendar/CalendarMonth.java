package controllers.calendar;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calendar/month")
public class CalendarMonth extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{


        int year;
        int month;

        String param = request.getParameter("YEAR");
        if (param == null || param.length() == 0){
            year = -999;
        }else{
            try{
                year = Integer.parseInt(request.getParameter("year"));
                month=Integer.parseInt(request.getParameter("month"));
            }catch (Exception e){
                year = 1;
                month=1;
            }
        }

        param = request.getParameter("MONTH");
        if (param == null || param.length() == 0){
            month = -999;
        }else{
            try{
                month = Integer.parseInt(param);
            }catch (NumberFormatException e){
                month = -999;
            }
        }
        if (year == -999 || month == -999){
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);

        }else{
            if (month == 12){
                month = 0;
                year++;
            }

            if (month == -1){
                month = 11;
                year--;
            }
        }
    }
}