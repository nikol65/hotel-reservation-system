package bg.softuni.hotel_reservation_system.web;

import bg.softuni.hotel_reservation_system.model.dto.user.UserRegisterRequest;
import bg.softuni.hotel_reservation_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("userRegisterRequest", new UserRegisterRequest());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(
            @Valid @ModelAttribute("userRegisterRequest") UserRegisterRequest userRegisterRequest,
            BindingResult bindingResult) {
        if (!userRegisterRequest.getPassword().equals(userRegisterRequest.getConfirmPassword())) {
            bindingResult.rejectValue(
                    "confirmPassword",
                    "password.mismatch",
                    "Passwords do not match"
            );
        }
        if (bindingResult.hasErrors()) {
            return new ModelAndView("register");
        }
        userService.register(userRegisterRequest);
        return new ModelAndView("redirect:/login");
    }
}