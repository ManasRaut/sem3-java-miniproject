package java_miniproject;

import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public interface Styles {
    Color primary_violet = new Color(151, 81, 188);
    Color primary_white = new Color(249, 249, 249);
    Color primary_button_color = new Color(179, 128, 206);
    Font text_field_font = new Font("Calibri", Font.PLAIN, 18);
    Font big_label_font = new Font("Roboto", Font.BOLD, 28);
    Font normal_label_font = new Font("Calibri", Font.PLAIN, 14);
    Border text_field_border = new CompoundBorder(new LineBorder(new Color(207, 176, 225), 2), new EmptyBorder(3, 2, 1, 2));
    Border black_button_border = new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(5, 15, 5, 15));
    Border white_button_border = new CompoundBorder(new LineBorder(Color.WHITE), new EmptyBorder(7, 20, 7, 20));
    Border red_warning_border = new CompoundBorder(new LineBorder(new Color(234, 55, 60), 2), new EmptyBorder(3, 2, 1, 2));
}