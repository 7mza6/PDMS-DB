package Componentes.Route;

import static PDMS.application.Application.connn;
import java.sql.*;
import java.util.ArrayList;

public class RouteCRUD {

    private static final Connection CONN = connn();

    // Read all routes from the database
    public static ArrayList<Route> readRoutes() {
        ArrayList<Route> routeList = new ArrayList<>();
        String sql = "SELECT * FROM Routes";
        try (Statement stmt = CONN.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Route route = new Route(
                    rs.getInt("id"),
                    rs.getString("origin"),
                    rs.getString("destination"),
                    rs.getFloat("distance"),
                    null, // CitiesClass مش مستخدم في الكود الحالي
                    rs.getString("estimatedTravelTime")
                );
                routeList.add(route);
            }
        } catch (SQLException e) {
            System.out.println("Error reading routes: " + e.getMessage());
        }
        return routeList;
    }

    // Not used anymore (JSON)
    public static void writeRoutes(ArrayList<Route> routeList) {
        // Not needed when using DB
    }

    // Create new route
    public static void createRoute(Route route) {
        String sql = "INSERT INTO Routes (id, origin, destination, distance, estimatedTravelTime) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
            stmt.setInt(1, route.getId());
            stmt.setString(2, route.getOrigin());
            stmt.setString(3, route.getDestination());
            stmt.setFloat(4, route.getDistance());
            stmt.setString(5, route.getEstimatedTravelTime());
            stmt.executeUpdate();
            System.out.println("Route created successfully!");
        } catch (SQLException e) {
            System.out.println("Error creating route: " + e.getMessage());
        }
    }

    // Read route by ID
    public static Route readRoute(int id) {
        String sql = "SELECT * FROM Routes WHERE id = ?";
        try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Route(
                        rs.getInt("id"),
                        rs.getString("origin"),
                        rs.getString("destination"),
                        rs.getFloat("distance"),
                        null, // CitiesClass
                        rs.getString("estimatedTravelTime")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error reading route: " + e.getMessage());
        }
        return null;
    }

    // Update route by ID
    public static void updateRoute(int id, Route updatedRoute) {
        String sql = "UPDATE Routes SET origin = ?, destination = ?, distance = ?, estimatedTravelTime = ? WHERE id = ?";
        try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
            stmt.setString(1, updatedRoute.getOrigin());
            stmt.setString(2, updatedRoute.getDestination());
            stmt.setFloat(3, updatedRoute.getDistance());
            stmt.setString(4, updatedRoute.getEstimatedTravelTime());
            stmt.setInt(5, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Route updated successfully.");
            } else {
                System.out.println("Route not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating route: " + e.getMessage());
        }
    }

    // Delete route by ID
    public static void deleteRoute(int id) {
        String sql = "DELETE FROM Routes WHERE id = ?";
        try (PreparedStatement stmt = CONN.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Route deleted successfully.");
            } else {
                System.out.println("Route not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting route: " + e.getMessage());
        }
    }

    // Get route by ID (same as readRoute)
    public static Route getRouteById(int routeId) {
        return readRoute(routeId);
    }
}