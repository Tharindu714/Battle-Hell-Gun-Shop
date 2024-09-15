package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.Purchase_item_DTO;
import dto.User_DTO;
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
@WebServlet(name = "loadPurchasedItems", urlPatterns = {"/loadPurchasedItems"})
public class loadPurchasedItems extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        Session session = HibernateUtil.getSessionFactory().openSession();
        HttpSession httpSession = req.getSession();
        Gson gson = new Gson();

        ArrayList<Purchase_item_DTO> Purchase_item_DTO_List = new ArrayList<>();

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
                    //Purchase Searching
                    Criteria criteria2 = session.createCriteria(Purchase.class);
                    criteria2.add(Restrictions.eq("user", user));

                    List<Purchase> PurchaseList = criteria2.list();

                    for (Purchase purchase : PurchaseList) {

                        //Purchase Items Searching
                        Criteria criteria3 = session.createCriteria(Purchase_items.class);
                        criteria3.add(Restrictions.eq("order", purchase));

                        List<Purchase_items> purchaseItemList = criteria3.list();

                        for (Purchase_items purchase_items : purchaseItemList) {

                            Purchase_item_DTO purchaseItemsDTO = new Purchase_item_DTO();

                            purchaseItemsDTO.setPurchase(purchase);
                            purchaseItemsDTO.setQty(purchase_items.getQty());
                            purchaseItemsDTO.setProduct(purchase_items.getProduct());
                            purchaseItemsDTO.setPurchase_status(purchase_items.getOrder_status());
                            System.out.println("Order Status: " + purchase_items.getOrder_status().getName());

                            Purchase_item_DTO_List.add(purchaseItemsDTO);
                        }

                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();

        }

        res.setContentType("application/json");
        res.getWriter().write(gson.toJson(Purchase_item_DTO_List));
        System.out.println("response sent");

    }
}
