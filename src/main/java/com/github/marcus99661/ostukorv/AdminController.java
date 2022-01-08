package com.github.marcus99661.ostukorv;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.marcus99661.ostukorv.Repository.AdminRepository;
import com.github.marcus99661.ostukorv.Repository.PiltRepository;
import com.github.marcus99661.ostukorv.Repository.ToodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.apache.commons.codec.binary.Hex;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.*;

@org.springframework.stereotype.Controller
@RequestMapping("/admin")
public class AdminController {

    static Algorithm algorithm = Algorithm.HMAC256("5543110C5628FFF59EDF76D82D5BF573BF800F16A4D65DFB1E5D6F1A46296D0B");

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MongoTemplate mt;

    @Autowired
    private ToodeRepository toodeRepository;

    private PiltService pildiTeenus = new PiltService();

    //@Autowired
    //private PiltRepository piltRepository;

    public AdminController(MongoTemplate mt, AdminRepository repository, ToodeRepository toodeRepository, PiltRepository piltRepository) {
        this.mt = mt;
        this.adminRepository = repository;
        this.toodeRepository = toodeRepository;
        //this.piltRepository = piltRepository;
        pildiTeenus.photoRepo = piltRepository;
    }

    @GetMapping("")
    public String adminIndex(@RequestParam(required = false) String error, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // if no session cookie redirect to /login
        System.out.println("SIIN OLEME");
        String token = getToken(request.getCookies());
        if (token.isBlank()) {
            response.sendRedirect("/admin/login");
            return "admin/admin";
        }
        // if session token then validate
        if (!validatetoken(token)) {
            response.sendRedirect("/admin/login");
            return "admin/admin";
        } else {
            List<Toode> tooted = toodeRepository.findAll();
            //System.out.println(tooted);
            model.addAttribute("tooted", tooted);
            return "admin/admin";
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
            return "admin/adminLogin";
        } else if (validatetoken(token)) {
            // if token is correct
            response.sendRedirect("/admin/");
            return "admin/admin";
        } else {
            // if token is not correct - remove cookie
            Cookie cookie = new Cookie("token", null);
            response.addCookie(cookie);
            return "admin/adminLogin";
        }
    }
    @PostMapping("/sendLogin")
    public void adminSendLogin(@RequestParam String username, @RequestParam String password, HttpServletResponse response) throws IOException {
        //System.out.println(username);
        //System.out.println(password);

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


    @GetMapping("/toode")
    public String toodeteMuutmine(@RequestParam String kood, Model model, HttpServletResponse response, HttpServletRequest request) throws IOException {
        if (!validatetoken(getToken(request.getCookies()))) {
            response.addCookie(new Cookie("admin", null));
            response.sendRedirect("/admin");
            return null;
        }

        List<Toode> tooteList = toodeRepository.findByKood(kood);

        if (kood.equals("new")) {
            model.addAttribute("kood", "");
            model.addAttribute("name", "");
            model.addAttribute("price", "");
            model.addAttribute("amount", "");
            model.addAttribute("desc", "");

            return "admin/toodeNew";
        }

        if (Objects.isNull(tooteList) || tooteList.size() == 0) {
            System.out.println("TOOTEKOOD ON VALE");
            model.addAttribute("desc", "TOOTEKOOD ON VALE");
            return "admin/toode";
        }

        Toode toode = tooteList.get(0);
        System.out.println(toode);
        /* Kui pilti polnud
        if (!(toode.getImage().size()==0)) {
            Pilt a = pildiTeenus.getPhoto(toode.getImage().get(0));
            model.addAttribute("image", Base64.getEncoder().encodeToString(a.getImage().getData()));
        }
         */
        Pilt a = pildiTeenus.getPhoto(toode.getImage().get(0));
        System.out.println(a.getHash());
        model.addAttribute("image", Base64.getEncoder().encodeToString(a.getImage().getData()));
        model.addAttribute("kood", toode.getKood());
        model.addAttribute("name", toode.getName());
        model.addAttribute("price", toode.getPrice());
        model.addAttribute("amount", toode.getAmount());
        model.addAttribute("desc", toode.getDesc());
        model.addAttribute("category", toode.getCategory());

        return "admin/toode";
    }

    @PostMapping("/toodeUpdate")
    public void toodeUpdate(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile multipart, @RequestParam String kood, @RequestParam String name, @RequestParam String price, @RequestParam String amount, @RequestParam String desc, @RequestParam String category, HttpServletResponse response) throws IOException {
        if (!validatetoken(getToken(request.getCookies()))) {
            response.addCookie(new Cookie("admin", null));
            response.sendRedirect("/admin");
            return;
        }
        /**
         * 16MB limiit
         * Pildi nimi ei tohi olla "default"
         * price ei tohi sisaldada tähti ega koma
         * VAHEPEAL MUUDAB DEFAULT PILDI PEALE LAMPI
         */
        System.out.println(multipart.getBytes().length);

        // Default pildi hash
        //String pildiHash = "8d0f20006bf035706e38f835a6f912d16aad25a14003c3221d4d633ba77a7855";
        String pildiHash = MainApplication.defaultHash;

        if (multipart.getBytes().length > 0) {
            // Kui saadetakse pilt
            try {
                // Teeb hashi
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(multipart.getBytes(), 0, multipart.getBytes().length);
                pildiHash = Hex.encodeHexString(md.digest());
            } catch (Exception e) {
                System.out.println(e);
            }
            // Kontrollib kas pilt on juba olemas hashi järgi
            if (Objects.isNull(pildiTeenus.getPhoto(pildiHash))) {
                // Pilti ei ole andmebaasis
                pildiTeenus.addPhoto(multipart.getOriginalFilename(), multipart);
            }
        }

        List<Toode> asd = toodeRepository.findByKood(kood);

        if (Objects.isNull(asd) || asd.size() == 0) {
            toodeRepository.save(new Toode(kood, name, desc, Double.valueOf(price), amount, new ArrayList<String>(Arrays.asList(pildiHash)), category));
            response.sendRedirect("/admin");
            return;
        }

        Toode temp = asd.get(0);
        /**
         * VÕIMALUS LISADA MITU PILTI
         */
        temp.setAmount(amount);
        temp.setName(name);
        temp.setPrice(Double.valueOf(price));
        temp.setDesc(desc);
        temp.setImage(new ArrayList<String>(Arrays.asList(pildiHash)));
        temp.setCategory(category);

        toodeRepository.save(temp);

        response.sendRedirect("/admin");
    }

    @GetMapping("/toodeRemove")
    public void toodeRemove(@RequestParam String kood, HttpServletResponse response, HttpServletRequest request) throws IOException {
        if (validatetoken(getToken(request.getCookies()))) {
            toodeRepository.deleteToodeByKood(kood);
            response.sendRedirect("/admin");
        } else {
            response.addCookie(new Cookie("admin", null));
            response.sendRedirect("/admin");
        }
    }

    @GetMapping("/toodeAdd")
    public void toodeAdd(HttpServletResponse response) throws IOException {
        response.sendRedirect("/admin/toode?kood=new");
    }


    @GetMapping("/tellimused")
    public void tellimused() {

    }

    @GetMapping("/kasutajatugi")
    public void kasutajatugi() {

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
