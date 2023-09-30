import edu.school21.model.BasicCalcModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class BasicCalcModelTest {
    BasicCalcModel calc = new BasicCalcModel();

    @Test
    public void addition() {
        Assertions.assertEquals( 100+200+300.0+123, calc.getResult("100+200+300.0+123", 0));
    }

    @Test
    public void degree() {
        Assertions.assertEquals( Math.pow(3, 3), calc.getResult("3^3", 0));
        Assertions.assertEquals( Math.pow(Math.pow(2, 3), 3), calc.getResult("2^3^3", 0));
    }

    @Test
    public void subtraction() {
        Assertions.assertEquals( 100-200-300.0-123, calc.getResult("100-200-300.0-123", 0));
    }

    @Test
    public void cos() {
        Assertions.assertEquals(Math.cos(1), calc.getResult("cos(1)", 0));
    }

    @Test
    public void sin() {
        Assertions.assertEquals( Math.sin(1), calc.getResult("sin(1)", 0));
    }

    @Test
    public void tan() {
        Assertions.assertEquals( Math.tan(13), calc.getResult("tan(13)", 0));
    }

    @Test
    public void atan() {
        Assertions.assertEquals( Math.atan(13), calc.getResult("atan(13)", 0));
    }
}

