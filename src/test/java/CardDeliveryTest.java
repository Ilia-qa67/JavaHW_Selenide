import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }
    int addDays = 7;

    public String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }
    String planningDate = generateDate(addDays, "dd.MM.yyyy");

    @Test
    public void shouldTest() {

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Василий Рука-Нога");
        $("[data-test-id=phone] input").setValue("+79779801234");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + planningDate));

    }

    @Test
    public void shouldTestCalendar() {
        String day = generateDate(7, "d");

        $("[data-test-id='city'] input").setValue("Мо");
        $$("span.menu-item__control").find(exactText("Москва")).click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("span.icon").click();
        if (LocalDate.now().getDayOfMonth() > LocalDate.now().plusDays(addDays).getDayOfMonth()) {
            $("[data-step='1']").click();
            $$("tr.calendar__row td.calendar__day").find(exactText(day)).click();
        } else {
            $$("tr.calendar__row td.calendar__day").find(exactText(day)).click();
        }

        $("[data-test-id=name] input").setValue("Василий Рука-Нога");
        $("[data-test-id=phone] input").setValue("+79779801234");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + planningDate));

    }
}
