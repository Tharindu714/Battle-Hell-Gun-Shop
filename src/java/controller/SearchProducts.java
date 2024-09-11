package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.Barrel;
import entity.Brand;
import entity.Gun_Action;
import entity.Gun_condition;
import entity.Model;
import entity.Person;
import entity.Product;
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
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "SearchProducts", urlPatterns = {"/SearchProducts"})
public class SearchProducts extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new Gson();
        JsonObject responseJsonObject = new JsonObject();
        responseJsonObject.addProperty("success", false);
        //get request json
        JsonObject requestJsonObject = gson.fromJson(request.getReader(), JsonObject.class);
        Session session = HibernateUtil.getSessionFactory().openSession();
        //search all products
        Criteria criteria1 = session.createCriteria(Product.class);

        //add category filter
        if (requestJsonObject.has("Brand_name")) {
            //category selected
            String BrandName = requestJsonObject.get("Brand_name").getAsString();

            //get Brand list from Db
            Criteria criteria2 = session.createCriteria(Brand.class);
            criteria2.add(Restrictions.eq("name", BrandName));
            List<Brand> brandList = criteria2.list();

            //get models by category from DB
            Criteria criteria3 = session.createCriteria(Model.class);
            criteria3.add(Restrictions.in("brand", brandList));
            List<Model> modelList = criteria3.list();

            //filter products by model list from DB
            criteria1.add(Restrictions.in("model", modelList));
        }

        if (requestJsonObject.has("action")) {
            String action = requestJsonObject.get("action").getAsString();

            Criteria criteria4 = session.createCriteria(Gun_Action.class);
            criteria4.add(Restrictions.eq("bolt", action));
            Gun_Action gunAction = (Gun_Action) criteria4.uniqueResult();

            criteria1.add(Restrictions.eq("gun_action", gunAction));
        }

        if (requestJsonObject.has("barrel")) {
            String barrel = requestJsonObject.get("barrel").getAsString();

            Criteria criteria5 = session.createCriteria(Barrel.class);
            criteria5.add(Restrictions.eq("chamber", barrel));
            Barrel gunBarrel = (Barrel) criteria5.uniqueResult();

            criteria1.add(Restrictions.eq("barrel", gunBarrel));
        }

        if (requestJsonObject.has("stock")) {
            String stock = requestJsonObject.get("stock").getAsString();

            Criteria criteria6 = session.createCriteria(Stock.class);
            criteria6.add(Restrictions.eq("w_trigger", stock));
            Stock gunStock = (Stock) criteria6.uniqueResult();

            criteria1.add(Restrictions.eq("stock", gunStock));
        }

        if (requestJsonObject.has("person")) {
            String person = requestJsonObject.get("person").getAsString();

            Criteria criteria7 = session.createCriteria(Person.class);
            criteria7.add(Restrictions.eq("type", person));
            Person gunPerson = (Person) criteria7.uniqueResult();

            criteria1.add(Restrictions.eq("person", gunPerson));
        }

        if (requestJsonObject.has("condition")) {
            String condition = requestJsonObject.get("condition").getAsString();

            //get condition from Db
            Criteria criteria8 = session.createCriteria(Gun_condition.class);
            criteria8.add(Restrictions.eq("name", condition));
            Gun_condition gun_Condition = (Gun_condition) criteria8.uniqueResult();

            //filter products by condition from DB
            criteria1.add(Restrictions.eq("gun_condition", gun_Condition));

        }

        double startPrice = requestJsonObject.get("price_range_start").getAsDouble();
        double endPrice = requestJsonObject.get("price_range_end").getAsDouble();

        criteria1.add(Restrictions.ge("price", startPrice));
        criteria1.add(Restrictions.le("price", endPrice));

        //filter products by sort from Db
        String sortText = requestJsonObject.get("sort_text").getAsString();

        if (sortText.equals("Sort by Latest")) {
            criteria1.addOrder(Order.desc("id"));

        } else if (sortText.equals("Sort by Oldest")) {
            criteria1.addOrder(Order.asc("id"));

        } else if (sortText.equals("Sort by Name")) {
            criteria1.addOrder(Order.asc("title"));

        } else if (sortText.equals("Sort by price")) {
            criteria1.addOrder(Order.asc("price"));

        }

        //get all product count
        responseJsonObject.addProperty("allProductCount", criteria1.list().size());

        //set product range
        int firstResult = requestJsonObject.get("firstResult").getAsInt();
        criteria1.setFirstResult(firstResult);
        criteria1.setMaxResults(6);

        //get product list
        List<Product> productList = criteria1.list();
        System.out.println(productList.size());

        //get product list
        for (Product product : productList) {
            product.setUser(null);
        }

        responseJsonObject.addProperty("success", true);
        responseJsonObject.add("productList", gson.toJsonTree(productList));

        //send response
        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(responseJsonObject));

    }

}
