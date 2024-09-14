package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.User_DTO;
import entity.Address;
import entity.City;
import entity.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.HibernateUtil;
import model.Validator;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "UpdateProfile", urlPatterns = {"/UpdateProfile"})
public class UpdateProfile extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new Gson();

        JsonObject requestJsonObject = gson.fromJson(request.getReader(), JsonObject.class);

        JsonObject responseJsonObject = new JsonObject();
        responseJsonObject.addProperty("success", true);

        HttpSession httpSession = request.getSession();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String cityId = requestJsonObject.get("cityId").getAsString();
        String address1 = requestJsonObject.get("address1").getAsString();
        String address2 = requestJsonObject.get("address2").getAsString();
        String postalCode = requestJsonObject.get("postalCode").getAsString();
        String mobile = requestJsonObject.get("mobile").getAsString();
//        String first_name = requestJsonObject.get("first_name").getAsString();
//        String last_name = requestJsonObject.get("last_name").getAsString();

        if (httpSession.getAttribute("user") != null) {
            //user signed in

            //get user from db
            User_DTO user_DTO = (User_DTO) httpSession.getAttribute("user");
            Criteria criteria1 = session.createCriteria(User.class);
            criteria1.add(Restrictions.eq("email", user_DTO.getEmail()));
            User user = (User) criteria1.uniqueResult();

            //create new address
            if (!Validator.VALIDATE_INTEGER(cityId)) {
                responseJsonObject.addProperty("message", "Invalid City");

            } else {
                //check city from db

                Criteria criteria3 = session.createCriteria(City.class);
                criteria3.add(Restrictions.eq("id", Integer.parseInt(cityId)));

                if (criteria3.list().isEmpty()) {
                    responseJsonObject.addProperty("message", "Invalid city selected");

                } else {
                    //city found
                    City city = (City) criteria3.list().get(0);

                    if (address1.isEmpty()) {
                        responseJsonObject.addProperty("message", "Please fill address line 1");

                    } else if (address2.isEmpty()) {
                        responseJsonObject.addProperty("message", "Please fill address line 2");

                    } else if (postalCode.isEmpty()) {
                        responseJsonObject.addProperty("message", "Please fill postal code");

                    } else if (postalCode.length() != 5) {
                        responseJsonObject.addProperty("message", "Invalid postal code");

                    } else if (!Validator.VALIDATE_INTEGER(postalCode)) {
                        responseJsonObject.addProperty("message", "Invalid postal code");

                    } else if (mobile.isEmpty()) {
                        responseJsonObject.addProperty("message", "Please fill mobile number");

                    } else if (!Validator.VALIDATE_MOBILE(mobile)) {
                        responseJsonObject.addProperty("message", "Invalid mobile number");

                    } else {
                        //create new address

                        Address address = new Address();
                        address.setCity(city);
                        address.setUser(user);

                        address.setLine1(address1);
                        address.setLine2(address2);
                        address.setMobile(mobile);
                        address.setPostal_code(postalCode);

                        address.setFirst_name("Not Set");
                        address.setLast_name("Not Set");
                        session.save(address);
                        transaction.commit();
                    }
                }
            }

        } else {
            //user not signed in
            responseJsonObject.addProperty("message", "User not signed in");
        }

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(responseJsonObject));

    }

}
