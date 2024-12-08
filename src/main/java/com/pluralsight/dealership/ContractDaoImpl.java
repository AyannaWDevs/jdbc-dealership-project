package com.pluralsight.dealership;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContractDaoImpl implements ContractDao {
    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;

    public ContractDaoImpl(String dbUrl, String dbUsername, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    @Override
    public boolean addContract(Contract contract) {
        String query = "INSERT INTO contracts (vin, customer_id, contract_date, details, salesperson_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, contract.getVin());
            stmt.setInt(2, contract.getCustomerId());
            stmt.setDate(3, Date.valueOf(contract.getContractDate()));
            stmt.setString(4, contract.getDetails());
            stmt.setInt(5, contract.getSalespersonId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Contract> getAllContracts() {
        String query = "SELECT * FROM contracts";
        List<Contract> contracts = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                contracts.add(new Contract(
                        rs.getInt("id"),
                        rs.getString("vin"),
                        rs.getInt("customer_id"),
                        rs.getDate("contract_date").toLocalDate(),
                        rs.getString("details"),
                        rs.getInt("salesperson_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contracts;
    }

    @Override
    public Contract getContractById(int id) {
        String query = "SELECT * FROM contracts WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Contract(
                            rs.getInt("id"),
                            rs.getString("vin"),
                            rs.getInt("customer_id"),
                            rs.getDate("contract_date").toLocalDate(),
                            rs.getString("details"),
                            rs.getInt("salesperson_id")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateContractDetails(int id, String details) {
        String query = "UPDATE contracts SET details = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, details);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteContract(int id) {
        String query = "DELETE FROM contracts WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
