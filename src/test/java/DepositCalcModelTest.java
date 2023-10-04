
import edu.school21.enums.PeriodType;
import edu.school21.enums.TermType;
import edu.school21.model.DepositCalcModel;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class DepositCalcModelTest {

  private static final Double DEPOSIT_PERCENT = 5d;
  private static final Double SUMMA = 1000000d;
  private static final Integer AMOUNT_OF_MONTH = 12;
  private static final Integer START_MONTH = 10;

  private static final Double RESULT = 50000d;

  private final DepositCalcModel calc = new DepositCalcModel();

  @Test
  public void resultPercent() {
    final double result = calc.resultPercent(
            SUMMA,
            AMOUNT_OF_MONTH,
            TermType.MONTH,
            DEPOSIT_PERCENT,
            PeriodType.NONE,
            PeriodType.ONCE,
            START_MONTH,
            Collections.emptyMap(),
            Collections.emptyMap()
    );
    Assertions.assertEquals(RESULT, result);
  }

  @Test
  public void resultPercentWithCapitalizationAndPay() {
    final double result = calc.resultPercent(
            SUMMA,
            AMOUNT_OF_MONTH,
            TermType.MONTH,
            DEPOSIT_PERCENT,
            PeriodType.MONTHLY,
            PeriodType.MONTHLY,
            START_MONTH,
            Collections.emptyMap(),
            Collections.emptyMap()
    );
    Assertions.assertEquals(RESULT, result);
  }

  @Test
  public void resultPercentWithCapitalization() {
    final double result = calc.resultPercent(
            SUMMA,
            AMOUNT_OF_MONTH,
            TermType.MONTH,
            DEPOSIT_PERCENT,
            PeriodType.MONTHLY,
            PeriodType.ONCE,
            START_MONTH,
            Collections.emptyMap(),
            Collections.emptyMap()
    );
    Assertions.assertTrue(RESULT < result);
  }

  @Test
  public void resultPercentWithAdditions() {
    final double result = calc.resultPercent(
            SUMMA,
            AMOUNT_OF_MONTH,
            TermType.MONTH,
            DEPOSIT_PERCENT,
            PeriodType.NONE,
            PeriodType.ONCE,
            START_MONTH,
            new TreeMap<>(Map.of(LocalDate.now(), 10000d)),
            Collections.emptyMap()
    );
    Assertions.assertTrue(RESULT < result);
  }

  @Test
  public void zeroTax() {
    final double result = calc.sumTax(
            0,
            RESULT
    );
    Assertions.assertEquals(0, result);
  }


  @Test
  public void nonZeroTax() {
    final double result = calc.sumTax(
            5,
            RESULT
    );
    Assertions.assertEquals(RESULT / 100 * 5, result);
  }

  @Test
  public void finalSumWithoutAdditionsAndWithdrawals() {
    final double result = calc.sumAtTheEnd(
            SUMMA,
            RESULT,
            Collections.emptyMap(),
            Collections.emptyMap()
    );
    Assertions.assertEquals(RESULT + SUMMA, result);
  }

  @Test
  public void finalSumWithEqualsAdditionsAndWithdrawals() {
    final double result = calc.sumAtTheEnd(
            SUMMA,
            RESULT,
            Collections.singletonMap(LocalDate.now(), 10000d),
            Collections.singletonMap(LocalDate.now(), 10000d)
    );
    Assertions.assertEquals(RESULT + SUMMA, result);
  }

  @Test
  public void finalSumWithAdditionsAndWithdrawals() {
    final double addition = 1000d;
    final double withdrawal = 5000d;
    final double result = calc.sumAtTheEnd(
            SUMMA,
            RESULT,
            Collections.singletonMap(LocalDate.now(), addition),
            Collections.singletonMap(LocalDate.now(), withdrawal)
    );
    Assertions.assertEquals(RESULT + SUMMA + addition - withdrawal, result);
  }


}

