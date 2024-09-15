/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dto.User_DTO;
import dto.purchase_dto;
import entity.Address;
import entity.Purchase;
import entity.Purchase_items;
import entity.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Tharindu
 */
@WebServlet(name = "loadCusDetails", urlPatterns = {"/loadCusDetails"})
public class loadCusDetails extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Session session = HibernateUtil.getSessionFactory().openSession();
        HttpSession httpSession = request.getSession();
        Gson gson = new Gson();

        ArrayList<purchase_dto> Purchase_DTO_List = new ArrayList<>();

        try {

            if (httpSession.getAttribute("user") != null) {
                //USER FOUND => DB CART
                User_DTO userDTO = (User_DTO) httpSession.getAttribute("user"); //CAST ATTRIBUTE TO USER DTO 

                //FIND USER IN DB
                Criteria criteria1 = session.createCriteria(User.class);
                criteria1.add(Restrictions.eq("email", userDTO.getEmail()));

                //CAST USER
                User user = (User) criteria1.uniqueResult();

                if (user != null) {
                    //Address Searching
                    Criteria criteria2 = session.createCriteria(Address.class);
                    criteria2.add(Restrictions.eq("user", user));

                    List<Address> AddressList = criteria2.list();

                    for (Address address : AddressList) {

                        purchase_dto purchaseDTO = new purchase_dto();

                        purchaseDTO.setAddress(address);
                        purchaseDTO.setUser(user);
//                            purchaseDTO.setDatetime(datetime);

                        Purchase_DTO_List.add(purchaseDTO);
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();

        }

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(Purchase_DTO_List));
        System.out.println("response sent");

    }

}
