package bg.softuni.hotel_reservation_system.web;

import bg.softuni.hotel_reservation_system.model.dto.reservation.ReservationCreateRequest;
import bg.softuni.hotel_reservation_system.model.dto.reservation.ReservationDto;
import bg.softuni.hotel_reservation_system.repository.UserRepository;
import bg.softuni.hotel_reservation_system.service.ReservationService;
import bg.softuni.hotel_reservation_system.service.RoomService;
import bg.softuni.hotel_reservation_system.service.UserService;
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
@RequestMapping()
public class ReservationController {

    private final ReservationService reservationService;
    private final RoomService roomService;
    private final UserService userService;
    private final UserRepository userRepository;

    public ReservationController(ReservationService reservationService, RoomService roomService, UserService userService, UserRepository userRepository) {
        this.reservationService = reservationService;
        this.roomService = roomService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/reservations")
 public ModelAndView myReservations(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("my-reservations");
     UUID id = (UUID) session.getAttribute("userId");
     if (id == null) {
         return new ModelAndView("redirect:/login");
     }
     List<ReservationDto> reservations = reservationService.findReservationsByUser(id);
     modelAndView.addObject("reservations", reservations);
     return modelAndView;
 }

 @GetMapping("/reservation/create/{roomId}")
    public ModelAndView createReservation(@PathVariable UUID roomId, HttpSession session) {
        if(session.getAttribute("userId") == null) {
            return new ModelAndView("redirect:/login");
        }
     ModelAndView modelAndView = new ModelAndView("reservation-create");
     modelAndView.addObject("room", roomService.findRoomById(roomId));
     ReservationCreateRequest reservationCreateRequest = new ReservationCreateRequest();
     modelAndView.addObject("reservationCreateRequest", reservationCreateRequest);
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
            return modelAndView;
        }
        UUID userId = (UUID) session.getAttribute("userId");
        reservationService.createReservation(userId, roomId, request);
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Reservation created successfully! Please complete your payment within 6 hours. If no payment is received, your reservation will be automatically cancelled."
        );
        return new ModelAndView("redirect:/reservations");
    }

    @PostMapping("/reservation/{id}/cancel")
    public ModelAndView cancelReservation(@PathVariable UUID id, HttpSession session) {
        if(session.getAttribute("userId") == null) {
            return new ModelAndView("redirect:/login");
        }
        UUID userId = (UUID) session.getAttribute("userId");
        reservationService.cancelReservation(id, userId);
        return new ModelAndView("redirect:/reservations");
    }

    @GetMapping("/reservation/{id}")
    public ModelAndView reservation(@PathVariable UUID id, HttpSession session) {
        if(session.getAttribute("userId") == null) {
            return new ModelAndView("redirect:/login");
        }
        UUID userId = (UUID) session.getAttribute("userId");
        ReservationDto reservation = reservationService.findReservationByIdAndUser(id, userId);
        ModelAndView modelAndView = new ModelAndView("reservation-details");
        modelAndView.addObject("reservation", reservation);
        return modelAndView;
    }
}

