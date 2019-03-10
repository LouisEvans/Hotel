package hotel;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.time.temporal.ChronoUnit.DAYS;

abstract class HotelImpl implements hotel.Hotel{

    private static List<Room> rooms = new ArrayList<>();
    private static List<Guest> guests = new ArrayList<>();
    private static List<Booking> bookings = new ArrayList<>();
    private static List<Payment> payments = new ArrayList<>();

    /*public void main(String args[]){

        //System.out.println(importRoomsData("src/data/rooms.txt"));
        //System.out.println(importGuestsData("src/data/guests.txt"));
        //System.out.println(importBookingsData("src/data/bookings.txt"));
        //System.out.println(importPaymentsData("src/data/payments.txt"));

        //displayAllRooms();
        //addRoom(123,RoomType.DOUBLE,123.00,2,"no bathroom");
        //displayAllRooms();

        //displayAllRooms();
        //displayAllBookings();
        //System.out.println(Arrays.toString(availableRooms(RoomType.FAMILY, LocalDate.parse("2019-04-04"), LocalDate.parse("2019-04-10"))));
        //System.out.println(isAvailable(102, LocalDate.parse("2019-04-01"), LocalDate.parse("2019-04-10")));

        //displayAllGuests();
        //System.out.println(Arrays.toString(searchGuest("Sarah","Hoopern")));
        //displayGuestBooking(10001);

        //displayBookingsOn(LocalDate.parse("2019-04-01"));
        //displayPaymentsOn(LocalDate.parse("2019-02-11"));
    }*/

    public boolean importRoomsData(String roomsTxtFileName){
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

    public boolean importGuestsData(String guestsTxtFileName){
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

    public boolean importBookingsData(String bookingsTxtFileName){
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

    public boolean importPaymentsData(String paymentsTxtFileName){
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

    public void displayAllRooms() {
        //Display heading. Set column widths
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("%5s %20s %10s %5s %30s\n", "Number", "Type", "Price", "Capacity", "Facilities");

        //For each room, print correct data
        for (Room room : rooms) {
            System.out.format("%5d %20s %10f %5d %30s\n", room.roomNumber, room.roomType, room.roomPrice, room.roomCapacity, room.roomFacilities);
        }
        System.out.println("-----------------------------------------------------------------------------------");
    }

    public void displayAllGuests() {
        //Display heading. Set column widths
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        System.out.printf("%5s %15s %15s %15s %20s %20s\n", "ID", "Name", "Surname", "Join Date", "VIP Start Date", "VIP End Date");

        //For each guest, print correct data
        for (Guest guest : guests) {
            System.out.format("%5s %15s %15s %15s", guest.guestID, guest.guestName, guest.guestSurname, guest.guestDateJoin);

            //If guest is a VIP, print last two columns as well
            if(guest instanceof VIPGuest){
                System.out.format("%20s %20s\n", ((VIPGuest)guest).VIPstartDate, ((VIPGuest)guest).VIPexpiryDate);
            }else{
                System.out.format("\n");
            }
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
    }

    public void displayAllBookings(){
        //Display heading. Set column widths
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("%10s %10s %10s %15s %15s %15s %15s\n", "ID", "Guest ID", "Room Number", "Booking Date", "Check-in Date", "Check-out Date", "Total amount");

        //For each booking, print correct data
        for (Booking booking : bookings) {
            System.out.format("%10d %10d %10d %15s %15s %15s %15f\n", booking.bookingID, booking.bookingGuestID, booking.bookingRoomNumber, booking.bookingBookingDate, booking.bookingCheckInDate, booking.bookingCheckOutDate, booking.bookingTotalAmount);
        }
        System.out.println("-----------------------------------------------------------------------------------");
    }

    public void displayAllPayments(){
        //Display heading. Set column widths
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("%15s %15s %15s %25s\n", "Date", "Guest ID", "Total Amount", "Reason");

        //For each payment, print correct data
        for (Payment payment : payments) {
            System.out.format("%15s %15d %15f %25s\n", payment.paymentDate, payment.paymentGuestID, payment.paymentTotalAmount, payment.paymentReason);
        }
        System.out.println("-----------------------------------------------------------------------------------");
    }

    public boolean isAvailable(int roomNumber, LocalDate checkin, LocalDate checkout){

        //For each booking, if it is the right booking number and overlaps with
        //another booking, then return false. Else, return true
        for(Booking booking : bookings) {
            if (roomNumber == booking.bookingRoomNumber) {
                if(((checkin.isAfter(booking.bookingCheckInDate) || checkin.equals(booking.bookingCheckInDate)) && checkin.isBefore(booking.bookingCheckOutDate)) || (checkin.isBefore(booking.bookingCheckInDate) && checkout.isAfter(booking.bookingCheckInDate))){
                    return false;
                }
            }
        }
        return true;
    }

    public int[] availableRooms(RoomType roomType, LocalDate checkin, LocalDate checkout){

        //Get all rooms of a certain RoomType, and remove the ones which obtain
        //false from isAvailable()
        List<Room> roomsOfType = new ArrayList<>();
        List<Room> roomsToRemove = new ArrayList<>();
        for(Room room : rooms){
            if(room.roomType == roomType){
                roomsOfType.add(room);
            }
        }
        for (Room room : roomsOfType) {
            if (isAvailable(room.roomNumber, checkin, checkout) == false) {
                roomsToRemove.add(room);
            }
        }
        roomsOfType.removeAll(roomsToRemove);

        //Add all room numbers to array
        int returnArray[] = new int[roomsOfType.size()];
        int count = 0;
        for(Room room : roomsOfType){
            returnArray[count] = room.roomNumber;
            count++;
        }

        return returnArray;
    }

    public int[] searchGuest(String firstName, String lastName){

        //Get all guest IDs of guests who match firstName and lastName
        List<Integer> guestIDs = new ArrayList<>();
        for(Guest guest : guests){
            if(guest.guestName.equals(firstName) && guest.guestSurname.equals(lastName)){
                guestIDs.add(guest.guestID);
            }
        }

        //Add guests to array
        int[] returnArray = new int[guestIDs.size()];
        int count = 0;
        for(Integer guestID : guestIDs){
            returnArray[count] = guestID;
        }

        return returnArray;
    }

    public void displayGuestBooking(int guestID){

        //Display heading. Set column widths
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("%10s %10s %10s %15s %15s %15s %15s\n", "ID", "Guest ID", "Room Number", "Booking Date", "Check-in Date", "Check-out Date", "Total amount");

        //For each booking, print correct data
        for(Booking booking : bookings){
            if(booking.bookingGuestID == guestID){
                System.out.format("%10d %10d %10d %15s %15s %15s %15f\n", booking.bookingID, booking.bookingGuestID, booking.bookingRoomNumber, booking.bookingBookingDate, booking.bookingCheckInDate, booking.bookingCheckOutDate, booking.bookingTotalAmount);
            }
        }
        System.out.println("-----------------------------------------------------------------------------------");
    }

    public void displayBookingsOn(LocalDate thisDate) {

        //Get all bookings on a given date
        List<Booking> bookingsToDisplay = new ArrayList<>();
        List<Booking> bookingsToRemove = new ArrayList<>();
        for (Booking booking : bookings) {
            if (isAvailable(booking.bookingRoomNumber, thisDate, thisDate) == false) {
                bookingsToDisplay.add(booking);
            }
        }
        for (Booking booking : bookingsToDisplay) {
            if((booking.bookingCheckOutDate.isBefore(thisDate) || booking.bookingCheckOutDate.isEqual(thisDate)) || (booking.bookingCheckInDate.isAfter(thisDate))) {
                bookingsToRemove.add(booking);
            }
        }
        bookingsToDisplay.removeAll(bookingsToRemove);

        //Display heading. Set column widths
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("%10s %15s %15s %10s %15s %15s %15s\n", "Guest ID", "Name", "Surname", "Room Number", "Room Type", "Room Price", "Payment Price");

        //For each booking on given date, find guest data and room data
        for(Booking booking : bookingsToDisplay) {
            //Set defaults
            String name = "";
            String surname = "";
            boolean vip = true;
            double paymentPrice = 0f;
            int guestID = booking.bookingGuestID;

            //Find guest data
            for(Guest guest : guests){
                if(guestID == guest.guestID){
                    name = guest.guestName;
                    surname = guest.guestSurname;

                    if(guest instanceof VIPGuest){
                        vip = true;
                    }else{
                        vip = false;
                    }
                }
            }

            //Find room type, price
            RoomType type = RoomType.DOUBLE;
            double price = 0f;

            int roomNum = booking.bookingRoomNumber;
            for(Room room : rooms){
                if(roomNum == room.roomNumber){
                    type = room.roomType;
                    price = room.roomPrice;
                }
            }

            //Add discount if VIP
            if(vip){
                paymentPrice = price * 0.9;
            }else{
                paymentPrice = price;
            }

            //Print correct data
            System.out.printf("%10d %15s %15s %10d %15s %15f %15f\n", booking.bookingGuestID, name, surname, booking.bookingRoomNumber, type, price, paymentPrice);
            }
        System.out.println("-----------------------------------------------------------------------------------");
    }

    public void displayPaymentsOn(LocalDate thisDate){
        //Display heading. Set column widths
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("%10s %15s %15s\n", "Guest ID", "Payment Amount", "Reason");

        //For each payment, print correct data
        for(Payment payment : payments){
            if(payment.paymentDate.equals(thisDate)){
                System.out.printf("%10d %15f %15s\n", payment.paymentGuestID, payment.paymentTotalAmount, payment.paymentReason);
            }
        }
        System.out.println("-----------------------------------------------------------------------------------");
    }

    public boolean addRoom(int roomNumber, RoomType roomType, double price, int capacity, String facilities) {
        // Checks if the room already exists
        boolean contains = false;
        for(Room room : rooms) {
            if (room.roomNumber == roomNumber) {
                contains = true;
            }
        }
        // Adds the room if it dosent already exist
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

    public boolean removeRoom(int roomNumber) {
        // Checks if the room has a booking
        boolean contains = false;
        for (Booking book : bookings) {
            if (book.bookingRoomNumber == roomNumber) {
                contains = true;
            }
        }
        // If it dosent it can be removed
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

    public boolean addGuest(String guestName, String guestSurname, LocalDate guestDateJoin) {
        // Adds 1 to the last guestID
        int newGuestID = guests.get(guests.size()-1).guestID + 1;
        try {
            // Adds a new guest with the parameters
            guests.add(new Guest(newGuestID,guestName, guestSurname, guestDateJoin));
            return true;
        }
        catch(Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean addGuest(String guestName,String guestSurname,LocalDate guestDateJoin, LocalDate guestVIPStartDate,LocalDate guestVIPExpiryDate) {
        // Adds 1 to the last guestID
        int newGuestID = guests.get(guests.size()-1).guestID + 1;
        // Adds a VIP guest with the parameters
        try {
            guests.add(new VIPGuest(newGuestID,guestName, guestSurname, guestDateJoin, guestVIPStartDate, guestVIPExpiryDate));
            return true;
        }
        catch(Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean removeGuest(int guestID){
        boolean contains = false;
        LocalDate now = LocalDate.now();
        // Checks if the guest has any future bookings
        for (Booking book : bookings) {
            if (book.bookingGuestID == guestID && book.bookingCheckInDate.compareTo(now) > 0) {
                contains = true;
                System.out.println("Guest Still has Future Bookings");
                return false;
            }

        }
        // Removes the guest if they dont have a booking
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

    public int bookOneRoom(int guestID, RoomType roomType, LocalDate checkin, LocalDate checkout){
        // Adds 1 to the last BookingID
        int newBookingID = bookings.get(bookings.size()-1).bookingID + 1;
        int typeAvliable[] = availableRooms(roomType,checkin,checkout);
        // Checks if the type of room is avaliable
        if (typeAvliable.length == 0){
            return -1;
        } else {
            try{
                // Picks a random room and calculates the duration
                int rnd = new Random().nextInt(typeAvliable.length);
                double duration = DAYS.between(checkin, checkout);
                boolean isInstance = false;
                double price = 0f ;
                for (Room room: rooms){
                    // Price of the room
                    if (room.roomNumber == typeAvliable[rnd]){
                        price = room.roomPrice;
                    }
                }
                // Checks if the guest is a VIP
                for (Guest guest: guests){
                    if (guest.guestID == guestID){
                        if (guest instanceof VIPGuest) {
                            isInstance = true;
                        }
                    }
                }
                // Creates a new booking
                if (isInstance == true){
                    double bookingTotalAmount = 0.9* duration * price;
                    bookings.add(new Booking(newBookingID, guestID, typeAvliable[rnd], LocalDate.now(), checkin, checkout, bookingTotalAmount));
                    return typeAvliable[rnd];
                } else {
                    double bookingTotalAmount = duration * price;
                    bookings.add(new Booking(newBookingID, guestID, typeAvliable[rnd], LocalDate.now(), checkin, checkout, bookingTotalAmount));
                    return typeAvliable[rnd];
                }
            } catch (Exception e) {
                System.out.println(e);
                return -1;
            }
        }

    }

    public boolean checkOut(int bookingID,LocalDate actualCheckoutDate){
        // Checks that the booking ID is the right one
        // Checks the checkOutDate is before or is on the check out date.
        try {
            for (Booking booking : bookings) {
                if (booking.bookingID == bookingID && booking.bookingCheckOutDate.compareTo(actualCheckoutDate) >= 0 && actualCheckoutDate.compareTo(booking.bookingCheckInDate) >= 0) {
                    bookings.remove(booking);
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid Booking ID");
            return false;
        }
        System.out.println("Request Denied");
        return false;
    }

    public boolean cancelBooking(int bookingID){
        // Checks the cancel date is 2 days before the checkin date
        LocalDate now = LocalDate.now();
        try {
            for (Booking booking : bookings) {
                // Checks the difference in the epoch days is > 1
                // Removes booking and adds a new payment
                if (booking.bookingID == bookingID && booking.bookingCheckInDate.toEpochDay()-now.toEpochDay()>1) {
                    double refund = -booking.bookingTotalAmount;
                    payments.add(new Payment(now, booking.bookingGuestID, refund, "refund"));
                    bookings.remove(booking);
                    return true;
                }

            }
        } catch (Exception e) {

            System.out.println(e);
            return false;
        }
        return false;
    }

    public boolean saveRoomsData(String roomsTxtFileName){
        // Over writes the old file and adds the new data into the file
        try{
            PrintWriter pw = new PrintWriter(roomsTxtFileName);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(roomsTxtFileName));
            for (Room room : rooms) {
                bufferedWriter.write(""+room.roomNumber+","+room.roomType+","+room.roomPrice+","+room.roomCapacity+","+room.roomFacilities);
                bufferedWriter.newLine();
            }
            // Closes the file
            bufferedWriter.close();
            return true;

        } catch (Exception e){
            return false;
        }
    }

    public boolean saveGuestsData(String roomsTxtFileName){
        // Over writes the old file and adds the new data into the file
        try{
            PrintWriter pw = new PrintWriter(roomsTxtFileName);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(roomsTxtFileName));
            for (Guest guest : guests) {
                if (guest instanceof VIPGuest) {
                    bufferedWriter.write(guest.guestID + "," + guest.guestName + "," + guest.guestSurname + "," + guest.guestDateJoin+","+((VIPGuest) guest).VIPstartDate+","+((VIPGuest) guest).VIPexpiryDate);
                    bufferedWriter.newLine();
                } else {
                    bufferedWriter.write(guest.guestID + "," + guest.guestName + "," + guest.guestSurname + "," + guest.guestDateJoin);
                    bufferedWriter.newLine();
                }
            }
            // Closes the file
            bufferedWriter.close();
            return true;

        } catch (Exception e){
            return false;
        }
    }

    public boolean saveBookingsData(String roomsTxtFileName){
        // Over writes the old file and adds the new data into the file
        try{
            PrintWriter pw = new PrintWriter(roomsTxtFileName);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(roomsTxtFileName));
            for (Booking booking : bookings) {
                bufferedWriter.write(booking.bookingID+","+booking.bookingGuestID+","+ booking.bookingRoomNumber+"," +booking.bookingBookingDate+","+ booking.bookingCheckInDate+"," +booking.bookingCheckOutDate+","+ booking.bookingTotalAmount);
                bufferedWriter.newLine();
            }
            // Closes the file
            bufferedWriter.close();
            return true;

        } catch (Exception e){
            return false;
        }
    }

    public boolean savePaymentsData(String roomsTxtFileName){
        // Over writes the old file and adds the new data into the file
        try{
            PrintWriter pw = new PrintWriter(roomsTxtFileName);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(roomsTxtFileName));
            for (Payment payment : payments) {
                bufferedWriter.write(payment.paymentDate+","+ payment.paymentGuestID+","+ payment.paymentTotalAmount+","+ payment.paymentReason);
                bufferedWriter.newLine();
            }
            // Closes the file
            bufferedWriter.close();
            return true;

        } catch (Exception e){
            return false;
        }
    }

}



