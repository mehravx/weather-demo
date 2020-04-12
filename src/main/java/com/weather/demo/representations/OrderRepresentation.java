package com.weather.demo.representations;

import com.weather.demo.models.OrderModel;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrderRepresentation {

    private Integer column;
    private Direction dir;

    public OrderRepresentation() {
    }

    public OrderRepresentation(Integer column, Direction dir) {
        this.column = column;
        this.dir = dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderRepresentation orderRepresentation = (OrderRepresentation) o;
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

    public static Function<OrderRepresentation, OrderModel> toOrderModel = orderRepresentation ->
            new OrderModel(
                    orderRepresentation.getColumn(),
                    orderRepresentation.getDir() == null ?
                            com.weather.demo.models.Direction.ASCENDING :
                            com.weather.demo.models.Direction.fromDescription(orderRepresentation.getDir().name()).orElse(com.weather.demo.models.Direction.ASCENDING)
            );

    public static Function<List<OrderRepresentation>, List<OrderModel>> toOrderModels = orderRepresentations ->
            orderRepresentations.stream().map(toOrderModel).collect(Collectors.toList());
}
