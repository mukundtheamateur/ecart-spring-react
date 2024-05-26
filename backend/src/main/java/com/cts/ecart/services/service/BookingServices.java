package com.cts.ecart.services.service;

import com.cts.ecart.entity.Booking;
import com.cts.ecart.exceptions.AlreadyExistsException;
import com.cts.ecart.exceptions.NotFoundException;

import java.util.List;

public interface BookingServices {

    List<Booking> getBookingsByUserId(Integer id) throws NotFoundException;

    Booking saveBooking(Booking booking) throws NotFoundException;

    Booking getBookingById(Integer id) throws NotFoundException;

    Booking updateBooking(Booking booking, Integer id) throws NotFoundException;

    void deleteBooking(Integer id) throws NotFoundException;

    List<Booking> getAllBookings() throws NotFoundException;
}
