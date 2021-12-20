package com.github.marcus99661.ctf;


import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@org.springframework.stereotype.Controller
public class Controller {

    /**
     * Register lehe errorid ei tööta
     * /data lehel üleval riba parandada
     * kontrollida mis on admini pass
     */

    @Autowired
    public CustomerRepository repository;

    @Autowired
    private MongoTemplate mt;

    public Controller(MongoTemplate mt, CustomerRepository repository) {
        this.mt = mt;
        this.repository = repository;
    }

    @GetMapping(value = "/")
    public String index(@RequestParam(required = false) String error, Model model) {
        if (!Objects.isNull(error)) {
            model.addAttribute("error", error);
        }
        return "login";
    }

    @GetMapping(value = "/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping(value = "/data")
    public String data(Model model, @RequestParam(required = false) String check) {
        if (Objects.isNull(check)) {
            model.addAttribute("flag", "NO FLAG HERE");
        } else if (check.equals("0004738a-870e-4300-86d4-f68a89da0e96")) {
            model.addAttribute("flag", MainApplication.FLAG);
        } else {
            model.addAttribute("flag", "NO FLAG HERE");
        }
        return "data";
    }


    @RequestMapping(value = "/registerSend", method = {RequestMethod.POST})
    @ResponseBody
    public void register(@RequestParam String username, @RequestParam String password, HttpServletResponse response) throws IOException {

        if (username.isBlank()) {
            response.sendRedirect("/register?error=username");
            return;
        }
        if (password.isBlank()) {
            response.sendRedirect("/register?error=password");
            return;
        }
        List<Customer> abc = repository.findByName(username);
        System.out.println("abc length: " + abc.size());

        if (Objects.isNull(abc) || abc.size() == 0) {
            // Account doesn't exist
            repository.save(new Customer(username, password));
            response.sendRedirect("/");
            return;
        }

        response.sendRedirect("/register?error=exist");
        return;
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    @ResponseBody
    public void login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) throws IOException {
        // { '$match':{ 'author': 'Leo Tolstoy' } }, { '$sort':{ 'title': 1} }

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
        System.out.println(str);
        Document bsonCmd = Document.parse(query);
        Document result = this.mt.executeCommand(bsonCmd);
        System.out.println(result);
        Document cursor = (Document) result.get("cursor");
        List<Document> docs = (List<Document>) cursor.get("firstBatch");

        if (Objects.isNull(docs) || docs.size() == 0) {
            System.out.println("User doesn't exist");
            response.sendRedirect("/?error=wrong");
            return;
        }
        System.out.println(docs.get(0).get("name"));
        System.out.println(docs.get(0).get("password"));
        if (docs.get(0).get("name").equals("admin")) {
            response.sendRedirect("/data?check=0004738a-870e-4300-86d4-f68a89da0e96");
            return;
        }
        response.sendRedirect("/data");
        return;
    }

    /**
     *
     * EEMALDADA
     *
     */

    @GetMapping("/all")
    public String all() {
        String a = "";
        for (Customer i : repository.findAll()) {
            a += i.toString() + "       ";
        }
        return a;
    }
}
