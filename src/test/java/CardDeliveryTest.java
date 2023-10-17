import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    public String date() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.plusDays(5).toString();
    }

    public int day() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.plusDays(7).getDayOfMonth();
    }

    @Test
    public void shouldTest() {

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").setValue(date());
        $("[data-test-id=name] input").setValue("Василий Рука-Нога");
        $("[data-test-id=phone] input").setValue("+79779801234");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Встреча успешно забронирована"));

    }

    @Test
    public void shouldTestCalendar() {
        String day = Integer.toString(day());

        $("[data-test-id='city'] input").setValue("Мо");
        $$("span.menu-item__control").find(exactText("Москва")).click();

        $("[data-test-id=date]").click();
        if (LocalDate.now().getDayOfMonth() > LocalDate.now().plusDays(7).getDayOfMonth()) {
            $$("tr.calendar__row td.calendar__day").find(exactText(day)).click();
        } else {
            $("[data-step='1']").click();
            $$("tr.calendar__row td.calendar__day").find(exactText(day)).click();
        }

        $("[data-test-id=name] input").setValue("Василий Рука-Нога");
        $("[data-test-id=phone] input").setValue("+79779801234");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Встреча успешно забронирована"));

    }
}
