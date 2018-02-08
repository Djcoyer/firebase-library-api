package com.library.firebaselibrary.models.dao;
import java.util.Date;

public class ReservationDao {
    private String reservationId;
    private String bookId;
    private String userId;
    private Date reservationStartDate;
    private Date reservationEndDate;
    private Date returnedDate;

    public ReservationDao() {
    }

    public ReservationDao(String reservationId, String bookId, String userId,
                          Date reservationStartDate, Date reservationEndDate, Date returnedDate) {
        this.bookId = bookId;
        this.reservationEndDate = reservationEndDate;
        this.reservationId = reservationId;
        this.reservationStartDate = reservationStartDate;
        this.userId = userId;
        this.returnedDate = returnedDate;
    }


    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getReservationStartDate() {
        return reservationStartDate;
    }

    public void setReservationStartDate(Date reservationStartDate) {
        this.reservationStartDate = reservationStartDate;
    }

    public Date getReservationEndDate() {
        return reservationEndDate;
    }

    public void setReservationEndDate(Date reservationEndDate) {
        this.reservationEndDate = reservationEndDate;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }
}
