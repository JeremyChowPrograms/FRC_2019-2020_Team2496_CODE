package frc.robot;

import edu.wpi.first.wpilibj.util.Color;

public class ColorSwitch {

    public enum PossibleColor {
        RED, BLUE, YELLOW, GREEN, WHITE, WTF
    }

    private ColorSwitch() {
    }

    public static PossibleColor inputColor(Color color) {
        if (color.green > color.blue) {
            if (color.red > color.green)
                return PossibleColor.RED;
            if (color.red > color.blue)
                return PossibleColor.YELLOW;
            if (color.red < color.blue)
                return PossibleColor.GREEN;
        } else if (color.blue > color.green && color.green > color.red)
            return PossibleColor.BLUE;
        return PossibleColor.WTF;
    }
}