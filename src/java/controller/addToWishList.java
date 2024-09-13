/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import dto.Response_DTO;
import dto.User_DTO;
import entity.Cart;
import entity.Product;
import entity.User;
import entity.Wishlist;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.HibernateUtil;
import model.Validator;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

@WebServlet(name = "addToWishList", urlPatterns = {"/addToWishList"})
public class addToWishList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        Session session = HibernateUtil.getSessionFactory().openSession(); //GET SESSION
        Transaction transaction = session.beginTransaction(); //GET TRANSACTION
        Gson gson = new Gson();
        Response_DTO resDTO = new Response_DTO();

        try {
            //GET REQUEST PARAMETERS
            String id = req.getParameter("id");

            //VALIDATIONS
            if (!Validator.VALIDATE_INTEGER(id)) {
                //PRODUCT NOT FOUND
                resDTO.setContent("Product Not Found");

            } else {
                int productId = Integer.parseInt(id);

                Product product = (Product) session.get(Product.class, productId);

                if (product != null) {
                    if (req.getSession().getAttribute("user") != null) {
            
                        User_DTO userDTO = (User_DTO) req.getSession().getAttribute("user");//GET USER

                        //FIND USER IN DB
                        Criteria criteria1 = session.createCriteria(User.class);
                        criteria1.add(Restrictions.eq("email", userDTO.getEmail()));

                        //CAST USER
                        User user = (User) criteria1.uniqueResult();

                        //CHECK IN DB CART
                        Criteria criteria2 = session.createCriteria(Wishlist.class);
                        criteria2.add(Restrictions.eq("user", user));
                        criteria2.add(Restrictions.eq("product", product));

                        //ITEM NOT FOUND IN CART
                        Wishlist wish = new Wishlist();
                        wish.setProduct(product);
                        wish.setUser(user);

                        session.save(wish);//SAVE TO RAM
                        transaction.commit();//SAVE TO DB

                        resDTO.setSuccess(true);
                        resDTO.setContent("Added to Wishlist");

                    }
                } else {
                    resDTO.setContent("This item already in wishlist");

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            resDTO.setContent("Unable to process you request");
        }
        session.close();
        res.setContentType("application/json");
        res.getWriter().write(gson.toJson(resDTO));

    }

}
