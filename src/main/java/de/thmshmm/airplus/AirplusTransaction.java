package de.thmshmm.airplus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public class AirplusTransaction {

    private String cardNo;

    private String invoiceNo;

    private LocalDate invoiceDate;

    private int invoiceItemNo;

    private LocalDate purchaseDate;

    private LocalDate entryDate;

    private String serviceProvider;

    private String serviceDescription;

    private Currency currency;

    private BigDecimal amount;

    private Character debitCredit;

    public AirplusTransaction() {
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public int getInvoiceItemNo() {
        return invoiceItemNo;
    }

    public void setInvoiceItemNo(int invoiceItemNo) {
        this.invoiceItemNo = invoiceItemNo;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Character getDebitCredit() {
        return debitCredit;
    }

    public void setDebitCredit(Character debitCredit) {
        this.debitCredit = debitCredit;
    }

    @Override
    public String toString() {
        return "AirplusTransaction{" +
                "cardNo='" + cardNo + '\'' +
                ", invoiceNo='" + invoiceNo + '\'' +
                ", invoiceDate=" + invoiceDate +
                ", invoiceItemNo=" + invoiceItemNo +
                ", purchaseDate='" + purchaseDate + '\'' +
                ", entryDate='" + entryDate + '\'' +
                ", serviceProvider='" + serviceProvider + '\'' +
                ", serviceDescription='" + serviceDescription + '\'' +
                ", currency='" + currency + '\'' +
                ", amount='" + amount + '\'' +
                ", debitCredit='" + debitCredit + '\'' +
                '}';
    }
}
