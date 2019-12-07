package com.arainko.xno.gamelogic.abstracts;

public abstract class CordPairElement {
    private int cordX;
    private int cordY;

    public CordPairElement(int cordX, int cordY) {
        this.setCordX(cordX);
        this.setCordY(cordY);
    }

    private void setCordX(int cordX) {
        if (cordX >= 0)
            this.cordX = cordX;
        else throw new IllegalStateException("Negative value: cordX = " + cordX);
    }

    private void setCordY(int cordY) {
        if (cordY >= 0)
            this.cordY = cordY;
        else throw new IllegalStateException("Negative value: cordY = " + cordY);
    }

    public int getCordX() {
        return cordX;
    }

    public int getCordY() {
        return cordY;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CordPairElement))
            return false;

        CordPairElement that = (CordPairElement) obj;
        return this.getCordX() == that.getCordX() && this.getCordY() == that.getCordY();
    }

    @Override
    public String toString() {
        return "cordX = " + this.getCordX() + ", cordY = " + this.getCordY();
    }
}

