package com.github.marcus99661.ostukorv;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
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
@RequestMapping("/admin")
public class AdminController {

    static Algorithm algorithm = Algorithm.HMAC256("6643110C5628FFF59EDF76D82D5BF573BF800F16A4D65DFB1E5D6F1A46296D0B");

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MongoTemplate mt;

    @Autowired
    private ToodeRepository toodeRepository;

    public AdminController(MongoTemplate mt, AdminRepository repository, ToodeRepository toodeRepository) {
        this.mt = mt;
        this.adminRepository = repository;
        this.toodeRepository = toodeRepository;
    }

    @GetMapping("")
    public String adminIndex(@RequestParam(required = false) String error, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // if no session cookie redirect to /login
        System.out.println("SIIN OLEME");
        String token = getToken(request.getCookies());
        if (token.isBlank()) {
            response.sendRedirect("/admin/login");
            return "adminTooted";
        }
        // if session token then validate
        if (!validatetoken(token)) {
            response.sendRedirect("/admin/login");
            return "adminTooted";
        } else {
            List<Toode> tooted = toodeRepository.findAll();
            System.out.println(tooted);
            model.addAttribute("tooted", tooted);
            return "adminTooted";
        }
    }

    @GetMapping("/")
    public void adminIndex2(@RequestParam(required = false) String error, Model model, HttpServletResponse response) throws IOException {
        System.out.println("SIIN ME OLEME");
        response.sendRedirect("/admin");
        return;
    }


    @GetMapping("/login")
    public String adminLogin(@RequestParam(required = false) String error, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = getToken(request.getCookies());
        if (token.isBlank()) {
            // no cookie
            return "adminLogin";
        } else if (validatetoken(token)) {
            // if token is correct
            return "/admin";
        } else {
            // if token is not correct - remove cookie
            Cookie cookie = new Cookie("token", null);
            response.addCookie(cookie);
            return "adminLogin";
        }
    }

    @PostMapping("/sendLogin")
    public void adminSendLogin(@RequestParam String username, @RequestParam String password, HttpServletResponse response) throws IOException {
        System.out.println(username);
        System.out.println(password);

        List<Admin> asd = adminRepository.findByName(username);

        // if no account is with that name
        if (Objects.isNull(asd) || asd.size() == 0) {
            // send back to /admin/login with ?error
            response.sendRedirect("/admin/login?error=wrong");
            return;
        }

        if (asd.get(0).password.equals(password)) {
            // if password is correct
            String token = JWT.create()
                    .withSubject(username)
                    .sign(algorithm);
            response.addCookie(new Cookie("admin", token));
            response.sendRedirect("/admin");
            return;
        }
        response.sendRedirect("/admin");
    }







    public static String getToken(Cookie[] cookies) {
        String token = "";
        if (Objects.isNull(cookies)) {
            return token;
        }
        for (Cookie i : cookies) {
            if (i.getName().equalsIgnoreCase("admin")) {
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