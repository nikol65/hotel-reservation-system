package bg.softuni.hotel_reservation_system.web;

import bg.softuni.hotel_reservation_system.model.dto.user.EditUserRequest;
import bg.softuni.hotel_reservation_system.model.dto.user.UserDto;
import bg.softuni.hotel_reservation_system.repository.ReservationRepository;
import bg.softuni.hotel_reservation_system.service.PaymentService;
import bg.softuni.hotel_reservation_system.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.UUID;

@Controller
public class UserController {

    private final UserService userService;
    private final ReservationRepository reservationRepository;
    private final PaymentService paymentService;

    public UserController(UserService userService,
                          ReservationRepository reservationRepository,
                          PaymentService paymentService) {
        this.userService = userService;
        this.reservationRepository = reservationRepository;
        this.paymentService = paymentService;
    }

    @GetMapping("/profile")
    public ModelAndView profile(HttpSession session) {
        UUID id = (UUID) session.getAttribute("userId");

        if (id == null) {
            return new ModelAndView("redirect:/login");
        }

        UserDto user = userService.findUserById(id);

        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("user", user);

        if ("USER".equals(user.getRole().toString())) {
            long totalReservations = reservationRepository.countByUserId(id);
            BigDecimal totalSpent = paymentService.calculateTotalSpentByUser(id);

            String loyaltyStatus = "Standard";

            if (totalSpent.compareTo(BigDecimal.valueOf(1000)) >= 0) {
                loyaltyStatus = "Gold";
            } else if (totalSpent.compareTo(BigDecimal.valueOf(500)) >= 0) {
                loyaltyStatus = "Silver";
            }

            modelAndView.addObject("totalReservations", totalReservations);
            modelAndView.addObject("totalSpent", totalSpent);
            modelAndView.addObject("loyaltyStatus", loyaltyStatus);
        }

        return modelAndView;
    }

    @GetMapping("/profile/edit")
    public ModelAndView editProfile(HttpSession session) {
        UUID id = (UUID) session.getAttribute("userId");

        if (id == null) {
            return new ModelAndView("redirect:/login");
        }

        UserDto user = userService.findUserById(id);

        EditUserRequest editUserRequest = EditUserRequest.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();

        ModelAndView modelAndView = new ModelAndView("edit-profile");
        modelAndView.addObject("editUserRequest", editUserRequest);

        return modelAndView;
    }

    @PostMapping("/profile/edit")
    public ModelAndView editProfile(@Valid @ModelAttribute("editUserRequest") EditUserRequest editUserRequest,
                                    BindingResult bindingResult,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes) {
        UUID id = (UUID) session.getAttribute("userId");

        if (id == null) {
            return new ModelAndView("redirect:/login");
        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("edit-profile");
        }

        try {
            userService.updateProfile(id, editUserRequest);
        } catch (RuntimeException e) {
            ModelAndView modelAndView = new ModelAndView("edit-profile");
            modelAndView.addObject("editUserRequest", editUserRequest);
            modelAndView.addObject("errorMessage", e.getMessage());
            return modelAndView;
        }

        redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully.");
        return new ModelAndView("redirect:/profile");
    }
}