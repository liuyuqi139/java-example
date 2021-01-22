package calculate;

import java.math.BigDecimal;

/**
 *
 */
public class BigDecimalTest {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal(1.11);
        BigDecimal b = new BigDecimal(2.11);

        BigDecimal c = new BigDecimal("1.11");
        BigDecimal d = new BigDecimal("2.11");

        System.out.println(a.multiply(b));
        System.out.println(a.multiply(b.setScale(2, 4)));
        System.out.println(a.multiply(b).setScale(2, 4));

        System.out.println(c.multiply(d));

        System.out.println(BigDecimal.ZERO); //0
        System.out.println(BigDecimal.ZERO.compareTo(new BigDecimal("0.00")));  //0
        System.out.println(BigDecimal.ZERO.compareTo(new BigDecimal("0")) == 0);  //true
        System.out.println(new BigDecimal("0.00").compareTo(BigDecimal.ZERO) > 0);  //false
        System.out.println(new BigDecimal("0.1").compareTo(BigDecimal.ZERO) > 0);  //true

        System.out.println(new BigDecimal("2.1156").divide(d, 2, 4));
    }
}
