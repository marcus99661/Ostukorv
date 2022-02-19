package com.github.marcus99661.ostukorv;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.marcus99661.ostukorv.Repository.KasutajaRepository;
import com.github.marcus99661.ostukorv.Repository.PiltRepository;
import com.github.marcus99661.ostukorv.Repository.ToodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@org.springframework.stereotype.Controller
public class Controller {

    static Algorithm algorithm = Algorithm.HMAC256("6643110C5628FFF59EDF76B82D5BF573BF800F16A4D65DEB1E5D6F1A46296D0B");

    @Autowired
    public KasutajaRepository repository;

    @Autowired
    private MongoTemplate mt;

    @Autowired
    private ToodeRepository toodeRepository;

    private PiltService pildiTeenus = new PiltService();

    public Controller(MongoTemplate mt, KasutajaRepository repository, ToodeRepository toodeRepository, PiltRepository piltRepository) {
        this.mt = mt;
        this.repository = repository;
        this.toodeRepository = toodeRepository;
        pildiTeenus.photoRepo = piltRepository;
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
         * ~~Check if email is already registered~~
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
        String token = getCookieString(request.getCookies(), "session");
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

    @GetMapping("/kategooria")
    public String kategooria(Model model) {
        model.addAttribute("page", "kategooria");
        return "main";
    }

    @GetMapping("/kategooria/{kategooria}")
    public String kategooria(Model model, @PathVariable String kategooria, @RequestParam(required = false) String p) {
        if (Objects.isNull(p)) {
            p = "1";
        }
        model.addAttribute("page", "kategooria");
        // TODO: Kontrollib kas kategooria on listis

        // TODO: Kontrollib kas redirect on lubatud
        model.addAttribute("redirect", kategooria);
        if (Objects.isNull(p)) {
            model.addAttribute("p", "1");
        } else {
            model.addAttribute("p", p);
        }

        // Võtab kõik tooted andmebaasist mille kategooria on antud
        List<Toode> kategooriaTooted = toodeRepository.findByCategory(kategooria);
        List<Toode> uusTooteList = new ArrayList<>();
        for (Toode temp : kategooriaTooted) {
            Pilt a = pildiTeenus.getPhoto(temp.getImage().get(0));
            temp.setThumbnail(Base64.getEncoder().encodeToString(a.getImage().getData()));
            uusTooteList.add(temp);
        }
        model.addAttribute("tooted", uusTooteList);
        System.out.println(uusTooteList);
        return "main";
    }

    @GetMapping("/toode")
    public String toode(Model model, @RequestParam String kood) {
        model.addAttribute("page", "toode");

        Toode toode = toodeRepository.findByKood(kood).get(0);
        System.out.println(toode);

        Pilt a = pildiTeenus.getPhoto(toode.getImage().get(0));
        toode.setThumbnail(Base64.getEncoder().encodeToString(a.getImage().getData()));

        List<String> pildid = new ArrayList<>();

        for (String i : toode.getImage()) {
            Pilt b = pildiTeenus.getPhoto(toode.getImage().get(0));
            pildid.add(Base64.getEncoder().encodeToString(b.getImage().getData()));
        }

        model.addAttribute("kood", toode.getKood());
        model.addAttribute("name", toode.getName());
        model.addAttribute("price", toode.getPrice());
        model.addAttribute("desc", toode.getDesc());
        model.addAttribute("thumbnail", toode.getThumbnail());
        model.addAttribute("pics", pildid);
        return "main";
    }

    @PostMapping("lisaToode")
    public void lisaToode(HttpServletResponse response, HttpServletRequest request, @RequestParam String kood, @RequestParam(required = false) String kogus, @RequestParam(required = false) String redirect,@RequestParam(required = false) String p) throws IOException {
        System.out.println(redirect);
        // TODO: kontrollida, et kogus või kokku oleks alla Integer.MAX_VALUE
        if (Objects.isNull(kogus)) {
            kogus = "1";
        }
        String tooted = getCookieString(request.getCookies(), "tooted");
        if (tooted.isBlank()) {
            tooted += kogus + "=" + kood;
        } else {
            List<String> tooteList = List.of(tooted.split("\\|"));
            List<String> uusTooteList = new ArrayList<>();
            boolean leitud = false;
            for (String i : tooteList) {
                String listiKood = i.split("=")[1];
                if (listiKood.equals(kood)) {
                    String kokku = String.valueOf(Integer.valueOf(i.split("=")[0]) + Integer.valueOf(kogus));
                    uusTooteList.add(kokku + "=" + kood);
                    leitud = true;
                } else {
                    uusTooteList.add(i);
                }
            }
            if (!leitud) {
                uusTooteList.add(kogus + "=" + kood);
            }

            // List -> 1=ABC3|2=ABC7
            tooted = String.join("|", uusTooteList);
        }
        System.out.println(tooted);
        response.addCookie(new Cookie("tooted", tooted));
        response.sendRedirect("/ostukorv");

        /* HILJEM KUI KASUTAB REDIRECTI
        if (!Objects.isNull(redirect)) {
            if (redirect.equalsIgnoreCase("/ostukorv")) {
                response.sendRedirect(redirect);
            } else if (Objects.isNull(p)) {
                response.sendRedirect(redirect + "?p=1");
            } else {
                response.sendRedirect(redirect + "?p=" + p);
            }
        } else {
            // Kui redirect on tühi siis viskab errori
            response.sendRedirect("error");
        }
         */
    }

    @PostMapping("eemaldaToode")
    public void eemaldaToode(HttpServletResponse response, HttpServletRequest request, @RequestParam String kood, @RequestParam(required = false) String kogus, @RequestParam(required = false) String redirect) throws IOException {
        // TODO: Võtab toodete Cookiest toote vähemaks koodi järgi. Kui toode kogus == 0 siis eemaldab täielikult
        System.out.println("Lahutab toote: " + kood);

        if (Objects.isNull(kogus)) {
            kogus = "1";
        }

        String cookieString = getCookieString(request.getCookies(), "tooted");
        if (cookieString.isBlank()) {
            System.out.println("Toodet ei ole cookie all");
            response.sendRedirect("/error");
        } else {
            List<String> tooteList = List.of(cookieString.split("\\|"));
            List<String> uusTooteList = new ArrayList<>();
            for (String i : tooteList) {
                String tooteKogus = i.split("=")[0];
                String listiKood = i.split("=")[1];
                if (listiKood.equals(kood)) {
                    String kokku = String.valueOf(Integer.valueOf(tooteKogus) - Integer.valueOf(kogus));
                    if (!(Integer.parseInt(kokku) <= 0)) {
                        uusTooteList.add(kokku + "=" + kood);
                    }
                } else {
                    uusTooteList.add(i);
                }
            }
            String tooted = String.join("|", uusTooteList);
            response.addCookie(new Cookie("tooted", tooted));
        }
        System.out.println(request.getHeader("Referer"));
        response.sendRedirect(redirect);
    }

    @GetMapping("/ostukorv")
    public String ostukorv(Model model, HttpServletRequest request) {
        // TODO: Lisada nupp millega saab toodet eemaldada
        model.addAttribute("page", "ostukorv");
        String tooteCookie = getCookieString(request.getCookies(), "tooted");
        System.out.println(tooteCookie);

        if (tooteCookie.isBlank()) {
            // Ei ole ühtegi toodet ostukorvis
        }
        List<String> tooteKoodiList = List.of(tooteCookie.split("\\|"));
        List<Toode> tooted = new ArrayList<>();

         // TODO: kontrollib kas tooted on ka saadaval

        // Millegi pärast on listis alati mingi element
        if (tooteKoodiList.size() > 0 && tooteKoodiList.get(0).length() > 0) {
            for (String i : tooteKoodiList) {
                //System.out.println("TootekoodiListi esimene element: " + i + " ja pikkus: " + i.length());
                String kogus = i.split("=")[0];
                String kood = i.split("=")[1];
                try {
                    Toode temp = toodeRepository.findByKood(kood).get(0);
                    Pilt a = pildiTeenus.getPhoto(temp.getImage().get(0));
                    temp.setThumbnail(Base64.getEncoder().encodeToString(a.getImage().getData()));
                    temp.setName(" " + temp.getName());
                    temp.setTooteKogus(kogus);
                    temp.setKoguseHind(String.valueOf(round(Integer.parseInt(kogus) * temp.getPrice(), 2)));
                    tooted.add(temp);
                } catch (Exception e) {
                    System.out.println("TOOTE KOODI EI OLE ANDMEBAASIS");
                }
            }
        }
        model.addAttribute("tooted", tooted);
        double totalPrice = 0d;
        for (Toode i : tooted) {
            totalPrice += Double.parseDouble(i.getKoguseHind());
        }

        model.addAttribute("subtotal", String.valueOf(round(totalPrice, 2)));

        if (tooteKoodiList.size() > 0 && tooteKoodiList.get(0).length() > 0) {
            model.addAttribute("transport", "10.0");
            model.addAttribute("total", String.valueOf(round(totalPrice+10d, 2)));
        } else {
            model.addAttribute("transport", "0.0");
            model.addAttribute("total", String.valueOf(round(totalPrice, 2)));
        }

        return "main";
    }

    @PostMapping("/ostukorvTooteEemaldamine")
    public void ostukorvTooteEemaldamine(HttpServletResponse response, HttpServletRequest request, @RequestParam String kood) throws IOException {
        // TODO: Eemaldab tootekoodi täielikult toodete Cookiest
        System.out.println("Eemaldab toote: " + kood);
        String cookieString = getCookieString(request.getCookies(), "tooted");
        if (cookieString.isBlank()) {
            System.out.println("Toodet ei ole cookie all");
            response.sendRedirect("/error");
        } else {
            List<String> tooteList = List.of(cookieString.split("\\|"));
            List<String> uusTooteList = new ArrayList<>();
            for (String i : tooteList) {
                String tooteKogus = i.split("=")[0];
                String listiKood = i.split("=")[1];
                if (!listiKood.equals(kood)) {
                    uusTooteList.add(i);
                }
            }
            String tooted = String.join("|", uusTooteList);
            response.addCookie(new Cookie("tooted", tooted));
        }
        response.sendRedirect("/ostukorv");
    }

    @GetMapping("/tellimus")
    public String tellimus(Model model, HttpServletRequest request) {
        model.addAttribute("page", "tellimus");
        String tooteCookie = getCookieString(request.getCookies(), "tooted");

        if (tooteCookie.isBlank()) {
            // Ei ole ühtegi toodet ostukorvis
        }
        List<String> tooteKoodiList = List.of(tooteCookie.split("\\|"));
        List<Toode> tooted = new ArrayList<>();

        // TODO: kontrollib kas tooted on ka saadaval

        // Millegi pärast on listis alati mingi element
        if (tooteKoodiList.size() > 0 && tooteKoodiList.get(0).length() > 0) {
            for (String i : tooteKoodiList) {
                //System.out.println("TootekoodiListi esimene element: " + i + " ja pikkus: " + i.length());
                String kogus = i.split("=")[0];
                String kood = i.split("=")[1];
                try {
                    Toode temp = toodeRepository.findByKood(kood).get(0);
                    temp.setName(" " + temp.getName());
                    temp.setTooteKogus(kogus);
                    temp.setKoguseHind(String.valueOf(round(Integer.parseInt(kogus) * temp.getPrice(), 2)));
                    tooted.add(temp);
                } catch (Exception e) {
                    System.out.println("TOOTE KOODI EI OLE ANDMEBAASIS");
                }
            }
        }
        model.addAttribute("tooted", tooted);
        double totalPrice = 0d;
        for (Toode i : tooted) {
            totalPrice += Double.parseDouble(i.getKoguseHind());
        }

        model.addAttribute("subtotal", String.valueOf(round(totalPrice, 2)));

        if (tooteKoodiList.size() > 0 && tooteKoodiList.get(0).length() > 0) {
            model.addAttribute("transport", "10.0");
            model.addAttribute("total", String.valueOf(round(totalPrice+10d, 2)));
        } else {
            model.addAttribute("transport", "0.0");
            model.addAttribute("total", String.valueOf(round(totalPrice, 2)));
        }




        // TODO: Kontrollib kas tooded on laos olemas, kui ei siis redirect ostukorvi erroriga
        return "main";
    }

    @PostMapping("/tellimuseVormistamine")
    public void tellimuseVormistamine(HttpServletResponse response, HttpServletRequest request, @RequestParam String a, @RequestParam String b) {
        System.out.println("Sain tellimuse: ");
    }

    public static String getCookieString(Cookie[] cookies, String name) {
        String token = "";
        if (Objects.isNull(cookies)) {
            return token;
        }
        for (Cookie i : cookies) {
            if (i.getName().equalsIgnoreCase(name)) {
                token = i.getValue();
                break;
            }
        }
        return token;
    }

    public static Cookie getCookie(Cookie[] cookies, String name) {
        String token = "";
        if (Objects.isNull(cookies)) {
            return new Cookie(name, "");
        }
        for (Cookie i : cookies) {
            if (i.getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return null;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
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
