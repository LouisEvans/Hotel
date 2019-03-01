package hotel;

import java.time.LocalDate;

class VIPGuest extends Guest {
    LocalDate VIPstartDate;
    LocalDate VIPexpiryDate;

    public VIPGuest(int id, String guestName, String guestSurname, LocalDate guestDateJoin, LocalDate guestVIPStart, LocalDate guestVIPExpiry) {
        super(id, guestName, guestSurname, guestDateJoin);
        VIPstartDate = guestVIPStart;
        VIPexpiryDate = guestVIPExpiry;
    }
}