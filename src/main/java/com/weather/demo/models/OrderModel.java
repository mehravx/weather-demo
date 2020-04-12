package com.weather.demo.models;

import java.util.Objects;

public class OrderModel {

    private Integer column;
    private Direction dir;

    public OrderModel() {
    }

    public OrderModel(Integer column, Direction dir) {
        this.column = column;
        this.dir = dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderModel orderRepresentation = (OrderModel) o;
        return column.equals(orderRepresentation.column) &&
                dir == orderRepresentation.dir;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, dir);
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

}
