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
        Assistant assistant = new Assistant("Sarah", new Geolocation(34.0204, -84.1713));
        Geolocation location = new Geolocation(34.0204, -84.1713);
        service.updateAssistantLocation(assistant, location);

        assertEquals(location, assistant.getLocation());
    }

    @Test
    public void testFindNearestAssistants() {
        RoadsideAssistanceService service = new RoadsideAssistanceServiceImpl();
        Geolocation searchLocation = new Geolocation(34.0204, -84.1823);
        Assistant assistant1 = new Assistant("David", new Geolocation(34.0197, -84.1829));
        Assistant assistant2 = new Assistant("Sarah", new Geolocation(34.0214, -84.1714));
        service.updateAssistantLocation(assistant1, assistant1.getLocation());
        service.updateAssistantLocation(assistant2, assistant2.getLocation());
        SortedSet<Assistant> assistants = service.findNearestAssistants(searchLocation, 1);
        assertEquals(1, assistants.size());
        assertTrue(assistants.contains(assistant1));
    }

    @Test
    void testReserveAssistant() {
        Assistant assistant = new Assistant("David", new Geolocation(34.0197, -84.1829));
        Customer customer = new Customer("dave.smith@email.com", "Dave", "Smith");
        Geolocation customerLocation = new Geolocation(34.0204, -84.1823);
        
        service.updateAssistantLocation(assistant, customerLocation);
        Optional<Assistant> reservedAssistant = service.reserveAssistant(customer, customerLocation);

        assertTrue(reservedAssistant.isPresent());
        assertEquals(assistant, reservedAssistant.get());
    }

    @Test
    void testReleaseAssistant() {
        Assistant assistant = new Assistant("David", new Geolocation(34.0197, -84.1829));
        Customer customer = new Customer("dave.smith@email.com", "Dave", "Smith");
        Geolocation customerLocation = new Geolocation(34.0224, -84.1713);
        
        service.updateAssistantLocation(assistant, customerLocation);
        service.reserveAssistant(customer, customerLocation);
        service.releaseAssistant(customer, assistant);

        assertFalse(service.findNearestAssistants(customerLocation, 1).contains(assistant));
    }
}
