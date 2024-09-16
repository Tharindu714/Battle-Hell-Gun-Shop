package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.Barrel;
import entity.Brand;
import entity.Gun_Action;

import entity.Model;
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
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "SearchProducts", urlPatterns = {"/SearchProducts"})
public class SearchProducts extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new Gson();

        JsonObject responseJsonObject = new JsonObject();
        responseJsonObject.addProperty("success", true);

        //get request json
        JsonObject requestJsonObject = gson.fromJson(request.getReader(), JsonObject.class);

        Session session = HibernateUtil.getSessionFactory().openSession();

        //search all products
        Criteria criteria1 = session.createCriteria(Product.class);

        //add category filter
        if (requestJsonObject.has("category_name")) {
            //category selected
            String categoryName = requestJsonObject.get("category_name").getAsString();

            //get category list from Db
            Criteria criteria2 = session.createCriteria(Brand.class);
            criteria2.add(Restrictions.eq("name", categoryName));
            Brand brand = (Brand) criteria2.uniqueResult();

            Criteria criteria3 = session.createCriteria(Model.class);
            criteria3.add(Restrictions.eq("brand", brand));
            List<Model> modelList = criteria3.list();

            if (!modelList.isEmpty()) {
                criteria1.add(Restrictions.in("model", modelList));
            }

        }

        if (requestJsonObject.has("condition")) {
            //condition selected
            String condition = requestJsonObject.get("condition").getAsString();

            //get condition from Db
            Criteria criteria4 = session.createCriteria(Gun_condition.class);
            criteria4.add(Restrictions.eq("name", condition));
            Gun_condition gun_condition = (Gun_condition) criteria4.uniqueResult();

            if (gun_condition != null) {
                criteria1.add(Restrictions.eq("gun_condition", gun_condition));
            }
        }

        if (requestJsonObject.has("action")) {
            //condition selected
            String gunaction = requestJsonObject.get("action").getAsString();

            //get condition from Db
            Criteria criteria5 = session.createCriteria(Gun_Action.class);
            criteria5.add(Restrictions.eq("bolt", gunaction));
            Gun_Action action = (Gun_Action) criteria5.uniqueResult();

            if (action != null) {
                criteria1.add(Restrictions.eq("action", action));
            }

        }

        if (requestJsonObject.has("barrel")) {
            //condition selected
            String gunbarrel = requestJsonObject.get("barrel").getAsString();

            //get condition from Db
            Criteria criteria6 = session.createCriteria(Barrel.class);
            criteria6.add(Restrictions.eq("chamber", gunbarrel));
            Barrel barrel = (Barrel) criteria6.uniqueResult();

            if (barrel != null) {
                criteria1.add(Restrictions.eq("barrel", barrel));
            }

        }

        if (requestJsonObject.has("stock")) {
            //condition selected
            String gunStock = requestJsonObject.get("stock").getAsString();

            //get condition from Db
            Criteria criteria7 = session.createCriteria(Stock.class);
            criteria7.add(Restrictions.eq("w_trigger", gunStock));
            Stock stock = (Stock) criteria7.uniqueResult();

            if (stock != null) {
                criteria1.add(Restrictions.eq("stock", stock));
            }

        }

        if (requestJsonObject.has("person")) {
            //condition selected
            String gunperson = requestJsonObject.get("person").getAsString();

            //get condition from Db
            Criteria criteria8 = session.createCriteria(Person.class);
            criteria8.add(Restrictions.eq("type", gunperson));
            Person personType = (Person) criteria8.uniqueResult();

            if (personType != null) {
                criteria1.add(Restrictions.eq("person", personType));
            }
        }

        double startPrice = requestJsonObject.get("price_range_start").getAsDouble();
        double endPrice = requestJsonObject.get("price_range_end").getAsDouble();

        criteria1.add(Restrictions.ge("price", startPrice));
        criteria1.add(Restrictions.le("price", endPrice));

        //filter products by sort from Db
        String sortText = requestJsonObject.get("sort_text").getAsString();

        switch (sortText) {
            case "Sort by Latest":
                criteria1.addOrder(Order.desc("id"));
                break;
            case "Sort by Oldest":
                criteria1.addOrder(Order.asc("id"));
                break;
            case "Sort by Name":
                criteria1.addOrder(Order.asc("title"));
                break;
            case "Sort by price":
                criteria1.addOrder(Order.asc("price"));
                break;
            default:
                break;
        }

        //get all product count
        responseJsonObject.addProperty("allProductCount", criteria1.list().size());

        //set product range
        int firstResult = requestJsonObject.get("firstResult").getAsInt();
        criteria1.setFirstResult(firstResult);
        criteria1.setMaxResults(9);

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
