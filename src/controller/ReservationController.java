package controller;

import dao.ReservationDAO;
import java.time.LocalDate;
import java.util.List;
import model.Reservation;
import model.RoomType;

/**
 * Controller for reservation business logic.
 */
public class ReservationController {
    private final ReservationDAO reservationDAO;

    public ReservationController() {
        this.reservationDAO = new ReservationDAO();
    }

    /**
     * Create a new reservation.
     * @return the created reservation, or null if failed
     */
    public Reservation createReservation(String guestName, String address, String contactNumber,
                                          RoomType roomType, LocalDate checkInDate, LocalDate checkOutDate) {
        // Validate inputs
        String validationError = validateReservationInput(guestName, address, contactNumber,
                                                          checkInDate, checkOutDate);
        if (validationError != null) {
            throw new IllegalArgumentException(validationError);
        }

        // Generate reservation number
        String reservationNumber = reservationDAO.generateReservationNumber();

        // Create reservation object
        Reservation reservation = new Reservation(
            reservationNumber,
            guestName.trim(),
            address.trim(),
            contactNumber.trim(),
            roomType,
            checkInDate,
            checkOutDate
        );

        // Save to database
        if (reservationDAO.create(reservation)) {
            return reservation;
        }
        return null;
    }

    /**
     * Find a reservation by its number.
     */
    public Reservation findReservation(String reservationNumber) {
        if (reservationNumber == null || reservationNumber.trim().isEmpty()) {
            return null;
        }
        return reservationDAO.findByReservationNumber(reservationNumber.trim());
    }

    /**
     * Get all reservations.
     */
    public List<Reservation> getAllReservations() {
        return reservationDAO.findAll();
    }

    /**
     * Search reservations by guest name.
     */
    public List<Reservation> searchByGuestName(String guestName) {
        if (guestName == null || guestName.trim().isEmpty()) {
            return reservationDAO.findAll();
        }
        return reservationDAO.searchByGuestName(guestName.trim());
    }

    /**
     * Update an existing reservation.
     */
    public boolean updateReservation(Reservation reservation) {
        if (reservation == null || reservation.getReservationNumber() == null) {
            return false;
        }
        return reservationDAO.update(reservation);
    }

    /**
     * Delete a reservation.
     */
    public boolean deleteReservation(String reservationNumber) {
        if (reservationNumber == null || reservationNumber.trim().isEmpty()) {
            return false;
        }
        return reservationDAO.delete(reservationNumber.trim());
    }

    /**
     * Calculate the bill for a reservation.
     * @return formatted bill string
     */
    public String generateBill(Reservation reservation) {
        if (reservation == null) {
            return null;
        }

        long nights = reservation.getNumberOfNights();
        int ratePerNight = reservation.getRoomType().getPricePerNight();
        long totalAmount = reservation.getTotalAmount();

        StringBuilder bill = new StringBuilder();
        bill.append("═══════════════════════════════════════════════════════\n");
        bill.append("              OCEAN VIEW RESORT - GALLE                \n");
        bill.append("                    GUEST BILL                         \n");
        bill.append("═══════════════════════════════════════════════════════\n\n");

        bill.append(String.format("Reservation No: %s\n", reservation.getReservationNumber()));
        bill.append(String.format("Date: %s\n\n", LocalDate.now()));

        bill.append("───────────────────────────────────────────────────────\n");
        bill.append("GUEST DETAILS\n");
        bill.append("───────────────────────────────────────────────────────\n");
        bill.append(String.format("Name: %s\n", reservation.getGuestName()));
        bill.append(String.format("Address: %s\n", reservation.getAddress()));
        bill.append(String.format("Contact: %s\n\n", reservation.getContactNumber()));

        bill.append("───────────────────────────────────────────────────────\n");
        bill.append("ROOM DETAILS\n");
        bill.append("───────────────────────────────────────────────────────\n");
        bill.append(String.format("Room Type: %s\n", reservation.getRoomType().getDisplayName()));
        bill.append(String.format("Check-in Date: %s\n", reservation.getCheckInDate()));
        bill.append(String.format("Check-out Date: %s\n", reservation.getCheckOutDate()));
        bill.append(String.format("Number of Nights: %d\n\n", nights));

        bill.append("───────────────────────────────────────────────────────\n");
        bill.append("CHARGES\n");
        bill.append("───────────────────────────────────────────────────────\n");
        bill.append(String.format("Rate per Night: LKR %,d\n", ratePerNight));
        bill.append(String.format("Total Nights: %d\n", nights));
        bill.append(String.format("─────────────────────────────────────────\n"));
        bill.append(String.format("TOTAL AMOUNT: LKR %,d\n", totalAmount));
        bill.append("═══════════════════════════════════════════════════════\n\n");

        bill.append("Thank you for choosing Ocean View Resort!\n");
        bill.append("We hope to see you again soon.\n");

        return bill.toString();
    }

    /**
     * Validate reservation input fields.
     * @return error message if validation fails, null if valid
     */
    private String validateReservationInput(String guestName, String address, String contactNumber,
                                            LocalDate checkInDate, LocalDate checkOutDate) {
        if (guestName == null || guestName.trim().isEmpty()) {
            return "Guest name is required";
        }
        if (address == null || address.trim().isEmpty()) {
            return "Address is required";
        }
        if (contactNumber == null || contactNumber.trim().isEmpty()) {
            return "Contact number is required";
        }
        if (!isValidContactNumber(contactNumber)) {
            return "Invalid contact number format";
        }
        if (checkInDate == null) {
            return "Check-in date is required";
        }
        if (checkOutDate == null) {
            return "Check-out date is required";
        }
        if (checkOutDate.isBefore(checkInDate) || checkOutDate.isEqual(checkInDate)) {
            return "Check-out date must be after check-in date";
        }
        if (checkInDate.isBefore(LocalDate.now())) {
            return "Check-in date cannot be in the past";
        }
        return null;
    }

    /**
     * Validate contact number format.
     */
    private boolean isValidContactNumber(String contactNumber) {
        // Allow digits, spaces, hyphens, plus sign, and parentheses
        String cleaned = contactNumber.replaceAll("[\\s\\-\\(\\)\\+]", "");
        return cleaned.matches("\\d{9,15}");
    }
}
