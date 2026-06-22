package bg.softuni.hotel_reservation_system.web;
import bg.softuni.hotel_reservation_system.model.dto.room.RoomAddRequest;
import bg.softuni.hotel_reservation_system.model.enums.UserRole;
import bg.softuni.hotel_reservation_system.service.RoomService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/rooms")
public class AdminRoomController {

    private final RoomService roomService;

    @GetMapping
    public ModelAndView adminRooms(HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return new ModelAndView("redirect:/login");
        }
        if (!isAdmin(session)) {
            return new ModelAndView("redirect:/home");
        }
        ModelAndView modelAndView = new ModelAndView("admin-rooms");
        modelAndView.addObject("rooms", roomService.findAllRooms());
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView addRoom(HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return new ModelAndView("redirect:/login");
        }
        if (!isAdmin(session)) {
            return new ModelAndView("redirect:/home");
        }
        ModelAndView modelAndView = new ModelAndView("room-add");
        modelAndView.addObject("roomCreateRequest", new RoomAddRequest());
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addRoom(@Valid @ModelAttribute("roomCreateRequest") RoomAddRequest roomCreateRequest,
                                BindingResult bindingResult,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        if (session.getAttribute("userId") == null) {
            return new ModelAndView("redirect:/login");
        }
        if (!isAdmin(session)) {
            return new ModelAndView("redirect:/home");
        }
        if (bindingResult.hasErrors()) {
            return new ModelAndView("room-add");
        }
        try {
            roomService.addRoom(roomCreateRequest);
        } catch (RuntimeException e) {
            bindingResult.reject("room.error", e.getMessage());
            return new ModelAndView("room-add");
        }
        redirectAttributes.addFlashAttribute("successMessage", "Room created successfully.");
        return new ModelAndView("redirect:/admin/rooms");
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editRoom(@PathVariable UUID id, HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return new ModelAndView("redirect:/login");
        }
        if (!isAdmin(session)) {
            return new ModelAndView("redirect:/home");
        }
        ModelAndView modelAndView = new ModelAndView("room-edit");
        modelAndView.addObject("roomId", id);
        modelAndView.addObject("roomEditRequest", roomService.findRoomById(id));
        return modelAndView;
    }

    @PostMapping("/{id}/edit")
    public ModelAndView editRoom(@PathVariable UUID id,
                                 @Valid @ModelAttribute("roomEditRequest") RoomAddRequest roomEditRequest,
                                 BindingResult bindingResult,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        if (session.getAttribute("userId") == null) {
            return new ModelAndView("redirect:/login");
        }
        if (!isAdmin(session)) {
            return new ModelAndView("redirect:/home");
        }
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("room-edit");
            modelAndView.addObject("roomId", id);
            modelAndView.addObject("roomEditRequest", roomEditRequest);
            return modelAndView;
        }
        try {
            roomService.updateRoom(id, roomEditRequest);
        } catch (RuntimeException e) {
            ModelAndView modelAndView = new ModelAndView("room-edit");
            modelAndView.addObject("roomId", id);
            modelAndView.addObject("roomEditRequest", roomEditRequest);
            modelAndView.addObject("errorMessage", e.getMessage());
            return modelAndView;
        }
        redirectAttributes.addFlashAttribute("successMessage", "Room updated successfully.");
        return new ModelAndView("redirect:/admin/rooms");
    }

    @PostMapping("/{id}/delete")
    public ModelAndView deactivateRoom(@PathVariable UUID id,
                                       HttpSession session,
                                       RedirectAttributes redirectAttributes) {
        if (session.getAttribute("userId") == null) {
            return new ModelAndView("redirect:/login");
        }
        if (!isAdmin(session)) {
            return new ModelAndView("redirect:/home");
        }
        roomService.deactivateRoom(id);
        redirectAttributes.addFlashAttribute("successMessage", "Room deactivated successfully.");
        return new ModelAndView("redirect:/admin/rooms");
    }

    private boolean isAdmin(HttpSession session) {
        Object userRole = session.getAttribute("userRole");
        if (userRole instanceof UserRole) {
            return userRole == UserRole.ADMIN;
        }
        return userRole != null && UserRole.ADMIN.name().equals(userRole.toString());
    }
}