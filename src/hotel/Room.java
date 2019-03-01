package hotel;

public class Room {
    int roomNumber;
    RoomType roomType;
    double roomPrice;
    int roomCapacity;
    String roomFacilities;

    public Room(int number, RoomType type, double price, int capacity, String facilities){
        roomNumber = number;
        roomType = type;
        roomPrice = price;
        roomCapacity = capacity;
        roomFacilities = facilities;
    }
}