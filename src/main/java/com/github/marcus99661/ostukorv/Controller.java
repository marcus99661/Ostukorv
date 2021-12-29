package com.github.marcus99661.ostukorv;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@org.springframework.stereotype.Controller
public class Controller {

    static Algorithm algorithm = Algorithm.HMAC256("6643110C5628FFF59EDF76B82D5BF573BF800F16A4D65DEB1E5D6F1A46296D0B");

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
     */
    @GetMapping(value = "/login")
    public String login(@RequestParam(required = false) String error, Model model) {
        if (!Objects.isNull(error)) {
            // Kontrollib, et error on olemas ja ei lubaks SSTI
            model.addAttribute("error", error);
        }
        model.addAttribute("page", "login");
        return "main";
    }



    /**
     * first_name
     * last_name
     * email
     * email_exists
     * password
     * weak
     * compassword
     * not_same
     */
    @GetMapping(value = "/register")
    public String register(@RequestParam(required = false) String error, Model model) {
        if (!Objects.isNull(error)) {
            model.addAttribute("error", error);
        }
        model.addAttribute("page", "register");
        return "main";
    }


    @RequestMapping(value = "/sendRegister", method = {RequestMethod.POST})
    @ResponseBody
    public void sendRegister(@RequestParam String first_name, @RequestParam String last_name, @RequestParam String email, @RequestParam String password, @RequestParam String compassword, HttpServletResponse response) throws IOException {
        /**
         * Check if email is already registered
         * Check if email is valid email
         */
        /**
         * first_name
         * last_name
         * email
         * email_exists
         * password
         * weak
         * compassword
         * not_same
         */


        if (first_name.isBlank()) {
            response.sendRedirect("/register?error=first_name");
            return;
        }
        if (last_name.isBlank()) {
            response.sendRedirect("/register?error=last_name");
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
        if (compassword.isBlank()) {
            response.sendRedirect("/register?error=compassword");
            return;
        }
        if (password.length() < 6) {
            response.sendRedirect("/register?error=weak");
            return;
        }
        if (!password.equals(compassword)) {
            response.sendRedirect("/register?error=not_same");
            return;
        }
        if (repository.findByEmail(email).size() != 0) {
            response.sendRedirect("/register?error=email_exists");
            return;
        }




        List<Kasutaja> abc = repository.findByFirstName(first_name);

        if (Objects.isNull(abc) || abc.size() == 0) {
            // Account doesn't exist
            repository.save(new Kasutaja(first_name, last_name, email, password));
            response.sendRedirect("/");
            return;
        }

        response.sendRedirect("/register?error=exist");
        return;
    }

    @PostMapping("/sendLogin")
    public void sendLogin(@RequestParam String email, @RequestParam String password, HttpServletResponse response) throws IOException {
        List<Kasutaja> asd = repository.findByEmail(email);

        if (email.isBlank()) {
            response.sendRedirect("/login?error=email");
            return;
        }

        if (password.isBlank()) {
            response.sendRedirect("/login?error=password");
            return;
        }

        // if no account is with that name
        if (Objects.isNull(asd) || asd.size() == 0) {
            // send back to /login with ?error
            response.sendRedirect("/login?error=wrong");
            return;
        }

        if (asd.get(0).password.equals(password)) {
            // if password is correct
            String token = JWT.create()
                    .withSubject(email)
                    .sign(algorithm);
            response.addCookie(new Cookie("session", token));
            response.sendRedirect("/konto");
            return;
        }
        response.sendRedirect("/login?error=wrong");
    }

    @GetMapping("/konto")
    public String konto(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException {
        String token = getToken(request.getCookies());
        if (token.isBlank()) {
            response.sendRedirect("/login");
            return null;
        }
        model.addAttribute("page", "konto");

        model.addAttribute("username");
        model.addAttribute("picture");
        model.addAttribute("orderHistory");

        return "main";
    }

    public static String getToken(Cookie[] cookies) {
        String token = "";
        if (Objects.isNull(cookies)) {
            return token;
        }
        for (Cookie i : cookies) {
            if (i.getName().equalsIgnoreCase("session")) {
                token = i.getValue();
                break;
            }
        }
        return token;
    }

    public static boolean validatetoken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            String name = jwt.getSubject();
            return true;
        } catch (JWTVerificationException exception) {
            System.out.println("Error in validating method");
        }
        return false;
    }

    public static String getTokenSubject(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            String name = jwt.getSubject();
            return name;
        } catch (JWTVerificationException exception) {
            System.out.println("Error in validating method");
        }
        return null;
    }
}
