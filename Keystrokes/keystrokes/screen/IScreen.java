package keystrokes.screen;

public interface IScreen
{
    default int calculateHeight(final int row) {
        return 55 + row * 23;
    }
}
