package ru.raiffeisen.tests.parametrized;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.raiffeisen.tests.base.BaseTests;

public class ParametrizedMortgageAppTest extends BaseTests {

    @ParameterizedTest
    @ValueSource(strings = {"Иванов Иван Иванович", "Петров Петр Петрович", "Александров Александр Александрович"})
    public void parametrizedMortgageAppTest (String arg) {

        // Кликаем на меню и проверяем появилось ли рефинансирование
        getWait().until(ExpectedConditions.elementToBeClickable(clickElement(By.xpath("//ul[@class='main-menu']//a[contains(text(),'Ипотека')]"))));
        checkExistance(By.xpath("//div[@id='menu2']//a[contains(.,'Рефинансирование')]"), "Рефинансирование");

        // Кликаем на рефинансирование и проверяем новую страницу по заголовку h1
        clickElement(By.xpath("//div[@id='menu2']//a[contains(.,'Рефинансирование')]"));
        checkExistance(By.xpath("//div[@class='b-intro']//h1[contains(.,'Рефинансирование ипотеки')]"), "ефинансирование ипотеки");

        // Оставляем заявку и проверяем по заголовку переход на страницу с формой заявки
        clickElement(By.xpath("//div[@class='b-intro']//a[contains(.,'Оставить заявку')]"));
        checkExistance(By.xpath("//*[@id='form']//div[@data-marker='MainForm.constructor.Title']//div[contains(.,'Оставьте заявку на ')]"), "ефинансирование ипотеки");

        // Заполняем с проверкой поля ФИО, дату рождения и место рождения
        fillField(By.xpath("//*[@id='form']//input[@data-marker-field='fullName']"), arg);
        clickAndFillField(By.xpath("//*[@id='form']//input[@data-marker-field='birthDate']"), "01.01.1990");
        clickAndFillField(By.xpath("//*[@id='form']//input[@data-marker-field='birthPlace']"), "Москва");

        // Выбираем пол
        clickElement(By.xpath("//*[@id='form']//span[contains(.,'Мужской')]"));

        // Переключаем свитчер "Являюсь гражданином РФ" на нет с проверкой по названию паспорта
        clickElement(By.xpath("//*[@id='form']//span[@data-marker='Switcher.Caption']"));
        checkExistance(By.xpath("//*[@id='form']//h3[contains(.,'иностранного')]"), "иностранного");

        // Заполняем страну гражданства "Германия"
        clickElement(By.xpath("//*[@id='form']//div[@data-marker-field='citizenship']"));
        clickElement(By.xpath("//*[@id='form']//div[@data-marker='Dropdown.Box']//div[@data-marker='Item.Value']//div[contains(.,'Германия')]"));

        // Вводим данные иностранного паспорта с проверками
        fillField(By.xpath("//*[@id='form']//input[@data-marker-field='foreignSeries']"), "1111");
        fillField(By.xpath("//*[@id='form']//input[@data-marker-field='foreignNumber']"), "1111111111");
        clickAndFillField(By.xpath("//*[@id='form']//input[@data-marker-field='foreignIssuedDate']"), "01.01.2020");
        fillField(By.xpath("//*[@id='form']//input[@data-marker-field='foreignIssuedBy']"), "111");
        fillField(By.xpath("//*[@id='form']//input[@data-marker-field='registrationAddress']"), "г Москва, Ломоносовский пр-кт, д 27Д");
        fillPhoneNumber(By.xpath("//*[@id='form']//input[@data-marker-field='phone']"), "(909) 999-99-99");

        // Жмем кнопку "Продолжить" и проверяем остались ли на той же странице
        clickElement(By.xpath("//*[@id='form']//button[@data-marker='MainForm.constructor.Button']"));
        checkExistance(By.xpath("//*[@id='form']//div[@data-marker='MainForm.constructor.Title']//div[contains(.,'Оставьте заявку на ')]"), "ефинансирование ипотеки");

    }
}
