package bg.softuni.hotel_reservation_system.web;

import bg.softuni.hotel_reservation_system.model.dto.user.UserDto;
import bg.softuni.hotel_reservation_system.model.dto.user.UserLoginRequest;
import bg.softuni.hotel_reservation_system.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("userLoginRequest", new UserLoginRequest());
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(
            @ModelAttribute("userLoginRequest") UserLoginRequest userLoginRequest,
            HttpSession session) {
        try {
            UserDto user = userService.login(userLoginRequest);

            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userRole", user.getRole());
            if ("ADMIN".equals(user.getRole().toString())) {
                return new ModelAndView("redirect:/admin/rooms");
            }
            return new ModelAndView("redirect:/home");

        } catch (RuntimeException e) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("userLoginRequest", userLoginRequest);
            modelAndView.addObject("errorMessage", "Invalid username or password.");
            return modelAndView;
        }
    }

    @PostMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        return new ModelAndView("redirect:/");
    }
}