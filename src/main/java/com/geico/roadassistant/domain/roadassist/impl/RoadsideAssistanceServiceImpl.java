package com.geico.roadassistant.domain.roadassist.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.geico.roadassistant.domain.assistant.Assistant;
import com.geico.roadassistant.domain.customer.Customer;
import com.geico.roadassistant.domain.location.Geolocation;
import com.geico.roadassistant.domain.roadassist.RoadsideAssistanceService;

public class RoadsideAssistanceServiceImpl implements RoadsideAssistanceService {

    private Map<Assistant, Geolocation> assistantLocations = new HashMap<>();
    private Map<Customer, Assistant> customerAssistantMap = new HashMap<>();

    public RoadsideAssistanceServiceImpl() {
    }

    @Override
    public void updateAssistantLocation(Assistant assistant, Geolocation assistantLocation) {
        assistantLocations.put(assistant, assistantLocation);
    }

    @Override
    public SortedSet<Assistant> findNearestAssistants(Geolocation geolocation, int limit) {
        // Create a set to hold the roadside assistants ordered by distance
        Comparator<Assistant> byDistance = Comparator.comparingDouble(a -> a.getLocation().distanceTo(geolocation));
        SortedSet<Assistant> assistantsByDistance = new TreeSet<>(byDistance);

        // Add all the assistants and their locations to the set
        for (Assistant assistant : assistantLocations.keySet()) {
            assistantsByDistance.add(assistant);
        }

        // Return a subset of the set limited by the specified number of assistants
        return assistantsByDistance.stream().limit(limit).collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public Optional<Assistant> reserveAssistant(Customer customer, Geolocation customerLocation) {
        // Find the nearest available assistant to the customer's location
        SortedSet<Assistant> nearestAssistants = findNearestAssistants(customerLocation, 1);

        // Check if there is an available assistant
        if (nearestAssistants.isEmpty()) {
            return Optional.empty();
        }

        // Reserve the nearest assistant for the customer
        Assistant assistant = nearestAssistants.first();
        customerAssistantMap.put(customer, assistant);
        return Optional.of(assistant);
    }

    @Override
    public void releaseAssistant(Customer customer, Assistant assistant) {
        // Remove the customer's reservation of the assistant
        customerAssistantMap.remove(customer);
    }

    // Define a comparator to order assistants by their distance from a given geolocation
    private static class DistanceCalculator implements Comparator<Assistant> {
        private Geolocation geolocation;

        public DistanceCalculator(Geolocation geolocation) {
            this.geolocation = geolocation;
        }

        @Override
        public int compare(Assistant a1, Assistant a2) {
            double distance1 = a1.getLocation().distanceTo(geolocation);
            double distance2 = a2.getLocation().distanceTo(geolocation);
            return Double.compare(distance1, distance2);
        }
    }
}

