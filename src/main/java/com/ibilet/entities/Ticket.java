package com.ibilet.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Ticket {
    public enum PaymentMethod {
        CASH, CARD, IBAN
    }

    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private int age;
    private PaymentMethod paymentMethod;
    private String flightId;
}
