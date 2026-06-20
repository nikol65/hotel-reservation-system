package bg.softuni.hotel_reservation_system.web;

import bg.softuni.hotel_reservation_system.model.dto.user.UserDto;
import bg.softuni.hotel_reservation_system.repository.ReservationRepository;
import bg.softuni.hotel_reservation_system.service.PaymentService;
import bg.softuni.hotel_reservation_system.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final ReservationRepository reservationRepository;
    private final PaymentService paymentService;

    @GetMapping("/profile")
    public ModelAndView profile(HttpSession session){
        ModelAndView modelAndView = new ModelAndView("profile");
        if (session.getAttribute("userId") == null) {
            return new ModelAndView("redirect:/login");
        }
        UUID userId = (UUID) session.getAttribute("userId");
        UserDto user = userService.findUserById(userId);
}
