package ru.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


public class AlphaBankTestRegistration {

    public String date(int days, String format) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(format));
    }

    @BeforeEach
    void startTest() {
        open("http://localhost:9999");
    }

    @Test
    void shouldEnterAll() {
//      Test configuration
        String meetDate = date(3, "dd.MM.yyyy");
//      Test data
        $("[data-test-id='city'] .input__box .input__control[placeholder='Город']").setValue("Уфа");
        $("[data-test-id='date'] .input__box .input__control[placeholder='Дата встречи']")
                .doubleClick().sendKeys(Keys.chord(Keys.BACK_SPACE));
        $("[data-test-id='date'] .input__box .input__control[placeholder='Дата встречи']")
                .setValue(String.valueOf(meetDate));
        $("[data-test-id='name'] .input__box .input__control[name='name']").setValue("Семенов Андрей");
        $("[data-test-id='phone'] .input__box .input__control[name='phone']").setValue("+12345678901");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='notification']").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(byText("Успешно!")).shouldBe(Condition.visible);
        $("[data-test-id='notification'] .notification__content").shouldHave
                (Condition.text("Встреча успешно забронирована на " + meetDate));
    }


}
