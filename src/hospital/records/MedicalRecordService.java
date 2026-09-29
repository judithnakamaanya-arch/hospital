package hospital.records;

import hospital.staff.Doctor;
import hospital.staff.Staff;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class MedicalRecordService {
    String prescription;
    String dosage;


    private final Map<String, Patient> patients = new LinkedHashMap<>();

    public void registerPatient(Staff registeredBy, Patient patient) {
        patients.put(patient.getPatientId(), patient);
        System.out.println("  Registered: " + patient + " (by " + registeredBy.getName() + ")");
    }

    public String viewBasicDetails(Staff requester, String patientId) {
        Patient p = findPatient(patientId);
        return "Name: " + p.getFullName() + ", Appointment: " + p.getAppointmentDate();
    }

    public void rescheduleAppointment(Staff requester, String patientId, LocalDate newDate) {
        Patient p = findPatient(patientId);
        p.setAppointmentDate(newDate); // allowed: same package
        System.out.println("  " + p.getFullName() + " rescheduled to " + newDate
                + " by " + requester.getName());
    }

    public String viewMedicalRecord(Doctor doctor, String patientId) {
        Patient p = findPatient(patientId);
        return "Patient: " + p.getFullName()
                + "\n    Diagnosis:      " + p.getDiagnosis()
                + "\n    Prescriptions:  " + p.getPrescriptions()
                + "\n    Clinical notes: " + p.getClinicalNotes();
    }

    public void updateDiagnosis(Doctor doctor, String patientId, String diagnosis) {
        Patient p = findPatient(patientId);
        p.setDiagnosis(diagnosis);
        p.addClinicalNote("Diagnosis updated by Dr. " + doctor.getName());
    }

    public void prescribe(Doctor doctor, String patientId, String prescription) {
        Patient p = findPatient(patientId);
        p.addPrescription(prescription + " (Dr. " + doctor.getName() + ")");
    }

    private Patient findPatient(String patientId) {
        Patient p = patients.get(patientId);
        if (p == null) {
            throw new IllegalArgumentException("No patient with ID " + patientId);
        }
        return p;
    }
}
