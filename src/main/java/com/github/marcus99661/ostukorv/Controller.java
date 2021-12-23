package com.github.marcus99661.ostukorv;


import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    public KasutajaRepository repository;

    @Autowired
    private MongoTemplate mt;

    public Controller(MongoTemplate mt, KasutajaRepository repository) {
        this.mt = mt;
        this.repository = repository;
    }

    /**
     * Soodustused võtab andmebaasist
     *
     * Esilehe tooted (hind, allahindlus, pilti file) võtab andmebaasist
     */

    // Main leht
    @GetMapping(value = "/")
    public String index(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("page", "en");
        return "main";
    }


    /**
     * Errorid:
     * Vale kasutajanimi/parool.
     * Kasutajanime lahter on tühi.
     * Parooli lahter on tühi
     *
     *
     */
    @GetMapping(value = "/login")
    @ResponseBody
    public String login(@RequestParam(required = false) String error, Model model) {
        if (!Objects.isNull(error)) {
            model.addAttribute("error", error);
        }
        return "login";
    }

    /**
     * Errorid:
     * Kasutaja on juba olemas.
     * Kasutajanime lahter on tühi.
     * Emaili lahter on tühi.
     * Parooli lahter on tühi
     * Nõrk salasõna
     *
     */
    @GetMapping(value = "/register")
    public String registerPage(@RequestParam(required = false) String error, Model model) {
        if (!Objects.isNull(error)) {
            model.addAttribute("error", error);
        }
        return "register";
    }


    @RequestMapping(value = "/registerSend", method = {RequestMethod.POST})
    @ResponseBody
    public void register(@RequestParam String username, @RequestParam String email, @RequestParam String password, HttpServletResponse response) throws IOException {
        /**
         * Check if username is already registered
         * Check if email is already registered
         * Check if email is valid email
         */




        if (username.isBlank()) {
            response.sendRedirect("/register?error=username");
            return;
        }
        if (email.isBlank()) {
            response.sendRedirect("/register?error=email");
            return;
        }
        if (password.isBlank()) {
            response.sendRedirect("/register?error=password");
            return;
        }
        List<Kasutaja> abc = repository.findByName(username);

        if (Objects.isNull(abc) || abc.size() == 0) {
            // Account doesn't exist
            repository.save(new Kasutaja(username, email, password));
            response.sendRedirect("/");
            return;
        }

        response.sendRedirect("/register?error=exist");
        return;
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    @ResponseBody
    public void login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) throws IOException {

        if (username.isBlank()) {
            response.sendRedirect("/?error=username");
            return;
        }
        if (password.isBlank()) {
            response.sendRedirect("/?error=password");
            return;
        }

        String str = "{ '$match':{ 'name': '" + username + "', 'password': '" + password + "'} }";
        String query = "{ 'aggregate': 'customer', 'pipeline': [" + str + "], 'cursor': { } }";
        //System.out.println(str);
        Document bsonCmd = Document.parse(query);
        Document result = this.mt.executeCommand(bsonCmd);
        //System.out.println(result);
        Document cursor = (Document) result.get("cursor");
        List<Document> docs = (List<Document>) cursor.get("firstBatch");

        if (Objects.isNull(docs) || docs.size() == 0) {
            System.out.println("User doesn't exist");
            response.sendRedirect("/?error=wrong");
            return;
        }
        //System.out.println(docs.get(0).get("name"));
        //System.out.println(docs.get(0).get("password"));
        if (docs.get(0).get("name").equals("admin")) {
            response.sendRedirect("/data?check=0004738a-870e-4300-86d4-f68a89da0e96");
            return;
        }
        response.sendRedirect("/data");
        return;
    }
}
