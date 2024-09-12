package controller;

import com.google.gson.Gson;
import dto.Response_DTO;
import dto.User_DTO;
import entity.Barrel;
import entity.Brand;
import entity.Bullet_type;
import entity.Gun_Action;
import entity.Gun_condition;
import entity.Gun_status;
import entity.Magazine;
import entity.Model;
import entity.Muzzle;
import entity.Person;
import entity.Product;
import entity.Stock;
import entity.User;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import model.HibernateUtil;
import model.Validator;

@MultipartConfig
@WebServlet(name = "ProductListing", urlPatterns = {"/ProductListing"})
public class ProductListing extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        //RESPONSE OBJECT
        Response_DTO resDTO = new Response_DTO();

        //GSON OBJECT
        Gson gson = new Gson();

        //BASIC DETAILS
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String price = req.getParameter("price");
        String qty = req.getParameter("qty");

        //SELECTED ID'S
        String categoryId = req.getParameter("categoryId");
        String modelId = req.getParameter("modelId");

        String muzzleId = req.getParameter("muzzleId");
        String boreId = req.getParameter("boreId");

        String magId = req.getParameter("magId");
        String stockId = req.getParameter("stockId");

        String actionID = req.getParameter("actionID");
        String bulletTypeId = req.getParameter("bulletTypeId");

        String conditionId = req.getParameter("conditionId");

        String personId = req.getParameter("personId");

        //PRODUCT IMAGES
        Part img1 = req.getPart("img1");
        Part img2 = req.getPart("img2");
        Part img3 = req.getPart("img3");
        Part img4 = req.getPart("img4");

        //OPEN SESSION
        Session session = HibernateUtil.getSessionFactory().openSession();

        //VALIDATE FIELDS
        if (!Validator.VALIDATE_INTEGER(categoryId)) {
            resDTO.setContent("Invalid Category");

        } else if (!Validator.VALIDATE_INTEGER(modelId)) {
            resDTO.setContent("Invalid Model");

        } else if (!Validator.VALIDATE_INTEGER(magId)) {
            resDTO.setContent("Invalid Magazine Type");

        } else if (!Validator.VALIDATE_INTEGER(stockId)) {
            resDTO.setContent("Invalid Stock Type");

        } else if (!Validator.VALIDATE_INTEGER(muzzleId)) {
            resDTO.setContent("Invalid Muzzle Type");

        } else if (!Validator.VALIDATE_INTEGER(boreId)) {
            resDTO.setContent("Invalid Barrel Type");

        } else if (!Validator.VALIDATE_INTEGER(actionID)) {
            resDTO.setContent("Invalid Action Type");

        } else if (!Validator.VALIDATE_INTEGER(bulletTypeId)) {
            resDTO.setContent("Invalid Bullet Type");

        } else if (!Validator.VALIDATE_INTEGER(personId)) {
            resDTO.setContent("Invalid Person Type");

        } else if (!Validator.VALIDATE_INTEGER(conditionId)) {
            resDTO.setContent("Invalid Condition");

        } else if (title.isEmpty()) {
            resDTO.setContent("Please fill Title");

        } else if (description.isEmpty()) {
            resDTO.setContent("Please fill Description");

        } else if (price.isEmpty()) {
            resDTO.setContent("Please fill Price");

        } else if (!Validator.VALIDATE_DOUBLE(price)) {
            resDTO.setContent("Invalid price");

        } else if (Double.parseDouble(price) <= 0) {
            resDTO.setContent("Price must be greater than 0");

        } else if (qty.isEmpty()) {
            resDTO.setContent("Please fill Quantity");

        } else if (!Validator.VALIDATE_INTEGER(qty)) {
            resDTO.setContent("Invalid Quantity");

        } else if (Integer.parseInt(qty) <= 0) {
            resDTO.setContent("Quantity must be greater than 0");

        } else if (img1.getSubmittedFileName() == null) {
            resDTO.setContent("Please upload Image 1");

        } else if (img2.getSubmittedFileName() == null) {
            resDTO.setContent("Please upload Image 2");

        } else if (img3.getSubmittedFileName() == null) {
            resDTO.setContent("Please upload Image 3");

        } else if (img4.getSubmittedFileName() == null) {
            resDTO.setContent("Please upload Image 4");

        } else {

            //FIND CATEGORY BY REQUESTED CATEGORY ID
            Brand brand = (Brand) session.load(Brand.class, Integer.parseInt(categoryId)); //IF session.load() NOT WORKS THEN USE session.get()
            if (brand == null) {
                //CATEGORY NOT IN DB
                resDTO.setContent("Please select a valid Category");

            } else {

                //FIND MODEL BY REQUESTED MODEL ID
                Model model = (Model) session.load(Model.class, Integer.parseInt(modelId));
                if (model == null) {
                    //MODEL NOT IN DB
                    resDTO.setContent("Please select a valid Model");

                } else {

                    //COMPARE IS REQUESTED MODEL IN CATEGORY
                    if (model.getBrand().getId() != brand.getId()) {
                        //COMPARE FAILD
                        resDTO.setContent("Please select a valid Model");

                    } else {

                        //FIND MAGAZINE BY REQUESTED MAGAZINE ID
                        Magazine mag = (Magazine) session.load(Magazine.class, Integer.parseInt(magId));
                        if (mag == null) {
                            //MAGAZINE NOT IN DB
                            resDTO.setContent("Please select a valid Magazine Type");

                        } else {

                            //FIND STOCK BY REQUESTED STOCK ID
                            Stock stock = (Stock) session.load(Stock.class, Integer.parseInt(stockId));
                            if (stock == null) {
                                //STOCK NOT IN DB
                                resDTO.setContent("Please select a valid Stock Type");

                            } else {

                                //FIND MUZZLE BY REQUESTED MUZZLE ID
                                Muzzle muzz = (Muzzle) session.load(Muzzle.class, Integer.parseInt(muzzleId));
                                if (muzz == null) {
                                    //MUZZLE NOT IN DB
                                    resDTO.setContent("Please select a valid muzzle Type");

                                } else {

                                    //FIND BARREL BY REQUESTED BARREL ID
                                    Barrel bore = (Barrel) session.load(Barrel.class, Integer.parseInt(boreId));
                                    if (bore == null) {
                                        //BARREL NOT IN DB
                                        resDTO.setContent("Please select a valid Barrel Type");

                                    } else {

                                        //FIND ACTION BY REQUESTED ACTION ID
                                        Gun_Action action = (Gun_Action) session.load(Gun_Action.class, Integer.parseInt(actionID));
                                        if (action == null) {
                                            //ACTION NOT IN DB
                                            resDTO.setContent("Please select a valid Action Type");

                                        } else {

                                            //FIND AMMO TYPE BY REQUESTED AMMO TYPE ID
                                            Bullet_type ammo = (Bullet_type) session.load(Bullet_type.class, Integer.parseInt(bulletTypeId));
                                            if (ammo == null) {
                                                //AMMO TYPE NOT IN DB
                                                resDTO.setContent("Please select a valid Bullet Type");

                                            } else {

                                                //FIND PRODUCT CONDITION BY REQUESTED PRODUCT CONDITION ID
                                                Gun_condition condition = (Gun_condition) session.load(Gun_condition.class, Integer.parseInt(conditionId));
                                                if (condition == null) {
                                                    //PRODUCT CONDITION NOT IN DB
                                                    resDTO.setContent("Please select a valid condition Type");

                                                } else {

                                                    //FIND PERSON BY REQUESTED PERSON ID
                                                    Person person = (Person) session.load(Person.class, Integer.parseInt(personId));
                                                    if (person == null) {
                                                        //PERSON NOT IN DB
                                                        resDTO.setContent("Please select a valid Person Type");

                                                    } else {

                                                        //====> ALL VALIDATED [0]=> [28::VALIDATES] <====//
                                                        Product product = new Product();
                                                        //FIELDS
                                                        product.setTitle(title);
                                                        product.setDescription(description);
                                                        product.setPrice(Double.parseDouble(price));
                                                        product.setQty(Integer.parseInt(qty));
                                                        product.setReg_date(new Date());

                                                        //JOINS
                                                        product.setGun_condition(condition);
                                                        product.setModel(model);
                                                        product.setStock(stock);
                                                        product.setBarrel(bore);
                                                        product.setAction(action);
                                                        product.setBullet_type(ammo);
                                                        product.setPerson(person);

                                                        //APPROVED PRODUCT BY ADMIN == Active != Inactive
                                                        //===> GET ACTIVE STATUS <===//
                                                        Gun_status product_Status = (Gun_status) session.load(Gun_status.class, 1);
                                                        product.setGun_status(product_Status);

                                                        //*******************************************//
                                                        //GET SESSION USER
                                                        User_DTO userDTO = (User_DTO) req.getSession().getAttribute("user");
                                                        //SEARCH SESSION USER IN DB
                                                        Criteria criteria1 = session.createCriteria(User.class);
                                                        criteria1.add(Restrictions.eq("email", userDTO.getEmail()));
                                                        //CAST USER FROM SEARCH
                                                        User user = (User) criteria1.uniqueResult();
                                                        product.setUser(user);
                                                        //*******************************************//

                                                        //SAVE NEW PRODUCT TO RAM
                                                        int pid = (int) session.save(product); // RETURN AUTO INCREMENT ID AS SERIALIZABLE ==> CAST INTO INT

                                                        //ADD PRODUCT TO DATABASE
                                                        session.beginTransaction().commit();

                                                        //APPLICATION PATH
                                                        String applicationPath = req.getServletContext().getRealPath("");
                                                        String newAplicationPath = applicationPath.replace("build" + File.separator + "web", "web");

                                                        //PRODUCT FOLDER
                                                        File productFolder = new File(newAplicationPath + File.separator + "product-images" + File.separator + pid);
                                                        productFolder.mkdir();

                                                        //FILE 1
                                                        File file1 = new File(productFolder, "image1.png");// CREATE NEW FILE (IMAGE)
                                                        InputStream inputStream1 = img1.getInputStream(); //IMAGE 1 STREAM
                                                        Files.copy(inputStream1, file1.toPath(), StandardCopyOption.REPLACE_EXISTING); // COPY IMAGE

                                                        //FILE 2
                                                        File file2 = new File(productFolder, "image2.png");// CREATE NEW FILE (IMAGE)
                                                        InputStream inputStream2 = img2.getInputStream(); //IMAGE 2 STREAM
                                                        Files.copy(inputStream2, file2.toPath(), StandardCopyOption.REPLACE_EXISTING); // COPY IMAGE

                                                        //FILE 3
                                                        File file3 = new File(productFolder, "image3.png");// CREATE NEW FILE (IMAGE)
                                                        InputStream inputStream3 = img3.getInputStream(); //IMAGE 3 STREAM
                                                        Files.copy(inputStream3, file3.toPath(), StandardCopyOption.REPLACE_EXISTING); // COPY IMAGE

                                                        //FILE 4
                                                        File file4 = new File(productFolder, "image4.png");// CREATE NEW FILE (IMAGE)
                                                        InputStream inputStream4 = img4.getInputStream(); //IMAGE 4 STREAM
                                                        Files.copy(inputStream4, file4.toPath(), StandardCopyOption.REPLACE_EXISTING); // COPY IMAGE

                                                        //PRODUCT LISTING COMPLETE
                                                        resDTO.setSuccess(true);
                                                        resDTO.setContent("New Product Added");
                                                    }

                                                }
                                            }
                                        }

                                    }

                                }

                            }
                        }
                    }
                }
            }
        }

        //RETURN RESPONSE
        res.setContentType("application/json");
        res.getWriter().write(gson.toJson(resDTO));

        //END SESSION
        session.close();

    }

}
