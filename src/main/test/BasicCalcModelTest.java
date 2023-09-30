import edu.school21.model.BasicCalcModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class BasicCalcModelTest {
    BasicCalcModel calc = new BasicCalcModel();

    @Test
    public void addition() {
        Assertions.assertEquals( 100+200+300.0+123, calc.getResult("100+200+300.0+123", 0));
        Assertions.assertEquals( 100+9999.1+3213+123.12312, calc.getResult("100+9999.1+3213+123.12312", 0));
        Assertions.assertEquals( 100+(9999.1+(3213+123.12312)), calc.getResult("100+(9999.1+(3213+123.12312))", 0));
        Assertions.assertEquals( 100+(9999.1+(3213+(123.12312))), calc.getResult("100+(9999.1+(3213+(123.12312)))", 0));
    }

    @Test
    public void degree() {
        Assertions.assertEquals( Math.pow(3, 3), calc.getResult("3^3", 0));
        Assertions.assertEquals( Math.pow(Math.pow(2, 3), 3), calc.getResult("2^3^3", 0));
        Assertions.assertEquals( 3 * Math.pow(3, 3), calc.getResult("3*3^3", 0));
    }

    @Test
    public void subtraction() {
        Assertions.assertEquals( 100-200-300.0-123, calc.getResult("100-200-300.0-123", 0));
        Assertions.assertEquals( 100-9999.1-3213-123.12312, calc.getResult("100-9999.1-3213-123.12312", 0));
        Assertions.assertEquals( 100-(9999.1-(3213-123.12312)), calc.getResult("100-(9999.1-(3213-123.12312))", 0));
        Assertions.assertEquals( 100-(9999.1-(3213-(123.12312))), calc.getResult("100-(9999.1-(3213-(123.12312)))", 0));
    }

    @Test
    public void cos() {
        Assertions.assertEquals(Math.cos(1), calc.getResult("cos(1)", 0));
        Assertions.assertEquals(Math.cos(Math.cos(1)), calc.getResult("cos(cos(1))", 0));
        Assertions.assertEquals(Math.cos(1) + Math.cos(3), calc.getResult("cos(1)+cos(3)", 0));
        Assertions.assertEquals( Math.cos(Math.cos(1) + Math.cos(3)), calc.getResult(" cos(cos(1)+cos(3))", 0));


    }

    @Test
    public void sin() {
        Assertions.assertEquals(Math.sin(1), calc.getResult("sin(1)", 0));
        Assertions.assertEquals(Math.sin(Math.sin(1)), calc.getResult("sin(sin(1))", 0));
        Assertions.assertEquals(Math.sin(1) + Math.sin(3), calc.getResult("sin(1)+sin(3)", 0));
        Assertions.assertEquals( Math.sin(Math.sin(1) + Math.sin(3)), calc.getResult(" sin(sin(1)+sin(3))", 0));
    }

    @Test
    public void tan() {
        Assertions.assertEquals(Math.tan(1), calc.getResult("tan(1)", 0));
        Assertions.assertEquals(Math.tan(Math.tan(1)), calc.getResult("tan(tan(1))", 0));
        Assertions.assertEquals(Math.tan(1) + Math.tan(3), calc.getResult("tan(1)+tan(3)", 0));
        Assertions.assertEquals( Math.tan(Math.tan(1) + Math.tan(3)), calc.getResult(" tan(tan(1)+tan(3))", 0));    }

    @Test
    public void atan() {
        Assertions.assertEquals(Math.atan(1), calc.getResult("atan(1)", 0));
        Assertions.assertEquals(Math.atan(Math.atan(1)), calc.getResult("atan(atan(1))", 0));
        Assertions.assertEquals(Math.atan(1) + Math.atan(3), calc.getResult("atan(1)+atan(3)", 0));
        Assertions.assertEquals( Math.atan(Math.atan(1) + Math.atan(3)), calc.getResult(" atan(atan(1)+atan(3))", 0));    }
}

