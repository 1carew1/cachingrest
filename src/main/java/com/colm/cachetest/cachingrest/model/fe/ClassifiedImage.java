package com.colm.cachetest.cachingrest.model.fe;

import java.io.Serializable;

public class ClassifiedImage implements Serializable {
    private String label;
    private float probability;
    private byte[] imageDataPoints;

    public ClassifiedImage(String label, float probability, byte[] imageDataPoints) {
        this.label = label;
        this.probability = probability;
        this.imageDataPoints = imageDataPoints;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float getProbability() {
        return probability;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }

    public byte[] getImageDataPoints() {
        return imageDataPoints;
    }

    public void setImageDataPoints(byte[] imageDataPoints) {
        this.imageDataPoints = imageDataPoints;
    }
}
