Roadside Assistance Service
===========================

This project implements a roadside assistance service that provides assistance to customers stranded on the roadside due to a disabled vehicle. The service maintains a collection of roadside assistants, their current locations, and their availability to help customers. When a customer requests assistance, the service finds the nearest available assistant and reserves them for the customer. Once the assistant arrives at the customer's location and completes the work, the service releases the assistant.

Getting Started
---------------

To use this service, you can clone this repository and import the project into your favorite Java IDE. Alternatively, you can use a build tool like Maven or Gradle to include the project as a dependency in your own project.

### Prerequisites

*   Java 11
*   An IDE  (e.g. IntelliJ IDEA, Eclipse)
*  Maven build tool

### Installing

1.  Clone this repository:

    git clone https://github.com/aabuniaj/roadside-assistance-service.git

3.  Open the project in your Java IDE or build the project using maven.

Usage
-----

### Updating the Location of a Roadside Assistant

To update the location of a roadside assistant, you can call the `updateAssistantLocation` method of the `RoadsideAssistanceService`:

    Assistant assistant = // Get the assistant to update
    Geolocation location = // Get the new location of the assistant
    service.updateAssistantLocation(assistant, location);

### Finding the Nearest Roadside Assistants

To find the nearest roadside assistants to a given location, you can call the `findNearestAssistants` method of the `RoadsideAssistanceService`:

    Geolocation location = // The location to search from
    int limit = // The maximum number of assistants to return
    SortedSet<Assistant> nearestAssistants = service.findNearestAssistants(location, limit);

### Reserving a Roadside Assistant

To reserve a roadside assistant for a customer, you can call the `reserveAssistant` method of the `RoadsideAssistanceService`:

    Customer customer = // The customer who needs assistance
    Geolocation location = // The location of the customer
    Optional<Assistant> reservedAssistant = service.reserveAssistant(customer, location);

If an available assistant is found, the method returns an `Optional` containing the assistant. If no available assistant is found, the method returns an empty `Optional`.

### Releasing a Roadside Assistant

To release a reserved roadside assistant, you can call the `releaseAssistant` method of the `Roadside`
