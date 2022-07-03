package myjak.currencyexchangecalculator;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import myjak.currencyExchangeCalculator.Model.Currency;
import org.springframework.beans.factory.annotation.Autowired;


@Route("")
@StyleSheet("/public.css/style.css")
public class CurrencyExchangeGui extends VerticalLayout{
    Currency currency = new Currency();


    @Autowired
    public CurrencyExchangeGui(CurrencyService currencyService){

        Label labelEffectiveDate = new Label("kurs na dzień: " + currencyService.getEffectiveDate());

        ComboBox<String> comboBoxToBeConvertedCurrency = new ComboBox<String>("Chcę zamienić walutę:", currencyService.getCurrenciesNames());
        comboBoxToBeConvertedCurrency.setPlaceholder("wybierz walutę");

        NumberField numberFieldAmountToConvert = new NumberField();
        numberFieldAmountToConvert.setLabel("w ilości:");

        ComboBox<String> comboBoxConvertedCurrency = new ComboBox<>("na:", currencyService.getCurrenciesNames());
        comboBoxConvertedCurrency.setPlaceholder("wybierz walutę");

        Button buttonConvert = new Button("przelicz");

        Label labelFieldResult = new Label();

        buttonConvert.addClickListener(clickEvent -> labelFieldResult
                .setText(currencyService.convertCurrency(
                        comboBoxToBeConvertedCurrency.getValue(),
                        comboBoxConvertedCurrency.getValue(),
                        numberFieldAmountToConvert.getValue()) +
                        " " +
                        currencyService.getCurrencyCode(comboBoxConvertedCurrency.getValue())));

        add(labelEffectiveDate,comboBoxToBeConvertedCurrency, numberFieldAmountToConvert, comboBoxConvertedCurrency, buttonConvert, labelFieldResult);

    }
}
