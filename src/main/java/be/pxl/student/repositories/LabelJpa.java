package be.pxl.student.repositories;

import be.pxl.student.model.Label;
import be.pxl.student.exceptions.LabelException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LabelJpa {

    private EntityManager entityManager;

    public LabelJpa(EntityManager entityManager) {

        this.entityManager = entityManager;
    }

    public Label addLabel(Label label) throws LabelException {
        entityManager.getTransaction().begin();
        entityManager.persist(label);
        entityManager.getTransaction().commit();
        return entityManager.find(Label.class, label.getId());
    }

    public List<Label> getAllLabels() throws LabelException {
        TypedQuery<Label> query = entityManager.createNamedQuery("Label.findAll", Label.class);
        return query.getResultList();
    }

    public Label getById(int id) throws LabelException {
        Label label = entityManager.find(Label.class, id);
        if (label != null) {
            return label;
        }

        throw new LabelException("No Label found with id: " + id);
    }

    public Label updateLabel(Label label) throws LabelException {
        entityManager.getTransaction().begin();
        entityManager.merge(label);
        entityManager.getTransaction().commit();
        return entityManager.find(Label.class, label.getId());
    }
}
