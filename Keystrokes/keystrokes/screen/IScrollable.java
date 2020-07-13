package keystrokes.screen;

public interface IScrollable
{
    double getAmount();
    
    void onScroll(final double p0, final int p1);
}
