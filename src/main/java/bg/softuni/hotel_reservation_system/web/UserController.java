package bg.softuni.hotel_reservation_system.web;

import bg.softuni.hotel_reservation_system.model.dto.user.UserDto;
import bg.softuni.hotel_reservation_system.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ModelAndView profile(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("profile");
        UUID id = (UUID) session.getAttribute("userId");
        if (id != null) {
            modelAndView.addObject("user", userService.findUserById(id));
        }else {
            return new ModelAndView("redirect:/login");
        }
        return modelAndView;
    }
}
