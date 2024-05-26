package com.cts.ecart.services.impl;

import com.cts.ecart.entity.Booking;
import com.cts.ecart.entity.Cart;
import com.cts.ecart.entity.User;
import com.cts.ecart.exceptions.NotFoundException;
import com.cts.ecart.repository.BookingRepository;
import com.cts.ecart.repository.CartRepository;
import com.cts.ecart.repository.UserRepository;
import com.cts.ecart.services.service.BookingServices;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BookingServicesImpl implements BookingServices {

    private final BookingRepository bookingsRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    @Autowired
    public BookingServicesImpl(BookingRepository bookingsRepository, CartRepository cartRepository, UserRepository userRepository) {
        this.bookingsRepository = bookingsRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Booking> getBookingsByUserId(Integer userId) throws NotFoundException {
        log.info("Fetching bookings for user with id: {}", userId);
        return bookingsRepository.findByUserId(userId);
    }


    @Transactional
    @Override
    public Booking saveBooking(Booking booking) throws NotFoundException {
        log.info("Saving booking: {}", booking);
        Integer userId = booking.getUser().getId();
        Integer cartId = booking.getCart().getId();

        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Cart> cartOpt = cartRepository.findById(cartId);

        if (userOpt.isEmpty() || cartOpt.isEmpty()) {
            throw new NotFoundException("User or Cart not found");
        }

        User user = userOpt.get();
        Cart cart = cartOpt.get();

        booking.setUser(user);
        booking.setCart(cart);

        return bookingsRepository.save(booking);
    }

    @Override
    public Booking getBookingById(Integer id) throws NotFoundException {
        log.info("Fetching booking with id: {}", id);
        Optional<Booking> booking = bookingsRepository.findById(id);
        if(booking.isPresent()) {
            log.info("Booking fetched successfully with id: {}", id);
            return booking.get();
        }
        throw new NotFoundException("Booking does not exist with this id");
    }

    @Override
    public Booking updateBooking(Booking updatedBooking, Integer id) throws NotFoundException {
        log.info("Updating booking with id: {}", updatedBooking.getId());

        Booking existingBooking = bookingsRepository.findById(updatedBooking.getId())
                .orElseThrow(() -> new NotFoundException("Booking not found with id: " + updatedBooking.getId()));

        // Update the fields of the existing booking
        existingBooking.setUser(updatedBooking.getUser());
        existingBooking.setCart(updatedBooking.getCart());

        // Save the updated booking to the database
        Booking savedBooking = bookingsRepository.save(existingBooking);

        return savedBooking;
    }


    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public void deleteBooking(Integer id) throws NotFoundException {
        // Check if a booking with the given id exists
        if (!bookingRepository.existsById(id)) {
            throw new NotFoundException("Booking not found with id: " + id);
        }

        // Delete the booking
        bookingRepository.deleteById(id);
    }

    @Override
    public List<Booking> getAllBookings() throws NotFoundException {
        // Get all bookings
        List<Booking> bookings = bookingRepository.findAll();

        // If no bookings are found, throw an exception
        if (bookings.isEmpty()) {
            throw new NotFoundException("No bookings found");
        }

        return bookings;
    }

}
