package ru.netology.service;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;


public class ShouldTestV1 {

    public String generateDate (int days){
        String planningDate;
        LocalDate localDate = LocalDate.now().plusDays(days);
        planningDate = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(localDate);
        return planningDate;
    }
    @BeforeEach
    void openUrl(){
        open("http://localhost:9999/");
    }
    @AfterEach
    void tearDown(){
        closeWindow();
    }
    @Test
    void shouldTest(){
        String planningDate = generateDate(5);
        $("[data-test-id= 'city'] .input__control").setValue("Казань");
        $("[data-test-id = 'date'] .input__control").click();
        $("[data-test-id = 'date'] .input__control").sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME), Keys.BACK_SPACE, planningDate);
        $("[data-test-id = 'name'] .input__control").setValue("Иванов Иван");
        $("[data-test-id = 'phone'] .input__control").setValue("+79999999999");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}
