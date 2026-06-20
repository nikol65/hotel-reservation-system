package bg.softuni.hotel_reservation_system.model.mapper.room;

import bg.softuni.hotel_reservation_system.model.dto.room.RoomAddRequest;
import bg.softuni.hotel_reservation_system.model.dto.room.RoomDto;
import bg.softuni.hotel_reservation_system.model.entity.Room;

public class RoomMapper {
    private RoomMapper() {
    }

    public static Room toRoomEntity(RoomAddRequest roomAddRequest) {
        if (roomAddRequest == null) {
            return null;
        }
        return Room.builder()
                .roomNumber(roomAddRequest.getRoomNumber())
                .description(roomAddRequest.getDescription())
                .pricePerNight(roomAddRequest.getPricePerNight())
                .roomStatus(roomAddRequest.getRoomStatus())
                .roomType(roomAddRequest.getRoomType())
                .capacity(roomAddRequest.getCapacity())
                .floor(roomAddRequest.getFloor())
                .build();
    }

    public static RoomDto toRoomDto(Room room) {
        if (room == null) {
            return null;
        }
        return RoomDto.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .description(room.getDescription())
                .pricePerNight(room.getPricePerNight())
                .roomStatus(room.getRoomStatus())
                .roomType(room.getRoomType())
                .capacity(room.getCapacity())
                .floor(room.getFloor())
                .build();
    }

    public static void updateRoomFromRequest(RoomAddRequest request, Room room) {
        if (request == null || room == null) {
            return;
        }
        room.setRoomNumber(request.getRoomNumber());
        room.setDescription(request.getDescription());
        room.setPricePerNight(request.getPricePerNight());
        room.setRoomStatus(request.getRoomStatus());
        room.setRoomType(request.getRoomType());
        room.setCapacity(request.getCapacity());
        room.setFloor(request.getFloor());
    }
}
