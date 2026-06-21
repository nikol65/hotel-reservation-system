package bg.softuni.hotel_reservation_system.web;

import bg.softuni.hotel_reservation_system.model.dto.user.UserDto;
import bg.softuni.hotel_reservation_system.repository.ReservationRepository;
import bg.softuni.hotel_reservation_system.service.PaymentService;
import bg.softuni.hotel_reservation_system.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.UUID;

@Controller
public class UserController {

    private final UserService userService;
    private final ReservationRepository reservationRepository;
    private final PaymentService paymentService;

    public UserController(UserService userService, ReservationRepository reservationRepository, PaymentService paymentService) {
        this.userService = userService;
        this.reservationRepository = reservationRepository;
        this.paymentService = paymentService;
    }

    @GetMapping("/profile")
    public ModelAndView profile(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("profile");

        UUID id = (UUID) session.getAttribute("userId");

        if (id == null) {
            return new ModelAndView("redirect:/login");
        }

        UserDto user = userService.findUserById(id);

        long totalReservations = reservationRepository.countByUserId(id);
        BigDecimal totalSpent = paymentService.calculateTotalSpentByUser(id);

        String loyaltyStatus = "Standard";

        if (totalSpent.compareTo(BigDecimal.valueOf(1000)) >= 0) {
            loyaltyStatus = "Gold";
        } else if (totalSpent.compareTo(BigDecimal.valueOf(500)) >= 0) {
            loyaltyStatus = "Silver";
        }

        modelAndView.addObject("user", user);
        modelAndView.addObject("totalReservations", totalReservations);
        modelAndView.addObject("totalSpent", totalSpent);
        modelAndView.addObject("loyaltyStatus", loyaltyStatus);

        return modelAndView;
    }

}
