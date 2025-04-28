package org.example.Model;

public class Cell {

    public enum cells {
        empty,
        filled
    }

    private cells status;
    private String colour;

    void setStatus(cells statusNew) {
        status = statusNew;
    }

    public cells getStatus() {
        return status;
    }

    void setColour(String colourNew) {
        colour = colourNew;
    }

    public String getColour() {
        return colour;
    }
}
