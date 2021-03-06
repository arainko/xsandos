package com.arainko.xno.model.predicates;

import com.arainko.xno.model.elements.Cell;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class CellPredicates {
    public static Predicate<Cell> containingCross() {
        return c -> c.getCellContents() == Cell.Contents.CROSS;
    }

    public static Predicate<Cell> containingCircle() {
        return c -> c.getCellContents() == Cell.Contents.CIRCLE;
    }

    public static Predicate<Cell> partOfConnection() {
        return Cell::getConnectionFlag;
    }

    public static BiPredicate<Cell, Cell> nextToOnPlaneY() {
        return (c1, c2) -> Math.abs(c1.getCordY()-c2.getCordY()) == 1 && c1.getCordX()-c2.getCordX() == 0;
    }

    public static BiPredicate<Cell, Cell> nextToOnPlaneX() {
        return (c1, c2) -> Math.abs(c1.getCordX()-c2.getCordX()) == 1 && c1.getCordY()-c2.getCordY() == 0;
    }

    public static BiPredicate<Cell, Cell> connectedByJoint() {
        return (c1, c2) -> c1.getCordX() != c2.getCordX() && c1.getCordY() != c2.getCordY();
    }

}
