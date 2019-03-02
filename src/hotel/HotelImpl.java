package hotel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//abstract class HotelImpl implements hotel.Hotel {
public class HotelImpl{

    private static List<Room> rooms = new ArrayList<>();
    private static List<Guest> guests = new ArrayList<>();
    private static List<Booking> bookings = new ArrayList<>();
    private static List<Payment> payments = new ArrayList<>();

    public static void main(String args[]){
        System.out.println("Hello World!");

        System.out.println(importRoomsData("src/data/rooms.txt"));
        System.out.println(importGuestsData("src/data/guests.txt"));
        System.out.println(importBookingsData("src/data/bookings.txt"));
        System.out.println(importPaymentsData("src/data/payments.txt"));

        /*displayAllRooms();
        addRoom(123,RoomType.DOUBLE,123.00,2,"no bathroom");
        displayAllRooms();*/

        displayAllRooms();
        displayAllBookings();
        System.out.println(Arrays.toString(availableRooms(RoomType.TWIN, LocalDate.parse("2019-04-08"), LocalDate.parse("2019-04-10"))));
        //System.out.println(isAvailable(104, LocalDate.parse("2019-04-01"), LocalDate.parse("2019-04-10")));
    }

    public static boolean importRoomsData(String roomsTxtFileName){
        BufferedReader br = null;
        String line = "";
        String splitBy = ",";

        try {
            br = new BufferedReader(new FileReader(roomsTxtFileName));
            while((line = br.readLine()) != null){
                String[] room = line.split(splitBy);

                int roomNum = Integer.parseInt(room[0]);

                RoomType type;
                switch(room[1].toUpperCase()){
                    case "DOUBLE":
                        type = RoomType.DOUBLE;
                        break;
                    case "SINGLE":
                        type = RoomType.SINGLE;
                        break;
                    case "TWIN":
                        type = RoomType.TWIN;
                        break;
                    case "FAMILY":
                        type = RoomType.FAMILY;
                        break;
                    default:
                        throw new Exception("Invalid room type");
                }

                double price = Double.parseDouble(room[2]);
                int capacity = Integer.parseInt(room[3]);
                String facilities = room[4];

                rooms.add(new Room(roomNum, type, price, capacity, facilities));
            }

            br.close();
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static boolean importGuestsData(String guestsTxtFileName){
        BufferedReader br = null;
        String line = "";
        String splitBy = ",";

        try {
            br = new BufferedReader(new FileReader(guestsTxtFileName));
            while ((line = br.readLine()) != null) {
                String[] guest = line.split(splitBy);

                int guestID = Integer.parseInt(guest[0]);
                String guestName = guest[1];
                String guestSurname = guest[2];
                LocalDate guestDateJoin = LocalDate.parse(guest[3]);

                if(guest.length > 4) {
                    LocalDate guestVIPStartDate = LocalDate.parse(guest[4]);
                    LocalDate guestVIPExpiryDate = LocalDate.parse(guest[5]);
                    guests.add(new VIPGuest(guestID, guestName, guestSurname, guestDateJoin, guestVIPStartDate, guestVIPExpiryDate));
                }else {
                    guests.add(new Guest(guestID, guestName, guestSurname, guestDateJoin));
                }
            }
            br.close();
            return true;
        } catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public static boolean importBookingsData(String bookingsTxtFileName){
        BufferedReader br = null;
        String line = "";
        String splitBy = ",";

        try {
            br = new BufferedReader(new FileReader(bookingsTxtFileName));
            while ((line = br.readLine()) != null) {
                String[] booking = line.split(splitBy);

                int bookingID = Integer.parseInt(booking[0]);
                int bookingGuestID = Integer.parseInt(booking[1]);
                int bookingRoomNumber = Integer.parseInt(booking[2]);
                LocalDate bookingBookingDate = LocalDate.parse(booking[3]);
                LocalDate bookingCheckInDate = LocalDate.parse(booking[4]);
                LocalDate bookingCheckOutDate = LocalDate.parse(booking[5]);
                double bookingTotalAmount = Double.parseDouble(booking[6]);

                bookings.add(new Booking(bookingID, bookingGuestID, bookingRoomNumber, bookingBookingDate, bookingCheckInDate, bookingCheckOutDate, bookingTotalAmount));
            }
            } catch (Exception e){
                System.out.println(e);
                return false;
            }
        return true;
    }

    public static boolean importPaymentsData(String paymentsTxtFileName){
        BufferedReader br = null;
        String line = "";
        String splitBy = ",";

        try {
            br = new BufferedReader(new FileReader(paymentsTxtFileName));
            while ((line = br.readLine()) != null) {
                String[] payment = line.split(splitBy);

                LocalDate paymentDate = LocalDate.parse(payment[0]);
                int paymentGuestID = Integer.parseInt(payment[1]);
                double paymentTotalAmount = Double.parseDouble(payment[2]);
                String paymentReason = payment[3];

                payments.add(new Payment(paymentDate, paymentGuestID, paymentTotalAmount, paymentReason));
            }
        } catch (Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }

    public static void displayAllRooms() {
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("%5s %20s %10s %5s %30s\n", "Number", "Type", "Price", "Capacity", "Facilities");
        for (Room room : rooms) {
            //System.out.println(room.roomNumber+" "+room.roomType.toString()+" "+room.roomPrice+" "+room.roomCapacity+" "+room.roomFacilities);
            System.out.format("%5d %20s %10f %5d %30s\n", room.roomNumber, room.roomType, room.roomPrice, room.roomCapacity, room.roomFacilities);
        }
        System.out.println("-----------------------------------------------------------------------------------");
    }

    public static void displayAllGuests() {
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        System.out.printf("%5s %15s %15s %15s %20s %20s\n", "ID", "Name", "Surname", "Join Date", "VIP Start Date", "VIP End Date");
        for (Guest guest : guests) {
            //System.out.println(room.roomNumber+" "+room.roomType.toString()+" "+room.roomPrice+" "+room.roomCapacity+" "+room.roomFacilities);
            System.out.format("%5s %15s %15s %15s", guest.guestID, guest.guestName, guest.guestSurname, guest.guestDateJoin);

            if(guest instanceof VIPGuest){
                System.out.format("%20s %20s\n", ((VIPGuest)guest).VIPstartDate, ((VIPGuest)guest).VIPexpiryDate);
            }else{
                System.out.format("\n");
            }
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
    }

    public static void displayAllBookings(){
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("%10s %10s %10s %15s %15s %15s %15s\n", "ID", "Guest ID", "Room Number", "Booking Date", "Check-in Date", "Check-out Date", "Total amount");
        for (Booking booking : bookings) {
            //System.out.println(room.roomNumber+" "+room.roomType.toString()+" "+room.roomPrice+" "+room.roomCapacity+" "+room.roomFacilities);
            System.out.format("%10d %10d %10d %15s %15s %15s %15f\n", booking.bookingID, booking.bookingGuestID, booking.bookingRoomNumber, booking.bookingBookingDate, booking.bookingCheckInDate, booking.bookingCheckOutDate, booking.bookingTotalAmount);
        }
        System.out.println("-----------------------------------------------------------------------------------");
    }

    public static void displayAllPayments(){
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("%15s %15s %15s %25s\n", "Date", "Guest ID", "Total Amount", "Reason");
        for (Payment payment : payments) {
            //System.out.println(room.roomNumber+" "+room.roomType.toString()+" "+room.roomPrice+" "+room.roomCapacity+" "+room.roomFacilities);
            System.out.format("%15s %15d %15f %25s\n", payment.paymentDate, payment.paymentGuestID, payment.paymentTotalAmount, payment.paymentReason);
        }
        System.out.println("-----------------------------------------------------------------------------------");
    }

    public static boolean isAvailable(int roomNumber, LocalDate checkin, LocalDate checkout){
        for(Booking booking : bookings){
            if(roomNumber == booking.bookingRoomNumber) {
                if(checkin.compareTo(booking.bookingCheckInDate) < 0) {
                    //Checkin is before checkin
                    if (checkout.compareTo(booking.bookingCheckInDate) <= 0) {
                        //System.out.println("checkin before checkin, checkout before checkin");
                        return true;
                    }else if(checkout.compareTo(booking.bookingCheckInDate) >= 0){
                        //System.out.println("checkin before checkin, checkout after checkin");
                        return false;
                    }
                }else if(checkin.compareTo(booking.bookingCheckOutDate) >= 0){
                    //Checkin is after checkout
                    //System.out.println("checkin is after target checkout");
                    return true;
                }
            }
        }
        return false;
    }

    public static int[] availableRooms(RoomType roomType, LocalDate checkin, LocalDate checkout){

        List<Room> roomsOfType = new ArrayList<>();

        for(Room room : rooms){
            if(room.roomType == roomType){
                roomsOfType.add(room);
            }
        }

        for(Room room : roomsOfType){
            if(isAvailable(room.roomNumber, checkin, checkout) == false){
                roomsOfType.remove(room);
            }
        }

        int returnArray[] = new int[roomsOfType.size()];
        int count = 0;
        for(Room room : roomsOfType){
            returnArray[count] = room.roomNumber;
            count++;
        }

        return returnArray;
    }

    public static boolean addRoom(int roomNumber, RoomType roomType, double price, int capacity, String facilities) {
        boolean contains = false;
        for(Room room : rooms) {
            if (room.roomNumber == roomNumber) {
                contains = true;
            }
        }
        if (contains == false){
            try {
                rooms.add(new Room(roomNumber, roomType, price, capacity, facilities));
                return true;
            }
            catch(Exception e) {
                System.out.println(e);
                return false;
            }
        }else {
            return false;
        }
    }

    public static boolean removeRoom(int roomNumber) {
        boolean contains = false;
        for (Booking book : bookings) {
            if (book.bookingRoomNumber == roomNumber) {
                contains = true;
            }
        }
        if (contains == false) {
            try {
                for (Room room : rooms) {
                    if (room.roomNumber == roomNumber) {
                        rooms.remove(room);
                        return true;
                    }
                }
            }catch (Exception e) {
                System.out.println(e);
                return false;
            }

        } else {
            return false;
        }
        return false;
    }

    public static boolean addGuest(String guestName, String guestSurname, LocalDate guestDateJoin) {
        try {
            guests.add(new Guest(newGuestID(),guestName, guestSurname, guestDateJoin));
            return true;
        }
        catch(Exception e) {
            System.out.println(e);
            return false;
        }
    }

    private static int newGuestID(){
        return (guests.get(guests.size()-1).guestID + 1);
    }

    public static boolean addGuest(String guestName,String guestSurname,LocalDate guestDateJoin, LocalDate guestVIPStartDate,LocalDate guestVIPExpiryDate) {
        try {
            guests.add(new VIPGuest(newGuestID(),guestName, guestSurname, guestDateJoin, guestVIPStartDate, guestVIPExpiryDate));
            return true;
        }
        catch(Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static boolean removeGuest(int guestID){
        boolean contains = false;
        LocalDate now = LocalDate.now();
        for (Booking book : bookings) {
            if (book.bookingGuestID == guestID && book.bookingCheckInDate.compareTo(now) > 0) {
                contains = true;
                System.out.println("Guest Still has Future Bookings");
                return false;
            }

        }
        if (contains == false) {
            try {
                for (Guest hotelGuest : guests) {
                    if (hotelGuest.guestID == guestID) {
                        guests.remove(hotelGuest);
                        return true;
                    }
                }
            }catch (Exception e) {
                System.out.println(e);
                return false;
            }

        } else {
            return false;
        }
        return false;

    }

}


