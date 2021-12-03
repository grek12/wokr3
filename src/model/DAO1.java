package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO1 {
    private Connection connection;

    public DAO1() {

        connection = new ConnectionFactory().getConnection();
    }

    public void insertClient(Sotr sotr) {
        String sql = "insert into sotr (fam,oklad,otrday) values (?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, sotr.getFam());
            statement.setDouble(2, sotr.getOklad());
            statement.setInt(3, sotr.getOtrday());
            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void updateClient(Sotr sotr) {

        String sql = "update sotr set fam=?, oklad=?, otrday=? where id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, sotr.getFam());
            statement.setDouble(2, sotr.getOklad());
            statement.setInt(3, sotr.getOtrday());
            statement.setInt(4, sotr.getId());

            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void deleteSotr(Integer idSotr) {

        String sql = "delete from sotr where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, idSotr);

            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public ObservableList<Sotr> selectSotr() {

        ObservableList<Sotr> sotrs = FXCollections.observableArrayList();

        String sql = "select * from sotr ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Sotr sotr = new Sotr();

                sotr.setId(resultSet.getInt("id"));
                sotr.setFam(resultSet.getString("fam"));
                sotr.setOklad(resultSet.getDouble("oklad"));
                sotr.setOtrday(resultSet.getInt("otrday"));

                sotrs.add(sotr);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return sotrs;
    }
    public ObservableList<Calculation> selectSotrItog() {

        ObservableList<Calculation> calculations = FXCollections.observableArrayList();

        String sql = "SELECT fam, ((oklad * otrday)/20) AS nachisleno, ((oklad * otrday)/20)*0.13 AS tax, ((oklad * otrday)/20)- (((oklad * otrday)/20)*0.13) as vidacha\n" +
                "from sotr; ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Calculation calculation = new Calculation();
                calculation.setFam(resultSet.getString("fam"));
                calculation.setNachisleno(resultSet.getDouble("nachisleno"));
                calculation.setTax(resultSet.getDouble("tax"));
                calculation.setVidach(resultSet.getDouble("vidacha"));

                calculations.add(calculation);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return calculations;
    }
}
