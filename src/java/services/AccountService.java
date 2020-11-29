package services;

import dataaccess.UserDB;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;

public class AccountService {
    
    public User login(String email, String password, String path) {
        UserDB userDB = new UserDB();
        
        try {
            User user = userDB.get(email);
            if (password.equals(user.getPassword())) {
                Logger.getLogger(AccountService.class.getName()).log(Level.INFO, "Successful Login by {0}", email);
                
                String to = user.getEmail();
                String subject = "Notes App Login";
                String template = path + "/emailtemplates/login.html";        
                
                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", user.getFirstName());
                tags.put("lastname", user.getLastName());
                tags.put("date", (new java.util.Date()).toString());
                GmailService.sendMail(to, subject, template, tags);
                //GmailService.sendMail(user.getEmail(), "Notes app login", "Hi " + user.getFirstName() + ", you logged in on " + (new Date()).toString(), false);
                return user;
            }
        } catch (Exception e) {
        }
        
        return null;
    }
    
    public void sendReset(String email, String path, String url) {
        UserDB userDB = new UserDB();
     
        try {
            User user = userDB.get(email);
            String uuid = UUID.randomUUID().toString();
            user.setResetPasswordUuid(uuid);
            userDB.update(user);
            
            String subject = "Notes App Login";
            String template = path + "/emailtemplates/resetpassword.html";
            String link = url + "?uuid=" + uuid;

            HashMap<String, String> tags = new HashMap<>();
            tags.put("firstname", user.getFirstName());
            tags.put("lastname", user.getLastName());
            tags.put("link", link);
            
            GmailService.sendMail(email, subject, template, tags);
            //GmailService.sendMail(user.getEmail(), "Notes app login", "Hi " + user.getFirstName() + ", you logged in on " + (new Date()).toString(), false);
        }catch(Exception e) {
            
        }
    }

       public String changePassword(String uuid, String password) {
       UserDB userDB = new UserDB();
       String confirm;
        try {
            User user = userDB.getByUUID(uuid);
            user.setPassword(password);
            user.setResetPasswordUuid(null);
            userDB.update(user);
            return confirm = "true";
        } catch (Exception ex) {
            return null;
        }
    }

}
