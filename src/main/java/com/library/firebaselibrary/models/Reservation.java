package com.library.firebaselibrary.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    private String reservationId;
    private String bookId;
    private String userId;
    private Date reservationStartDate;
    private Date reservationEndDate;
    private Date returnedDate;
}
