package bg.softuni.hotel_reservation_system.service;

import bg.softuni.hotel_reservation_system.model.dto.room.RoomAddRequest;
import bg.softuni.hotel_reservation_system.model.dto.room.RoomDto;
import bg.softuni.hotel_reservation_system.model.entity.Room;
import bg.softuni.hotel_reservation_system.model.enums.RoomStatus;
import bg.softuni.hotel_reservation_system.model.enums.RoomType;
import bg.softuni.hotel_reservation_system.model.mapper.room.RoomMapper;
import bg.softuni.hotel_reservation_system.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomDto addRoom(RoomAddRequest roomAddRequest) {

        validateRoomCapacity(roomAddRequest);

        if (roomRepository.existsByRoomNumber(roomAddRequest.getRoomNumber())) {
            throw new RuntimeException("Room number already exists");
        }
        Room entity = RoomMapper.toRoomEntity(roomAddRequest);
        Room updatedRoom = roomRepository.save(entity);
        return RoomMapper.toRoomDto(updatedRoom);
    }

    public List<RoomDto> findAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream().map(RoomMapper::toRoomDto).toList();
    }

    public RoomDto findRoomById(UUID id) {
        Room room = roomRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Room not found"));
        return RoomMapper.toRoomDto(room);
    }

    public RoomDto updateRoom(UUID id, RoomAddRequest request) {
        validateRoomCapacity(request);
        Room room = roomRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Room not found"));
        RoomMapper.updateRoomFromRequest(request, room);
        Room updatedRoom = roomRepository.save(room);
        return RoomMapper.toRoomDto(updatedRoom);
    }

    public RoomDto deactivateRoom(UUID id) {
        Room room = roomRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Room not found"));
        room.setRoomStatus(RoomStatus.INACTIVE);
        Room updatedRoom = roomRepository.save(room);
        return RoomMapper.toRoomDto(updatedRoom);
    }
    public List<RoomDto> findFilteredRooms(RoomType roomType, BigDecimal maxPrice, Integer minCapacity) {
        return findAllRooms()
                .stream()
                .filter(room -> room.getRoomStatus() == RoomStatus.AVAILABLE)
                .filter(room -> roomType == null || room.getRoomType() == roomType)
                .filter(room -> maxPrice == null || room.getPricePerNight().compareTo(maxPrice) <= 0)
                .filter(room -> minCapacity == null || room.getCapacity() >= minCapacity)
                .toList();
    }
    public long getTotalRoomsCount() {
        return roomRepository.count();
    }

    public long getAvailableRoomsCount() {
        return roomRepository.findAll()
                .stream()
                .filter(room -> room.getRoomStatus() == RoomStatus.AVAILABLE)
                .count();
    }

    public long getInactiveRoomsCount() {
        return roomRepository.findAll()
                .stream()
                .filter(room -> room.getRoomStatus() == RoomStatus.INACTIVE)
                .count();
    }

    public double getAverageRoomPrice() {
        return roomRepository.findAll()
                .stream()
                .map(Room::getPricePerNight)
                .mapToDouble(BigDecimal::doubleValue)
                .average()
                .orElse(0);
    }
    private void validateRoomCapacity(RoomAddRequest request) {
        if (request.getRoomType() == null || request.getCapacity() == null) {
            return;
        }

        int maxCapacity;

        switch (request.getRoomType()) {
            case SINGLE -> maxCapacity = 1;
            case DOUBLE -> maxCapacity = 2;
            case APARTMENT -> maxCapacity = 4;
            default -> throw new RuntimeException("Unsupported room type");
        }

        if (request.getCapacity() > maxCapacity) {
            throw new RuntimeException(
                    request.getRoomType() + " room cannot have more than " + maxCapacity + " guests"
            );
        }
    }

    public boolean isInvalidRoomTypeCapacity(RoomType roomType, Integer minCapacity) {
        if (roomType == null || minCapacity == null) {
            return false;
        }

        return switch (roomType) {
            case SINGLE -> minCapacity > 1;
            case DOUBLE -> minCapacity > 2;
            case APARTMENT -> minCapacity > 4;
            default -> false;
        };
    }
}