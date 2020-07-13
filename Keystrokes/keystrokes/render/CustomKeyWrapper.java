package keystrokes.render;

import keystrokes.keys.impl.*;

public class CustomKeyWrapper
{
    private final CustomKey theKey;
    private double xOffset;
    private double yOffset;
    
    public CustomKeyWrapper(final CustomKey theKey, final int xOffset, final int yOffset) {
        this.theKey = theKey;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    
    public CustomKey getTheKey() {
        return this.theKey;
    }
    
    public double getXOffset() {
        return this.xOffset;
    }
    
    public void setxOffset(final double xOffset) {
        this.xOffset = xOffset;
    }
    
    public double getyOffset() {
        return this.yOffset;
    }
    
    public void setyOffset(final double yOffset) {
        this.yOffset = yOffset;
    }
}
