package hospital.app;

import hospital.admin.Administrator;
import hospital.records.MedicalRecordService;
import hospital.staff.Doctor;
import hospital.staff.Receptionist;

import java.time.LocalDate;

public class HospitalApp {

    public static void main(String[] args) {
        MedicalRecordService records = new MedicalRecordService();

        Doctor doctor = new Doctor("D001", "Sarah Namubiru", "UMDPC-45821", "Internal Medicine");
        Receptionist receptionist = new Receptionist("R001", "Brian Okello");
        Administrator admin = new Administrator("A001", "Grace Atim");

        heading("1. Receptionist registers patients (basic details only)");
        receptionist.registerPatient(records, "P100", "John Mukasa", LocalDate.of(2026, 10, 2));
        receptionist.registerPatient(records, "P101", "Amina Nakato", LocalDate.of(2026, 10, 3));

        heading("2. Receptionist views and reschedules (allowed)");
        System.out.println("  " + receptionist.viewPatient(records, "P100"));
        receptionist.reschedule(records, "P101", LocalDate.of(2026, 10, 5));

        heading("3. Doctor reads and updates medical information (allowed)");
        doctor.diagnose(records, "P100", "Malaria (P. falciparum)");
        doctor.prescribe(records, "P100", "Artemether-Lumefantrine 80/480mg, 3 days");
        System.out.println("  " + doctor.readMedicalRecord(records, "P100"));

        heading("4. Administrator manages financial records (allowed, admin package only)");
        admin.billPatient("P100", "Consultation", 50_000, true);
        admin.billPatient("P100", "Laboratory - malaria test", 25_000, false);
        admin.billPatient("P101", "Consultation", 50_000, false);
        admin.printLedger();

        heading("5. What is BLOCKED (each line below fails to COMPILE if uncommented)");
       
        System.out.println("  See the commented lines in HospitalApp.java and AccessViolations.txt");
        System.out.println("  for the exact compiler errors each attempt produces.");
    }

    private static void heading(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
    }
}
