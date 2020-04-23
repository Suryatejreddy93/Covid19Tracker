package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import services.Covid19DataService;

@Controller
public class VirusController {

    @Autowired
    Covid19DataService covid19DataService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("virusDataList", covid19DataService.getVirusAllDataList());
        return "index";
    }
}
