package bg.softuni.hotel_reservation_system.web;

import bg.softuni.hotel_reservation_system.model.enums.RoomType;
import bg.softuni.hotel_reservation_system.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/rooms")
    public ModelAndView rooms(@RequestParam(required = false) RoomType roomType,
                              @RequestParam(required = false) BigDecimal maxPrice,
                              @RequestParam(required = false) Integer minCapacity) {

        ModelAndView modelAndView = new ModelAndView("rooms");

        modelAndView.addObject("rooms",
                roomService.findFilteredRooms(roomType, maxPrice, minCapacity));

        modelAndView.addObject("selectedRoomType", roomType);
        modelAndView.addObject("maxPrice", maxPrice);
        modelAndView.addObject("minCapacity", minCapacity);
        modelAndView.addObject("roomTypes", RoomType.values());

        return modelAndView;
    }
}