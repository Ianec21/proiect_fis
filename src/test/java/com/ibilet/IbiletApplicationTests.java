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
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IbiletApplicationTests {

    @Nested
    class FlightFilterControllerTest {

		@Mock
		private FlightFilterService flightFilterService;

		@InjectMocks
		private FlightFilterController flightFilterController;

		private Model model;

		@BeforeEach
		public void setUp() {
			MockitoAnnotations.openMocks(this);
			model = new BindingAwareModelMap();
		}

		@Test
		public void testSearchFlightsValidInput() throws ExecutionException, InterruptedException {
			List<FlightDTO> mockFlights = new ArrayList<>();
			when(flightFilterService.FilterFlights(anyString(), anyString(), anyString(), anyString(), anyInt(), anyInt(), anyString(), anyString()))
					.thenReturn(mockFlights);

			String viewName = flightFilterController.searchFlights(model, "NYC", "Economy", "2024-07-10", "2024-07-20", 2, 1, "LAX", "RoundTrip");

			assertEquals("search", viewName);
			assertEquals(mockFlights, model.getAttribute("flights"));
			verify(flightFilterService, times(1)).FilterFlights("NYC", "Economy", "2024-07-10", "2024-07-20", 1, 2, "LAX", "RoundTrip");
		}

		@Test
		public void testSearchFlightsMissingArrival() throws ExecutionException, InterruptedException {
			String viewName = flightFilterController.searchFlights(model, null, "Economy", "2024-07-10", "2024-07-20", 2, 1, "LAX", "RoundTrip");

			assertEquals("redirect:/booking", viewName);
		}

		@Test
		public void testSearchFlightsMissingDepartureDate() throws ExecutionException, InterruptedException {
			String viewName = flightFilterController.searchFlights(model, "NYC", "Economy", null, "2024-07-20", 2, 1, "LAX", "RoundTrip");

			assertEquals("redirect:/booking", viewName);
		}

		@Test
		public void testSearchFlightsMissingDeparture() throws ExecutionException, InterruptedException {
			String viewName = flightFilterController.searchFlights(model, "NYC", "Economy", "2024-07-10", "2024-07-20", 2, 1, null, "RoundTrip");

			assertEquals("redirect:/booking", viewName);
		}
		@Test
		public void testSearchFlightsMissingReservationType() throws ExecutionException, InterruptedException {
			String viewName = flightFilterController.searchFlights(model, "NYC", null, "2024-07-10", "2024-07-20", 2, 1, "LAX", "RoundTrip");

			assertEquals("redirect:/booking", viewName);
		}
	}
}
