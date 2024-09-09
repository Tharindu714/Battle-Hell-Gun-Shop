package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.Cart_DTO;
import dto.Response_DTO;
import dto.User_DTO;
import entity.Cart;
import entity.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import model.HibernateUtil;
import model.Validator;

/**
 *
 * @author ByteBigBoss
 */
@WebServlet(name = "SignIn", urlPatterns = {"/SignIn"})
public class SignIn extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        //RESPONSE OBJECT
        Response_DTO resDTO = new Response_DTO();

        //GSON BUILDER
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        //MAP REQUEST PARAMETER TO USER_DTO CLASS
        User_DTO userDTO = gson.fromJson(req.getReader(), User_DTO.class);

        //VALIDATE REQUEST PARAMETERS
        if (userDTO.getEmail().isEmpty()) {
            //EMAIL IS EMPTY
            resDTO.setContent("Please enter your Email");

        } else if (!Validator.VALIDATE_EMAIL(userDTO.getEmail())) {
            //EMAIL IS NOT VALID
            resDTO.setContent("Please enter a valid Email");

        } else if (userDTO.getPassword().isEmpty()) {
            //PASSWORD IS EMPTY
            resDTO.setContent("Please enter your Password");

        } else if (!Validator.VALIDATE_PASSWORD(userDTO.getPassword())) {
            //PASSWORD NOT VALID
            resDTO.setContent("Password must include at least one uppercase letter, number, "
                    + "special character, and be at least 8 characters long");

        } else {

            //FIND SESSION USER
            Session session = HibernateUtil.getSessionFactory().openSession();

            Criteria criteria1 = session.createCriteria(User.class);
            criteria1.add(Restrictions.eq("email", userDTO.getEmail()));
            criteria1.add(Restrictions.eq("password", userDTO.getPassword()));

            if (!criteria1.list().isEmpty()) {

                User user = (User) criteria1.list().get(0);

                if (!user.getVerification().equals("Verified")) {
                    //NOT VERIFIED
                    req.getSession().setAttribute("email", userDTO.getEmail());
                    resDTO.setContent("Unverified");

                } else {
                    //VERIVIED
                    userDTO.setFirst_name(user.getFirst_name());
                    userDTO.setLast_name(user.getLast_name());
                    userDTO.setPassword(null);
                    req.getSession().setAttribute("user", userDTO);

                    //TRANSFER SESSION CART TO DB CART
                    if (req.getSession().getAttribute("sessionCart") != null) {

                        ArrayList<Cart_DTO> sessionCart = (ArrayList<Cart_DTO>) req.getSession().getAttribute("sessionCart");

                        Criteria criteria2 = session.createCriteria(Cart.class);
                        criteria2.add(Restrictions.eq("user", user));
                        List<Cart> dbCart = criteria2.list();

                        if (dbCart.isEmpty()) {
                            //DB CART IS EMPTY
                            //ADD ALL SESSION CART ITEMS INTO DB CART                           
                            for (Cart_DTO cartDTO : sessionCart) {
                                Cart cart = new Cart();
                                cart.setProduct(cartDTO.getProduct());
                                cart.setQty(cartDTO.getQty());
                                cart.setUser(user);
                                session.save(cart);
                            }

                        } else {
                            //FOUND ITEMS IN DB CART
                            for (Cart_DTO cartDTO : sessionCart) {
                                
                                boolean isFoundInDBCart = false;
                                
                                for (Cart cart : dbCart) {

                                    if(cartDTO.getProduct().getId() == cart.getProduct().getId()){
                                        //SAME ITEM FOUND IN SESSION CART & DB CART
                                        isFoundInDBCart = true;
                                        
                                        if((cartDTO.getQty() + cart.getQty()) <= cart.getProduct().getQty()){
                                            //QUANTITY AVAILABLE
                                            cart.setQty(cartDTO.getQty()+cart.getQty());
                                            session.update(cart);
                                            
                                        }else{
                                            //QUANTITY NOT AVAILABLE
                                            //SET MAX AVAILABLE QTY
                                            cart.setQty(cart.getProduct().getQty());
                                            session.update(cart);
                                        }
                                        
                                    }
                                    
                                }
                                
                                if(!isFoundInDBCart){
                                    //NOT FOUND IN DB CART
                                    Cart cart = new Cart();
                                    cart.setProduct(cartDTO.getProduct()); //*
                                    cart.setQty(cartDTO.getQty());
                                    cart.setUser(user);
                                    session.save(cart);
                                }
                                
                            }

                        }

                        req.getSession().removeAttribute("sessionCart");
                        session.beginTransaction().commit();
                    }

                    resDTO.setSuccess(true);
                    resDTO.setContent("Sign In Success");

                }

            } else {
                resDTO.setContent("Invalid details! Please try again");
            }

        }

        res.setContentType("application/json");
        res.getWriter().write(gson.toJson(resDTO));

    }

}
