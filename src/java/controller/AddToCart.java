package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.Cart_DTO;
import dto.Response_DTO;
import dto.User_DTO;
import entity.Cart;
import entity.Product;
import entity.User;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import model.HibernateUtil;
import model.Validator;

@WebServlet(name = "AddToCart", urlPatterns = {"/AddToCart"})
public class AddToCart extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        Session session = HibernateUtil.getSessionFactory().openSession(); //GET SESSION
        Transaction transaction = session.beginTransaction(); //GET TRANSACTION
        Gson gson = new Gson();
        Response_DTO resDTO = new Response_DTO();

        try {
            //GET REQUEST PARAMETERS
            String id = req.getParameter("id");
            String qty = req.getParameter("qty");

            //VALIDATIONS
            if (!Validator.VALIDATE_INTEGER(id)) {
                //PRODUCT NOT FOUND
                resDTO.setContent("Product Not Found");

            } else if (!Validator.VALIDATE_INTEGER(qty)) {
                //INVALID QUANTITY
                resDTO.setContent("Invalid Quantity");
            } else {

                int productId = Integer.parseInt(id);
                int productQty = Integer.parseInt(qty);

                if (productQty <= 0) {
                    //QUANTITY MUST BE GREATER THAN 0
                    resDTO.setContent("Quantity must be greater than 0");

                } else {
                    //QUANTITY IS GREATER THAN 0

                    Product product = (Product) session.get(Product.class, productId);

                    if (product != null) {
                        //PRODUCT FOUND

                        if (req.getSession().getAttribute("user") != null) {
                            //DB CART
                            User_DTO userDTO = (User_DTO) req.getSession().getAttribute("user");//GET USER

                            //FIND USER IN DB
                            Criteria criteria1 = session.createCriteria(User.class);
                            criteria1.add(Restrictions.eq("email", userDTO.getEmail()));

                            //CAST USER
                            User user = (User) criteria1.uniqueResult();

                            //CHECK IN DB CART
                            Criteria criteria2 = session.createCriteria(Cart.class);
                            criteria2.add(Restrictions.eq("user", user));
                            criteria2.add(Restrictions.eq("product", product));

                            if (criteria2.list().isEmpty()) {
                                //ITEM NOT FOUND IN CART

                                if (productQty <= product.getQty()) {
                                    //ADD PRODUCT INTO CART

                                    Cart cart = new Cart();
                                    cart.setProduct(product);
                                    cart.setQty(productQty);
                                    cart.setUser(user);

                                    session.save(cart);//SAVE TO RAM
                                    transaction.commit();//SAVE TO DB

                                    resDTO.setSuccess(true);
                                    resDTO.setContent("Cart Item Addes");

                                } else {
                                    //QUNITY NOT AVAILABLE
                                    resDTO.setContent("Quantity Not Available");
                                }

                            } else {
                                //ITEM ALREADY FOUND IN CART

                                Cart cartItem = (Cart) criteria2.uniqueResult();

                                //ADD NEW QUANITY TO EXISTING CART ITEM & CHECK TOTATL QUANTIY EXISTS IN PRODUCT
                                if ((cartItem.getQty() + productQty) <= product.getQty()) {
                                    //QUANTITY AVAILABLE TO UPDATE
                                    cartItem.setQty(cartItem.getQty() + productQty);

                                    session.update(cartItem); //SAVE UPDATE IN RAM
                                    transaction.commit(); //UPDATE DB
                                    resDTO.setSuccess(true);
                                    resDTO.setContent("Quantity updated");

                                } else {
                                    //CAN'T UPDATE YOUR CART. QUANTITY NOT AVAILABLE 
                                    resDTO.setContent("Quantity Not Available");
                                }

                            }

                        } else {
                            //SESSION CART

                            HttpSession httpSession = req.getSession();

                            if (httpSession.getAttribute("sessionCart") != null) {
                                //SESSION CART FOUND

                                ArrayList<Cart_DTO> sessionCart = (ArrayList<Cart_DTO>) httpSession.getAttribute("sessionCart");

                                Cart_DTO foundCartDTO = null;
                                for (Cart_DTO cartDTO : sessionCart) {
                                    if (cartDTO.getProduct().getId() == product.getId()) {
                                        foundCartDTO = cartDTO;
                                        break;
                                    }
                                }

                                if (foundCartDTO != null) {
                                    //PRODUCT FOUND

                                    if ((foundCartDTO.getQty() + productQty) <= product.getQty()) {
                                        //UPDATE QUANTITY
                                        foundCartDTO.setQty(foundCartDTO.getQty() + productQty);
                                        resDTO.setSuccess(true);
                                        resDTO.setContent("Product quantity updated");
                                    } else {
                                        //QUANTITY NOT AVAILABLE
                                        resDTO.setContent("Quantity Not Available");

                                    }

                                } else {
                                    //PRODUCT NOT FOUND

                                    if (productQty <= product.getQty()) {
                                        //ADD TO SESSION CART
                                        Cart_DTO cartDTO = new Cart_DTO();
                                        cartDTO.setProduct(product);
                                        cartDTO.setQty(productQty);
                                        sessionCart.add(cartDTO);
                                        resDTO.setSuccess(true);
                                        resDTO.setContent("Product added to cart");
                                        System.out.println(cartDTO.getProduct().getTitle());

                                    } else {
                                        //QUANTITY NOT AVAILABLE
                                        resDTO.setContent("Quantity Not Available");
                                    }

                                }

                            } else {
                                //SESSION CART NOT FOUND
                                if (productQty <= product.getQty()) {
                                    //ADD TO SESSION CART
                                    ArrayList<Cart_DTO> sessionCart = new ArrayList<>();

                                    Cart_DTO cartDTO = new Cart_DTO();
                                    cartDTO.setProduct(product);
                                    cartDTO.setQty(productQty);
                                    sessionCart.add(cartDTO);

                                    req.getSession().setAttribute("sessionCart", sessionCart);

                                    resDTO.setSuccess(true);
                                    resDTO.setContent("Product added to Session Cart");
                                    System.out.println(cartDTO.getProduct().getTitle());
                                } else {
                                    //QUANTITY NOT AVALIALE
                                    resDTO.setContent("Not enough stock");
                                }
                            }

                        }

                    } else {
                        //PRODUCT NOT FOUND
                        resDTO.setContent("Product not found");
                    }
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
