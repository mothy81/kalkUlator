package com.empro.kalk_u_lator.data;

import java.io.Serializable;

public class LayersList implements Serializable
{
    private String material;
    private int lambdaValue;
    private int layerThickness;
    private int rValue;

    public LayersList(String material, int lambdaValue, int layerThickness, int rValue) {
        this.material = material;
        this.lambdaValue = lambdaValue;
        this.layerThickness = layerThickness;
        this.rValue = rValue;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getLambdaValue() {
        return lambdaValue;
    }

    public void setLambdaValue(int lambdaValue) {
        this.lambdaValue = lambdaValue;
    }

    public int getLayerThickness() {
        return layerThickness;
    }

    public void setLayerThickness(int layerThickness) {
        this.layerThickness = layerThickness;
    }

    public int getrValue() {
        return rValue;
    }

    public void setrValue(int rValue) {
        this.rValue = rValue;
    }
}
