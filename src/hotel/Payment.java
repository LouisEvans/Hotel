package hotel;

import java.time.LocalDate;

public class Payment {
    LocalDate paymentDate;
    int paymentGuestID;
    double paymentTotalAmount;
    String paymentReason;



    public Payment(LocalDate date, int guestID, double totalAmount, String reason){
        paymentDate = date;
        paymentGuestID = guestID;
        paymentTotalAmount = totalAmount;
        paymentReason = reason;
    }
}