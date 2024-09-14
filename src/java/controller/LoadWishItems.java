package controller;

import com.google.gson.Gson;
import dto.User_DTO;
import dto.Wish_DTO;
import entity.Product;
import entity.User;
import entity.Wishlist;
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
@WebServlet(name = "LoadWishItems", urlPatterns = {"/LoadWishItems"})
public class LoadWishItems extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        Session session = HibernateUtil.getSessionFactory().openSession();
        HttpSession httpSession = req.getSession();
        Gson gson = new Gson();
        ArrayList<Wish_DTO> Wish_DTO_List = new ArrayList<>();

        try {

            if (httpSession.getAttribute("user") != null) {
                //USER FOUND => DB CART
                User_DTO userDTO = (User_DTO) httpSession.getAttribute("user"); //CAST ATTRIBUTE TO USER DTO 

                //FIND USER IN DB
                Criteria criteria1 = session.createCriteria(User.class);
                criteria1.add(Restrictions.eq("email", userDTO.getEmail()));

                //CAST USER
                User user = (User) criteria1.uniqueResult();

                //DB SEARCH
                Criteria criteria2 = session.createCriteria(Wishlist.class);
                criteria2.add(Restrictions.eq("user", user));

                //ITEM LIST
                List<Wishlist> wishList = criteria2.list();

                for (Wishlist wish : wishList) {

                    Wish_DTO wishDTO = new Wish_DTO();

                    Product product = wish.getProduct();
                    product.setUser(null);
                    wishDTO.setProduct(product);

                    Wish_DTO_List.add(wishDTO);

                }
            }else{
                res.sendRedirect("index.html");
               System.out.println("User must Sign in"); 
               
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.close();
        res.setContentType("application/json");
        res.getWriter().write(gson.toJson(Wish_DTO_List));
        System.out.println("response sent");

    }

}
