package edu.uga.dawgtrades.model;

public interface AttributeType
        extends Persistable {

    String getName();

    void setName(String name);

    long getCategoryId();

    void setCategoryId(long categoryId);
}
