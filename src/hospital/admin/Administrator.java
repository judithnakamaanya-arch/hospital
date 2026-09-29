package hospital.admin;

import hospital.staff.Staff;

public class Administrator extends Staff {

    private final FinanceLedger ledger = new FinanceLedger();

    public Administrator(String staffId, String name) {
        super(staffId, name);
    }

    @Override
    public String getRole() {
        return "Administrator";
    }

    public String billPatient(String patientId, String service, double amount, boolean paid) {
        FinancialRecord r = ledger.post(patientId, service, amount, paid);
        logAccess("posted " + r.getInvoiceNo() + " for " + patientId);
        return r.getInvoiceNo();
    }

    public void printLedger() {
        logAccess("opened the finance ledger");
        for (FinancialRecord r : ledger.all()) {
            System.out.println("    " + r);
        }
        System.out.printf("    Total billed: UGX %,.0f | Outstanding: UGX %,.0f%n",
                ledger.totalBilled(), ledger.totalOutstanding());
    }
}
