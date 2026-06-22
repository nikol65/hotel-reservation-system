package bg.softuni.hotel_reservation_system.web;

import bg.softuni.hotel_reservation_system.model.dto.reservation.ReservationCreateRequest;
import bg.softuni.hotel_reservation_system.model.dto.reservation.ReservationDto;
import bg.softuni.hotel_reservation_system.service.ReservationService;
import bg.softuni.hotel_reservation_system.service.RoomService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
public class ReservationController {

    private final ReservationService reservationService;
    private final RoomService roomService;

    public ReservationController(ReservationService reservationService, RoomService roomService) {
        this.reservationService = reservationService;
        this.roomService = roomService;
    }

    @GetMapping("/reservations")
    public ModelAndView myReservations(HttpSession session) {
        UUID userId = (UUID) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView("redirect:/login");
        }
        List<ReservationDto> reservations = reservationService.findReservationsByUser(userId);
        ModelAndView modelAndView = new ModelAndView("my-reservations");
        modelAndView.addObject("reservations", reservations);
        return modelAndView;
    }

    @GetMapping("/reservation/create/{roomId}")
    public ModelAndView createReservation(@PathVariable UUID roomId, HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelAndView modelAndView = new ModelAndView("reservation-create");
        modelAndView.addObject("room", roomService.findRoomById(roomId));
        modelAndView.addObject("reservationCreateRequest", new ReservationCreateRequest());
        return modelAndView;
    }

    @PostMapping("/reservation/create/{roomId}")
    public ModelAndView createReservation(@PathVariable UUID roomId,
                                          @Valid @ModelAttribute ReservationCreateRequest request,
                                          BindingResult result,
                                          HttpSession session,
                                          RedirectAttributes redirectAttributes) {

        if (session.getAttribute("userId") == null) {
            return new ModelAndView("redirect:/login");
        }

        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("reservation-create");
            modelAndView.addObject("room", roomService.findRoomById(roomId));
            modelAndView.addObject("reservationCreateRequest", request);
            return modelAndView;
        }
        UUID userId = (UUID) session.getAttribute("userId");
        try {
            reservationService.createReservation(userId, roomId, request);
            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Reservation created successfully! Please complete your payment within 6 hours. If no payment is received, your reservation will be automatically cancelled."
            );
            return new ModelAndView("redirect:/reservations");

        } catch (RuntimeException e) {
            ModelAndView modelAndView = new ModelAndView("reservation-create");
            modelAndView.addObject("room", roomService.findRoomById(roomId));
            modelAndView.addObject("reservationCreateRequest", request);
            modelAndView.addObject("errorMessage", e.getMessage());
            return modelAndView;
        }
    }

    @PostMapping("/reservation/{id}/cancel")
    public ModelAndView cancelReservation(@PathVariable UUID id, HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return new ModelAndView("redirect:/login");
        }
        UUID userId = (UUID) session.getAttribute("userId");
        reservationService.cancelReservation(id, userId);
        return new ModelAndView("redirect:/reservations");
    }

    @GetMapping("/reservation/{id}")
    public ModelAndView reservation(@PathVariable UUID id, HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return new ModelAndView("redirect:/login");
        }
        UUID userId = (UUID) session.getAttribute("userId");
        ReservationDto reservation = reservationService.findReservationByIdAndUser(id, userId);
        ModelAndView modelAndView = new ModelAndView("reservation-details");
        modelAndView.addObject("reservation", reservation);
        return modelAndView;
    }
}