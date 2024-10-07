import java.awt.*;

public class Screen_Size {

    static Dimension size= Toolkit.getDefaultToolkit().getScreenSize();

    // width will store the width of the screen
    static int width = (int)size.getWidth();

    // height will store the height of the screen
    static int height = (int)size.getHeight();

}
