package bg.softuni.hotel_reservation_system.web;

import bg.softuni.hotel_reservation_system.model.dto.payment.PaymentCreateRequest;
import bg.softuni.hotel_reservation_system.model.dto.reservation.ReservationDto;
import bg.softuni.hotel_reservation_system.service.PaymentService;
import bg.softuni.hotel_reservation_system.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final ReservationService reservationService;
    private final PaymentService paymentService;

    @GetMapping("/payment/create/{reservationId}")
    public ModelAndView createPayment(@PathVariable UUID reservationId, HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return new ModelAndView("redirect:/login");
        }
        UUID userId = (UUID) session.getAttribute("userId");
        ReservationDto reservation = reservationService.findReservationByIdAndUser(reservationId, userId);
        ModelAndView modelAndView = new ModelAndView("payment-create");
        modelAndView.addObject("reservation", reservation);
        modelAndView.addObject("paymentCreateRequest", new PaymentCreateRequest());
        return modelAndView;
    }

    @PostMapping("/payment/create/{reservationId}")
    public ModelAndView createPayment(@PathVariable UUID reservationId, @Valid @ModelAttribute PaymentCreateRequest request,
        BindingResult result, HttpSession session){
            if (session.getAttribute("userId") == null ){
                return new ModelAndView("redirect:/login");
            }
            if (result.hasErrors()){
                ModelAndView modelAndView = new ModelAndView("payment-create");
                UUID userId = (UUID) session.getAttribute("userId");
                ReservationDto reservation = reservationService.findReservationByIdAndUser(reservationId, userId);
                modelAndView.addObject("reservation", reservation);
                modelAndView.addObject("paymentCreateRequest", request);
                return modelAndView;
            }
        UUID userId = (UUID) session.getAttribute("userId");
        paymentService.createPayment(reservationId, userId, request);
        return new ModelAndView("redirect:/reservations");
    }

    @GetMapping("/payments")
    public ModelAndView myPayments(HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return new ModelAndView("redirect:/login");
        }

        UUID userId = (UUID) session.getAttribute("userId");

        ModelAndView modelAndView = new ModelAndView("payments");
        modelAndView.addObject("payments", paymentService.findPaymentsByUser(userId));

        return modelAndView;
    }

}
