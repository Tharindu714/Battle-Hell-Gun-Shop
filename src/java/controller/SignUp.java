package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.Response_DTO;
import dto.User_DTO;
import entity.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import model.Generate;
import model.HibernateUtil;
import model.Mail;
import model.Validator;

/**
 *
 * @author ByteBigBoss
 */
@WebServlet(name = "SignUp", urlPatterns = {"/SignUp"})
public class SignUp extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        //RESPONSE OBJECT
        Response_DTO resDTO = new Response_DTO();

        //GSON BUILDER: => EXCLUDE EXPOSE ANNOTATED FIELD'S FROM SERIALIZATION (RESPONSE)
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        //MAP REQUEST PARAMETER TO USER_DTO CLASS
       final User_DTO userDTO = gson.fromJson(req.getReader(), User_DTO.class);

        //VALIDATE PARAMETERS
        if (userDTO.getFirst_name().isEmpty()) {
            //FIRST NAME IS EMPTY
            resDTO.setContent("Please enter your First Name");

        } else if (userDTO.getLast_name().isEmpty()) {
            //LAST NAME IS EMPTY
            resDTO.setContent("Please enter your Last Name");

        } else if (userDTO.getEmail().isEmpty()) {
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

            //OPEN SESSION
            Session session = HibernateUtil.getSessionFactory().openSession();

            //CHECK USER EXIST OR NOT
            Criteria criteria1 = session.createCriteria(User.class);
            criteria1.add(Restrictions.eq("email", userDTO.getEmail()));

            if (!criteria1.list().isEmpty()) {
                resDTO.setContent("User with this Email already exists");
            } else {

                //GENERATE VERIFICATION CODE
                int code = Generate.RANDOM_VERIFICATION_CODE();

                //SET USER PROPERTIES
                final User user = new User();
                user.setEmail(userDTO.getEmail());
                user.setFirst_name(userDTO.getFirst_name());
                user.setLast_name(userDTO.getLast_name());
                user.setPassword(userDTO.getPassword());
                user.setVerification(String.valueOf(code));

               final String content = "<html lang=\"en\">\n"
                        + "<head>\n"
                        + "    <meta charset=\"UTF-8\">\n"
                        + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                        + "    <title>Verify Your Email</title>\n"
                        + "</head>\n"
                        + "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f9; margin: 0; padding: 0;\">\n"
                        + "    <table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width: 600px; margin: auto; background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\">\n"
                        + "        <tr>\n"
                        + "            <td style=\"text-align: center;\">\n"
                        + "                <h2 style=\"color: #333333;\">Verify Your Email</h2>\n"
                        + "                <p style=\"color: #666666;\">You are Joining with the biggest all island Gun store!</p>\n"
                        + "                <p style=\"color: #666666;\">Please use the verification code below to complete your registration:</p>\n"
                        + "                <h3 style=\"color: #007bff; font-size: 24px; margin: 20px 0;\">" + code + "</h3>\n"
                        + "                <p style=\"color: #666666;\">If you didn’t request this, you can safely ignore this email.</p>\n"
                        + "                <p style=\"color: #666666;\">Contact Us on Facebook <b style=\"color: #007bff;\">(Delta Codex Software Solutions)</b></p>\n"
                        + "            </td>\n"
                        + "        </tr>\n"
                        + "    </table>\n"
                        + "</body>\n"
                        + "</html>";

                //SEND VERIFICATION EMAIL
                Thread sendMailThread = new Thread() {
                    @Override
                    public void run() {
                        Mail.sendMail(userDTO.getEmail(), "Verification Battle Hell Account", content);

                    }

                };
                sendMailThread.start();

                //SAVE NEW USER TO RAM
                session.save(user);

                //ADD USER TO DATABASE
                session.beginTransaction().commit();

                //REGISTRATION COMPLETE
                resDTO.setSuccess(true);
                resDTO.setContent("Registration Complete. Please check your inbox for Verification Code!");

            }

            //END SESSION
            session.close();

        }

        //RETURN RESPONSE
        res.setContentType("application/json");
        res.getWriter().write(gson.toJson(resDTO));

    }

}
