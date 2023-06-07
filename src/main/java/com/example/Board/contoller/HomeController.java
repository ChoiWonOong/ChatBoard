package com.example.Board.contoller;

import com.example.Board.infra.AuthenticationFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private final AuthenticationFacade authFacade;
    public HomeController(@Autowired AuthenticationFacade authFacade){
        this.authFacade = authFacade;
    }
    @GetMapping("/")
    public String home(){
        return "redirect:/home";
    }
    @GetMapping("/home")
    public String index(//Principal principal
                        //Authentication authentication

                         ){
        try{
            logger.info("connected user : ", authFacade.getUsername());
            logger.info("authority : ", authFacade.getAuthorities());
        }
        catch (NullPointerException e){
            logger.info("no user logged in");
        }
        return "index";
    }
}
