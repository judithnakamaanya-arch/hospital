package hospital.admin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class FinanceLedger {
    String name="joan";

    private final List<FinancialRecord> records = new ArrayList<>();
    private int invoiceCounter = 1000;

    FinancialRecord post(String patientId, String description, double amount, boolean paid) {
        FinancialRecord r = new FinancialRecord(nextInvoiceNo(), patientId, description,
                amount, paid, LocalDate.now());
        records.add(r);
        return r;
    }

    List<FinancialRecord> all() {
        return Collections.unmodifiableList(records);
    }

    double totalBilled() {
        return records.stream().mapToDouble(FinancialRecord::getAmount).sum();
    }

    double totalOutstanding() {
        return records.stream().filter(r -> !r.isPaid()).mapToDouble(FinancialRecord::getAmount).sum();
    }

    private String nextInvoiceNo() {
        return "INV-" + (++invoiceCounter);
    }
}
