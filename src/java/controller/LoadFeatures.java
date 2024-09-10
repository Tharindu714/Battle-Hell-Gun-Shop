package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import entity.Gun_Action;
import entity.Barrel;
import entity.Brand;
import entity.Bullet_type;
import entity.Gun_condition;
import entity.Magazine;
import entity.Model;
import entity.Muzzle;
import entity.Person;
import entity.Stock;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import model.HibernateUtil;

@WebServlet(name = "LoadFeatures", urlPatterns = {"/LoadFeatures"})
public class LoadFeatures extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        //GSON OBJECT
        Gson gson = new Gson();

        //OPEN SESSION
        Session session = HibernateUtil.getSessionFactory().openSession();

        //====> CATEGORY <====//
        //SELECT ALL CATEGORIES
        Criteria criteria1 = session.createCriteria(Brand.class);
        criteria1.addOrder(Order.asc("name"));
        List<Brand> BrandList = criteria1.list(); //CATEGORY LIST

        //====> MODEL <====//
        //SELECT ALL MODELS
        Criteria criteria2 = session.createCriteria(Model.class);
        criteria2.addOrder(Order.asc("name"));
        List<Model> modelList = criteria2.list(); //MODEL LIST

        //====> MAGAZINE <====//
        //SELECT ALL MAGAZINES
        Criteria criteria3 = session.createCriteria(Magazine.class);
        criteria3.addOrder(Order.asc("name"));
        List<Magazine> magList = criteria3.list(); //MAGAZINE LIST

        //====> STOCK <====//
        //SELECT ALL STOCKS
        Criteria criteria4 = session.createCriteria(Stock.class);
        criteria4.addOrder(Order.asc("w_trigger"));
        List<Stock> stockList = criteria4.list(); //STOCK LIST

        //====> MUZZLE <====//
        //SELECT ALL MUZZLE TYPES
        Criteria criteria5 = session.createCriteria(Muzzle.class);
        criteria5.addOrder(Order.asc("name"));
        List<Muzzle> muzzList = criteria5.list(); //MUZZLE TYPE LIST

        //====> BARREL <====//
        //SELECT ALL BARREL TYPES
        Criteria criteria6 = session.createCriteria(Barrel.class);
        criteria6.addOrder(Order.asc("chamber"));
        List<Barrel> boreList = criteria6.list(); //BARREL TYPE LIST

        //====> ACTION <====//
        //SELECT ALL ACTION TYPES
        Criteria criteria7 = session.createCriteria(Gun_Action.class);
        criteria7.addOrder(Order.asc("bolt"));
        List<Gun_Action> actionList = criteria7.list(); //ACTION TYPE LIST

        //====> IF BULLET TYPE <====//
        //SELECT ALL BULLET TYPES
        Criteria criteria8 = session.createCriteria(Bullet_type.class);
        criteria8.addOrder(Order.asc("id"));
        List<Bullet_type> btypeList = criteria8.list(); //BULLET TYPE LIST

        //====> CONDITION <====//
        //SELECT ALL CONDITIONS
        Criteria criteria9 = session.createCriteria(Gun_condition.class);
        criteria9.addOrder(Order.asc("id"));
        List<Gun_condition> conditionList = criteria9.list(); //CONDITION LIST

        //====> PERSON TYPE <====//
        //SELECT ALL PERSON TYPES
        Criteria criteria10 = session.createCriteria(Person.class);
        criteria10.addOrder(Order.asc("id"));
        List<Person> personList = criteria10.list(); //PERSON TYPE LIST

        //JSON OBJECT {[],[],[],[],[]}
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("BrandList", gson.toJsonTree(BrandList));
        jsonObject.add("modelList", gson.toJsonTree(modelList));
        jsonObject.add("magList", gson.toJsonTree(magList));
        jsonObject.add("stockList", gson.toJsonTree(stockList));
        jsonObject.add("muzzList", gson.toJsonTree(muzzList));
        jsonObject.add("boreList", gson.toJsonTree(boreList));
        jsonObject.add("actionList", gson.toJsonTree(actionList));
        jsonObject.add("btypeList", gson.toJsonTree(btypeList));
        jsonObject.add("personList", gson.toJsonTree(personList));
        jsonObject.add("conditionList", gson.toJsonTree(conditionList));

        //SEND RESPONSE
        res.setContentType("application/json");
        res.getWriter().write(gson.toJson(jsonObject));

        //SESSION CLOSE
        session.close();

    }

}
