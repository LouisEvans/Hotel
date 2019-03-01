package hotel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
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

    public static boolean addRoom(int roomNumber, RoomType roomType, double price, int capacity, String facilities) {
        Boolean contains = false;
        for(Room room : rooms) {
            if (room.roomNumber == roomNumber) {
                contains = true;
            }
        }
        if (contains = false){
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
}

