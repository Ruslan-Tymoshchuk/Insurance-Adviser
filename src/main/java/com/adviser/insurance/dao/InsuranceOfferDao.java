package com.adviser.insurance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.adviser.insurance.domain.InsuranceOffer;
import com.adviser.insurance.domain.Vehicle;
import com.adviser.insurance.util.ConnectionManager;

public class InsuranceOfferDao {

    public static final String GET_INSURANCE_OFFERS_BY_USER_QUERY = "SELECT v.brand, v.model, io.insurer, io.price FROM vehicles v "
            + "LEFT JOIN users u ON u.id = v.user_id "
            + "LEFT JOIN vehicles_insurance_offers vio ON vio.vehicle_id = v.id "
            + "LEFT JOIN insurance_offers io ON io.id = vio.insurance_offer_id WHERE u.nick = ? "
            + "ORDER BY v.model, io.price";
    
    private final ConnectionManager connectionManager;
    
    public InsuranceOfferDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }
    
    public List<InsuranceOffer> findOffersByUserName(String userName) throws DaoException {
        try (Connection connection = connectionManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_INSURANCE_OFFERS_BY_USER_QUERY)) {
            statement.setString(1, userName);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<InsuranceOffer> offers = new ArrayList<>();
                while (resultSet.next()) {
                    offers.add(mapToInsuranceOffer(resultSet));
                }
                return offers;
            }
        } catch (SQLException e) {
            throw new DaoException("Error occur when searching by " + userName, e);
        }
    }
    
    private InsuranceOffer mapToInsuranceOffer(ResultSet resultSet) throws SQLException {
        InsuranceOffer insuranceOffer = new InsuranceOffer();
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(resultSet.getString("brand"));
        vehicle.setModel(resultSet.getString("model"));
        insuranceOffer.setVehicle(vehicle);
        insuranceOffer.setInsurer(resultSet.getString("insurer"));
        insuranceOffer.setPrice(resultSet.getLong("price"));
        return insuranceOffer;
    }
}