package ru.netology.service;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.io.StringReader;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;
import static org.openqa.selenium.Keys.ESCAPE;


public class shouldTestV1 {

    public String generateDate (int days){
        String Date;
        LocalDate localDate = LocalDate.now().plusDays(days);
        Date = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(localDate);
        return Date;
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
        String Date = generateDate(5);
        $("[data-test-id= 'city'] .input__control").setValue("Казань");
        $("[data-test-id = 'date'] .input__control").click();
        $("[data-test-id = 'date'] .input__control").sendKeys(Keys.chord(BACK_SPACE),Keys.chord(BACK_SPACE),Keys.chord(BACK_SPACE),Keys.chord(BACK_SPACE), Keys.chord(BACK_SPACE), Keys.chord(BACK_SPACE), Keys.chord(BACK_SPACE), Keys.chord(BACK_SPACE), Keys.chord(BACK_SPACE), Keys.chord(BACK_SPACE), Date);
        $("[data-test-id = 'name'] .input__control").setValue("Иванов Иван");
        $("[data-test-id = 'phone'] .input__control").setValue("+79999999999");
        $$(".checkbox__box").find(Condition.visible).click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(withText("Встреча успешно забронирована")).shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(withText("Встреча успешно забронирована")).shouldBe(Condition.text(Date));

    }
}
