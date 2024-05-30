package com.ibilet;

import com.ibilet.controllers.AuthController;
import com.ibilet.controllers.FlightController;
import com.ibilet.controllers.FlightFilterController;
import com.ibilet.entities.Flight;
import com.ibilet.entities.FlightDTO;
import com.ibilet.entities.User;
import com.ibilet.services.FlightFilterService;
import com.ibilet.services.FlightService;
import com.ibilet.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import java.util.ArrayList;
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

	@Mock
	private FlightService flightService;

	@Mock
	private HttpSession session;

	@InjectMocks
	private FlightController flightController;

	@Mock
	private UserService userService;

	@InjectMocks
	private AuthController authController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

    @Nested
    public class TestsIanec{
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
			assertEquals("redirect:/booking", result); // This will fail because the actual result is "redirect:/booking"
		}
	}

	@Nested
	public class TestsValentin {

		@Test
		void testShowAddFlightFormReturnsAddFlight() {
			String result = flightController.showAddFlightForm(model);
			assertEquals("add-flight", result);
		}

		@Test
		void testShowFlightsReturnsFlights() throws ExecutionException, InterruptedException {
			List<FlightDTO> flights = new ArrayList<>();
			when(flightService.getAllFlights()).thenReturn(flights);

			String result = flightController.showFlights(model);
			assertEquals("flights", result);
			verify(model).addAttribute("flights", flights);
		}

		@Test
		void testAddFlightRedirectsToHomeWhenUserNotLoggedIn() throws ExecutionException, InterruptedException {
			when(session.getAttribute("loggedInUser")).thenReturn(null);
			String result = flightController.addFlight("planeType", 100, 20, 200.0, 500.0, "departureCity", "arrivalCity", "10:00", "12:00", "2023-05-01", "2023-05-10", 10.0, 15.0, "OneWay", session, model);
			assertEquals("redirect:/", result);
		}

		@Test
		void testAddFlightRedirectsToAddFlightWhenValidationFails() throws ExecutionException, InterruptedException {
			User user = new User();
			user.setId("user-id");
			user.setRole(User.Role.AIRLINE);
			when(session.getAttribute("loggedInUser")).thenReturn(user);

			String result = flightController.addFlight("", 100, 20, 200.0, 500.0, "departureCity", "arrivalCity", "10:00", "12:00", "2023-05-01", "2023-05-10", 10.0, 15.0, "OneWay", session, model);
			assertEquals("redirect:/add-flight", result);
		}

		@Test
		void testAddFlightCreatesFlightAndRedirectsToHome() throws ExecutionException, InterruptedException {
			User user = new User();
			user.setId("user-id");
			user.setRole(User.Role.AIRLINE);
			when(session.getAttribute("loggedInUser")).thenReturn(user);

			String result = flightController.addFlight("planeType", 100, 20, 200.0, 500.0, "departureCity", "arrivalCity", "10:00", "12:00", "2023-05-01", "2023-05-10", 10.0, 15.0, "OneWay", session, model);
			assertEquals("redirect:/", result);
			verify(flightService).createFlight(any(Flight.class));
		}
	}

	@Nested
	public class TestsCalin {

		@Test
		void testLoginPageReturnsLoginView() {
			String result = authController.login(model);
			assertEquals("login", result);
		}

		@Test
		void testLogoutInvalidatesSessionAndRedirectsToLogin() {
			String result = authController.logout(model, session);
			verify(session, times(1)).invalidate();
			assertEquals("redirect:/login", result);
		}

		@Test
		void testLoginUserWithValidCredentialsAndClientRole() throws ExecutionException, InterruptedException {
			User user = new User();
			user.setUsername("clientUser");
			user.setPassword("password123");
			user.setRole(User.Role.CLIENT);

			when(userService.getUserByUsername(anyString())).thenReturn(user);

			String result = authController.loginUser("clientUser", "password123", model, session);
			verify(session, times(1)).setAttribute("loggedInUser", user);
			assertEquals("redirect:/", result);
		}

		@Test
		void testLoginUserWithValidCredentialsAndAirlineRole() throws ExecutionException, InterruptedException {
			User user = new User();
			user.setUsername("airlineUser");
			user.setPassword("password123");
			user.setRole(User.Role.AIRLINE);

			when(userService.getUserByUsername(anyString())).thenReturn(user);

			String result = authController.loginUser("airlineUser", "password123", model, session);
			verify(session, times(1)).setAttribute("loggedInUser", user);
			assertEquals("redirect:/", result);
		}

		@Test
		void testLoginUserWithInvalidCredentials() throws ExecutionException, InterruptedException {
			when(userService.getUserByUsername(anyString())).thenReturn(null);

			String result = authController.loginUser("invalidUser", "wrongPassword", model, session);
			verify(model, times(1)).addAttribute("error", "Invalid username or password");
			assertEquals("login", result);
		}
	}
}
