package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.Brand;
import entity.City;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

/**
 *
 * @author Tharindu
 */
@WebServlet(name = "LoadCity", urlPatterns = {"/LoadCity"})
public class LoadCity extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //GSON OBJECT
        Gson gson = new Gson();

        //OPEN SESSION
        Session session = HibernateUtil.getSessionFactory().openSession();

        //====> CITIES <====//
        //SELECT ALL CITIES
        Criteria criteria1 = session.createCriteria(City.class);
        criteria1.addOrder(Order.asc("name"));
        List<City> cityList = criteria1.list();

        //JSON OBJECT {[],[],[],[],[]}
        JsonObject jsonObject = new JsonObject();

        jsonObject.add("cityList", gson.toJsonTree(cityList));

        //SEND RESPONSE
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(jsonObject));

        //SESSION CLOSE
        session.close();
    }

}
