package hospital.admin;

import java.time.LocalDate;

class FinancialRecord {

    private final String invoiceNo;
    private final String patientId;
    private final String description;
    private final double amount;      // Uganda shillings
    private final boolean paid;
    private final LocalDate date;

    FinancialRecord(String invoiceNo, String patientId, String description,
                    double amount, boolean paid, LocalDate date) {
        this.invoiceNo = invoiceNo;
        this.patientId = patientId;
        this.description = description;
        this.amount = amount;
        this.paid = paid;
        this.date = date;
    }

    String getInvoiceNo()   { return invoiceNo; }
    String getPatientId()   { return patientId; }
    String getDescription() { return description; }
    double getAmount()      { return amount; }
    boolean isPaid()        { return paid; }
    LocalDate getDate()     { return date; }

    @Override
    public String toString() {
        return String.format("%s | %s | %-26s | UGX %,12.0f | %s",
                invoiceNo, patientId, description, amount, paid ? "PAID" : "OUTSTANDING");
    }
}
