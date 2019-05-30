package be.pxl.student.repositories;

import be.pxl.student.model.Label;
import be.pxl.student.exceptions.LabelException;
import be.pxl.student.utils.ConnectionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LabelJpaTest {
    private LabelJpa dao;

    @Before
    public void beforeTests() throws SQLException {
        EntityManager entityManager =
                Persistence.createEntityManagerFactory("budgetplannerTest").createEntityManager();
        this.dao = new LabelJpa(entityManager);
    }

    @After
    public void afterTests() throws SQLException {
        this.dao = null;
    }

    @Test
    public void addLabel_shouldAddALabel_Label() throws LabelException {
        //Arrange
        Label seededLabel = dao.addLabel(new Label("TestLabel", "A Label for test purposes"));

        Assert.assertNotNull(seededLabel);
        Assert.assertNotEquals(seededLabel.getId(), 0);
    }

    @Test
    public void getAllLabels_shouldReturnListOfLabels() throws LabelException {
        //Arrange
        Label seededLabel = dao.addLabel(new Label("TestLabel", "A Label for test purposes"));

        //Act
        List<Label> foundLabels = dao.getAllLabels();

        //Assert
        Assert.assertNotNull(foundLabels);
        Assert.assertEquals(1, foundLabels.size());
    }

    @Test
    public void getById_shouldReturnOneLabelWithGivenId_Label() throws LabelException {
        //Arrange
        Label seededLabel = dao.addLabel(new Label("TestLabel", "A Label for test purposes"));
        Assert.assertNotEquals(seededLabel.getId(), 0);

        //Act
        Label actual = dao.getById(seededLabel.getId());

        //Assert
        Assert.assertEquals(seededLabel, actual);
        LabelException exception = null;
        try{
            dao.getById(-1);
        }
        catch (LabelException labelException){
            exception=labelException;
        }

        Assert.assertNotNull(exception);
        Assert.assertEquals(exception.getMessage(),"No Label found with id: -1");
    }

    @Test
    public void updateLabel_shouldUpdateAllFieldsOfALabel_int() throws LabelException {
        //Arrange
        Label seededLabel = dao.addLabel(new Label("TestLabel", "A Label for test purposes"));
        Assert.assertNotNull(seededLabel);

        //Act
        seededLabel.setName("new name");
        seededLabel.setDescription("New description");

        Label updateLabel = dao.updateLabel(seededLabel);
        Assert.assertNotNull(updateLabel);

        Label actualLabel = dao.getById(seededLabel.getId());

        //Assert
        Assert.assertNotNull(actualLabel);
        Assert.assertEquals(seededLabel, actualLabel);
    }

}
