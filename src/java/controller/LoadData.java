package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.Barrel;
import entity.Brand;
import entity.Gun_Action;
import entity.Product;
import entity.Gun_condition;
import entity.Person;
import entity.Stock;
import java.io.IOException;
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

@WebServlet(name = "LoadData", urlPatterns = {"/LoadData"})
public class LoadData extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("success", false);

        Gson gson = new Gson();

        Session session = HibernateUtil.getSessionFactory().openSession();

        //main code
        //get category list from DB
        Criteria criteria1 = session.createCriteria(Brand.class);
        List<Brand> categoryList = criteria1.list();
        jsonObject.add("categoryList", gson.toJsonTree(categoryList));

        //get condition list from DB
        Criteria criteria2 = session.createCriteria(Gun_condition.class);
        List<Gun_condition> conditionList = criteria2.list();
        jsonObject.add("conditionList", gson.toJsonTree(conditionList));

        //get condition list from DB
        Criteria criteria3 = session.createCriteria(Gun_Action.class);
        List<Gun_Action> actionList = criteria3.list();
        jsonObject.add("actionList", gson.toJsonTree(actionList));

        Criteria criteria4 = session.createCriteria(Barrel.class);
        List<Barrel> barrelList = criteria4.list();
        jsonObject.add("barrelList", gson.toJsonTree(barrelList));

        Criteria criteria5 = session.createCriteria(Stock.class);
        List<Stock> stockList = criteria5.list();
        jsonObject.add("stockList", gson.toJsonTree(stockList));

        Criteria criteria6 = session.createCriteria(Person.class);
        List<Person> personList = criteria6.list();
        jsonObject.add("personList", gson.toJsonTree(personList));

        //get product list from DB
        Criteria criteria0 = session.createCriteria(Product.class);

        //Get latest product
        criteria0.addOrder(Order.desc("id"));
        jsonObject.addProperty("allProductCount", criteria0.list().size());

        //set product range
        criteria0.setFirstResult(0);
        criteria0.setMaxResults(9);

        List<Product> productList = criteria0.list();

        //remove user from product
        for (Product product : productList) {
            product.setUser(null);
        }

        jsonObject.add("productList", gson.toJsonTree(productList));
        jsonObject.addProperty("success", true);
        //main code

        session.close();
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(jsonObject));

    }

}
