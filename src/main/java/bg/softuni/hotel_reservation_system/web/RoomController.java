package bg.softuni.hotel_reservation_system.web;

import bg.softuni.hotel_reservation_system.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/rooms")
    public ModelAndView rooms() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("rooms");
        modelAndView.addObject("rooms", roomService.findAllRooms());
        return modelAndView;


    }
}
