类库设计者将保存时间与给时间点命名分开。所以标准Java类库分别包含两个类：

一个用来表示时间点的`Date`类；另一个是日历表示法表示日期的`LocalDate`类。

将时间度量与日历分开是一种很好的面向对象设计。

### LocalDate

```java
java.time.LocalDate

static LocalDate now()
构造一个表示当前日期的对象

static LocalDate of(int year, int mongth, int day)
构造一个指定日期的对象

int getYear()
int getMonthValue()
int getDayOfMonth()
得到当前日期的年，月，日

DayOfWeek getDayOfWeek
得到当前日期是星期几，作为DayOfWeek类的一个实例返回。调用getValue（）来得到一个数字，表示这是星期几，1表示星期一，7表示星期七

LocalDate pulsDays(int n)
LocalDate minusDays(int n)
生成当前日期之后或之前n天的日期
```



```java
package CalendarTest;

import java.time.*;

/**
 * @author lyf
 */
public class CalendarTest {
    public static void main(String[] args) {
        // get month and day from LocalDate
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        int today = date.getDayOfMonth();

        // set to start of month
        date = date.minusDays(today - 1);
        DayOfWeek weekday = date.getDayOfWeek();
        int value = weekday.getValue();

        System.out.println("Mon Tue Wed Thu Fri Sat Sun");
        for (int i = 0; i < value; i++) {
            System.out.print("  ");
        }
        while (date.getMonthValue() == month) {
            System.out.printf("%3d", date.getDayOfMonth());
            if (date.getDayOfMonth() == today) {
                System.out.print("*");
            } else {
                System.out.print(" ");
            }
            date = date.plusDays(1);
            if (date.getDayOfWeek().getValue() == 1) {
                System.out.println();
            }
        }
    }
}
```