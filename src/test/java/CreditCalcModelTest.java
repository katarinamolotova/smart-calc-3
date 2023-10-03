import edu.school21.enums.CreditType;
import edu.school21.enums.TermType;
import edu.school21.model.CreditCalcModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


public class CreditCalcModelTest {

  private static final Integer AMOUNT_OF_MONTH = 12;
  private final CreditCalcModel calc = new CreditCalcModel();

  @BeforeEach
  public void calculate() {
    calc.calculate(
            CreditType.ANNUITY,
            1000000d,
            AMOUNT_OF_MONTH,
            TermType.MONTH,
            5d
    );
  }

  @Test
  public void overpay() {
    Assertions.assertEquals(27289.89, calc.getOverpay());
  }

  @Test
  public void totalPayment() {
    Assertions.assertEquals(1027289.88, calc.getTotalPayment());
  }

  @Test
  public void everyMonthPay() {
    List<Double> payment = calc.getEveryMothPay();
    Assertions.assertEquals(AMOUNT_OF_MONTH, payment.size());
    Assertions.assertEquals(85607.49, payment.get(0));
  }
}

