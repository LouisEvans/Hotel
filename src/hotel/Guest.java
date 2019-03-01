package hotel;

import java.time.LocalDate;

public class Guest {
    int guestID;
    String guestName;
    String guestSurname;
    LocalDate guestDateJoin;

    public Guest(int id, String name, String surname, LocalDate dateJoin){
        guestID = id;
        guestName = name;
        guestSurname = surname;
        guestDateJoin = dateJoin;
    }
}