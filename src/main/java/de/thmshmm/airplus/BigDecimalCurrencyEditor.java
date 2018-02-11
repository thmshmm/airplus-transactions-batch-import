package de.thmshmm.airplus;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;


public class BigDecimalCurrencyEditor extends PropertyEditorSupport {

    private Character thousandsSeparator = null;
    private Character decimalSeparator = null;

    public BigDecimalCurrencyEditor() {
    }

    public BigDecimalCurrencyEditor setThousandsSeparator(Character thousandsSeparator) {
        this.thousandsSeparator = thousandsSeparator;
        return this;
    }

    public BigDecimalCurrencyEditor setDecimalSeparator(Character decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
        return this;
    }

    public void setAsText(String text) throws IllegalArgumentException {
        String formattedText = text;

        if (thousandsSeparator != null) {
            formattedText = formattedText.replaceAll("\\" + thousandsSeparator, "");
        }

        if (decimalSeparator != null && decimalSeparator != '.') {
            formattedText = formattedText.replace(decimalSeparator, '.');
        }

        this.setValue(formattedText == null ? null : new BigDecimal(formattedText));
    }
}
