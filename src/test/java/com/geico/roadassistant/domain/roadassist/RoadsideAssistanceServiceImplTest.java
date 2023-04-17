package com.geico.roadassistant.domain.roadassist;

import org.junit.jupiter.api.*;

import com.geico.roadassistant.domain.assistant.Assistant;
import com.geico.roadassistant.domain.customer.Customer;
import com.geico.roadassistant.domain.location.Geolocation;
import com.geico.roadassistant.domain.roadassist.impl.RoadsideAssistanceServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.SortedSet;

class RoadsideAssistanceServiceImplTest {
    private static RoadsideAssistanceService service;

    @BeforeAll
    static void setUp() {
        service = new RoadsideAssistanceServiceImpl();
    }

    @Test
    void testUpdateAssistantLocation() {
        Assistant assistant = new Assistant("Jim", new Geolocation(34.0204, -84.1713));
        Geolocation location = new Geolocation(34.0204, -84.1713);
        service.updateAssistantLocation(assistant, location);

        assertEquals(location, assistant.getLocation());
    }

    @Test
    void testFindNearestAssistants() {
        Assistant assistant1 = new Assistant("David", new Geolocation(34.0204, -84.1714));
        Geolocation location1 = new Geolocation(34.0204, -84.1723);
        service.updateAssistantLocation(assistant1, location1);

        Assistant assistant2 = new Assistant("Sarah", new Geolocation(34.0214, -84.1714));
        Geolocation location2 = new Geolocation(34.0304, -84.1725);
        service.updateAssistantLocation(assistant2, location2);

        Geolocation searchLocation = new Geolocation(34.0204, -84.1823);
        int limit = 1;
        SortedSet<Assistant> nearestAssistants = service.findNearestAssistants(searchLocation, limit);

        assertEquals(limit, nearestAssistants.size());
        assertEquals(assistant1, nearestAssistants.first());
    }

    @Test
    void testReserveAssistant() {
        Assistant assistant = new Assistant("David", new Geolocation(34.0204, -84.1714));
        Geolocation location = new Geolocation(37.7749, -122.4194);
        service.updateAssistantLocation(assistant, location);

        Customer customer = new Customer("dave.smith@email.com", "Dave", "Smith");
        Geolocation customerLocation = new Geolocation(34.0224, -84.1713);
        Optional<Assistant> reservedAssistant = service.reserveAssistant(customer, customerLocation);

        assertTrue(reservedAssistant.isPresent());
        assertEquals(assistant, reservedAssistant.get());
    }

    @Test
    void testReleaseAssistant() {
        Assistant assistant = new Assistant("David", new Geolocation(34.0204, -84.1714));
        Geolocation location = new Geolocation(37.7749, -122.4194);
        service.updateAssistantLocation(assistant, location);

        Customer customer = new Customer("dave.smith@email.com", "Dave", "Smith");
        Geolocation customerLocation = new Geolocation(37.7765, -122.4166);
        service.reserveAssistant(customer, customerLocation);

        service.releaseAssistant(customer, assistant);

        assertFalse(service.findNearestAssistants(customerLocation, 1).contains(assistant));
    }
}
