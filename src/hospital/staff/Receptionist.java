package hospital.staff;

import hospital.records.MedicalRecordService;
import hospital.records.Patient;

import java.time.LocalDate;

public final class Receptionist extends Staff {

    public Receptionist(String staffId, String name) {
        super(staffId, name);
    }

    @Override
    public String getRole() {
        return "Receptionist";
    }

    public void registerPatient(MedicalRecordService records, String id, String name, LocalDate appointment) {
        records.registerPatient(this, new Patient(id, name, appointment));
        logAccess("registered patient " + id);
    }

    public String viewPatient(MedicalRecordService records, String patientId) {
        logAccess("viewed basic details of " + patientId);
        return records.viewBasicDetails(this, patientId);
    }

    public void reschedule(MedicalRecordService records, String patientId, LocalDate newDate) {
        records.rescheduleAppointment(this, patientId, newDate);
        logAccess("rescheduled " + patientId);
    }
}
