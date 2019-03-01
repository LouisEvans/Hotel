package hotel;

import java.time.LocalDate;

public class Booking {
    int bookingID;
    int bookingGuestID;
    int bookingRoomNumber;
    LocalDate bookingBookingDate;
    LocalDate bookingCheckInDate;
    LocalDate bookingCheckOutDate;
    double bookingTotalAmount;


    public Booking(int id, int guestID, int roomNumber, LocalDate bookingDate, LocalDate checkInDate, LocalDate checkOutDate, double totalAmount){
        bookingID = id;
        bookingGuestID = roomNumber;
        bookingRoomNumber = roomNumber;
        bookingBookingDate = bookingDate;
        bookingCheckInDate = checkInDate;
        bookingCheckOutDate = checkOutDate;
        bookingTotalAmount = totalAmount;
    }
}
