import edu.school21.model.BasicCalcModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Float.NaN;

public class BasicCalcModelTest {
    BasicCalcModel calc = new BasicCalcModel();
    static final int AROUNDVAR = 10;

    private static double round(double value) {
        if (AROUNDVAR < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(AROUNDVAR, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Test
    public void addition() {
        Assertions.assertEquals( round(100+200+300.0+123),
                calc.getResult("100+200+300.0+123", 0));
        Assertions.assertEquals( round(100+9999.1+3213+123.12312),
                calc.getResult("100+9999.1+3213+123.12312", 0));
        Assertions.assertEquals( round(100+(9999.1+(3213+123.12312))),
                calc.getResult("100+(9999.1+(3213+123.12312))", 0));
        Assertions.assertEquals( round(100+(9999.1+(3213+(123.12312)))),
                calc.getResult("100+(9999.1+(3213+(123.12312)))", 0));
    }

    @Test
    public void degree() {
        Assertions.assertEquals(Math.pow(3, 3), calc.getResult("3^3", 0));
        Assertions.assertEquals(Math.pow(Math.pow(2, 3), 3), calc.getResult("2^3^3", 0));
        Assertions.assertEquals( 3 + Math.pow(3, 3), calc.getResult("3+3^3", 0));
    }

    @Test
    public void subtraction() {
        Assertions.assertEquals(
                round(100-200-300.0-123),
                calc.getResult("100-200-300.0-123", 0));

        Assertions.assertEquals(
                round(100-9999.1-3213-123.12312),
                calc.getResult("100-9999.1-3213-123.12312", 0));

        Assertions.assertEquals(
                round(100-(9999.1-(3213-123.12312))),
                calc.getResult("100-(9999.1-(3213-123.12312))", 0));

        Assertions.assertEquals(
                round(100-(9999.1-(3213-(123.12312)))),
                calc.getResult("100-(9999.1-(3213-(123.12312)))", 0));
    }

    @Test
    public void cos() {
        Assertions.assertEquals(round(Math.cos(1)), calc.getResult("cos(1)", 0));
        Assertions.assertEquals(round(Math.cos(Math.cos(1))), calc.getResult("cos(cos(1))", 0));
        Assertions.assertEquals(round(Math.cos(1) + Math.cos(3)), calc.getResult("cos(1)+cos(3)", 0));
        Assertions.assertEquals(round( Math.cos(Math.cos(1) + Math.cos(3))), calc.getResult(" cos(cos(1)+cos(3))", 0));
    }

    @Test
    public void sin() {
        Assertions.assertEquals(round(Math.sin(1)), calc.getResult("sin(1)", 0));
        Assertions.assertEquals(round(Math.sin(Math.sin(1))), calc.getResult("sin(sin(1))", 0));
        Assertions.assertEquals(round(Math.sin(1) + Math.sin(3)), calc.getResult("sin(1)+sin(3)", 0));
        Assertions.assertEquals(round(Math.sin(Math.sin(1) + Math.sin(3))), calc.getResult(" sin(sin(1)+sin(3))", 0));
    }

    @Test
    public void tan() {
        Assertions.assertEquals(round(Math.tan(1)), calc.getResult("tan(1)", 0));
        Assertions.assertEquals(round(Math.tan(Math.tan(1))), calc.getResult("tan(tan(1))", 0));
        Assertions.assertEquals(round(Math.tan(1) + Math.tan(3)), calc.getResult("tan(1)+tan(3)", 0));
        Assertions.assertEquals(round(Math.tan(Math.tan(1) + Math.tan(3))), calc.getResult(" tan(tan(1)+tan(3))", 0));
    }

    @Test
    public void atan() {
        Assertions.assertEquals(round(Math.atan(1)), calc.getResult("atan(1)", 0));
        Assertions.assertEquals(round(Math.atan(Math.atan(1))), calc.getResult("atan(atan(1))", 0));
        Assertions.assertEquals(round(Math.atan(1) + Math.atan(3)), calc.getResult("atan(1)+atan(3)", 0));
        Assertions.assertEquals(round(Math.atan(Math.atan(1) + Math.atan(3))), calc.getResult(" atan(atan(1)+atan(3))", 0));
    }

    @Test
    public void square() {
        Assertions.assertEquals(round(Math.sqrt(1)), calc.getResult("sqrt(1)", 0));
        Assertions.assertEquals(round(Math.sqrt(Math.sqrt(1))), calc.getResult("sqrt(sqrt(1))", 0));
        Assertions.assertEquals(round(Math.sqrt(1) + Math.sqrt(3)), calc.getResult("sqrt(1)+sqrt(3)", 0));
        Assertions.assertEquals(round(Math.sqrt(Math.sqrt(1) + Math.sqrt(3))), calc.getResult(" sqrt(sqrt(1)+sqrt(3))", 0));
    }

    @Test
    public void replaceX() {
        Assertions.assertEquals(round(13+3+26), calc.getResult("13+x+26", 3));
        Assertions.assertEquals(round(Math.pow(3,3) + Math.sqrt(3)), calc.getResult("x^x+sqrt(x)", 3));
        Assertions.assertEquals(round(Math.pow(3,3)), calc.getResult("x^x", 3));
    }

    @Test
    public void mod() {
    }

    @Test
    public void ln() {
        Assertions.assertEquals(round(Math.log(45)), calc.getResult("ln(45)", 3));
    }

    @Test
    public void log() {
        Assertions.assertEquals(round(Math.log10(45)), calc.getResult("log(45)", 3));
    }

    @Test
    public void megaTests() {
        Assertions.assertEquals(344378539330472184817647616.000000, calc.getResult("14^23/6*9+5-1+(56*2)", 0));
        Assertions.assertEquals(1.3762255134, calc.getResult("cos(45)+sin(45)", 3));
        Assertions.assertEquals(12.168078936, calc.getResult("sqrt(45)+ln(45)+log(45)", 10));
    }
}

