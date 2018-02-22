package lt.mantas.kelioSalygos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //
public class
IndexController {
    @RequestMapping("/") // Kokiu adresu bus
//    @ResponseBody // ką rodyti atsiliepiant nereik jei darai is atskiro index.html failo
    String index() {
        // viskas kas parašyta šioje dalyje bus
        // HTML puslapio dalis
        return "index";
    }
}