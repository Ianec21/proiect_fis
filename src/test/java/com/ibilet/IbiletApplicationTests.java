package com.ibilet;

import com.ibilet.controllers.FlightFilterController;
import com.ibilet.entities.FlightDTO;
import com.ibilet.services.FlightFilterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class IbiletApplicationTests {

	@Mock
	private FlightFilterService flightFilterService;

	@Mock
	private Model model;

	@InjectMocks
	private FlightFilterController flightFilterController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

    @Nested
    public class Tests<Ianec>{
		@Test
		void testSearchFlightsRedirectsToBookingWhenArrivalIsNull() throws ExecutionException, InterruptedException {
			String result = flightFilterController.searchFlights(model, null, "reservationType", "2023-05-01", "2023-05-10", 2, 1, "departure", "OneWay");
			assertEquals("redirect:/booking", result);
		}

		@Test
		void testSearchFlightsRedirectsToBookingWhenDepartureIsNull() throws ExecutionException, InterruptedException {
			String result = flightFilterController.searchFlights(model, "arrival", "reservationType", "2023-05-01", "2023-05-10", 2, 1, null, "OneWay");
			assertEquals("redirect:/booking", result);
		}

		@Test
		void testSearchFlightsRedirectsToBookingWhenDepartureDateIsNull() throws ExecutionException, InterruptedException {
			String result = flightFilterController.searchFlights(model, "arrival", "reservationType", null, "2023-05-10", 2, 1, "departure", "OneWay");
			assertEquals("redirect:/booking", result);
		}

		@Test
		void testSearchFlightsRedirectsToBookingWhenArrivalDateIsNull() throws ExecutionException, InterruptedException {
			String result = flightFilterController.searchFlights(model, "arrival", "reservationType", "2023-05-01", null, 2, 1, "departure", "OneWay");
			assertEquals("redirect:/booking", result);
		}

		@Test
		void testOpenBookingReturnsBooking() {
			String result = flightFilterController.openBooking();
			assertEquals("booking", result);
		}

		@Test
		void testSearchFlightsReturnsSearchWhenArrivalIsNull() throws ExecutionException, InterruptedException {
			String result = flightFilterController.searchFlights(model, null, "reservationType", "2023-05-01", "2023-05-10", 2, 1, "departure", "OneWay");
			assertEquals("search", result); // This will fail because the actual result is "redirect:/booking"
		}
	}

}
