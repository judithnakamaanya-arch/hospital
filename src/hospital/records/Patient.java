package hospital.records;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Patient {

    private final String patientId;
    private final String fullName;
    private LocalDate appointmentDate;

    private String diagnosis;
    private final List<String> prescriptions = new ArrayList<>();
    private final List<String> clinicalNotes = new ArrayList<>();

    public Patient(String patientId, String fullName, LocalDate appointmentDate) {
        this.patientId = patientId;
        this.fullName = fullName;
        this.appointmentDate = appointmentDate;
        this.diagnosis = "Not yet assessed";
    }


    public String getPatientId() {
        return patientId;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }


    void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    String getDiagnosis() {
        return diagnosis;
    }

    void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    List<String> getPrescriptions() {
        return Collections.unmodifiableList(prescriptions); // callers cannot alter the list
    }

    void addPrescription(String prescription) {
        prescriptions.add(prescription);
    }

    List<String> getClinicalNotes() {
        return Collections.unmodifiableList(clinicalNotes);
    }

    void addClinicalNote(String note) {
        clinicalNotes.add(note);
    }

    @Override
    public String toString() {
        return patientId + " | " + fullName + " | Appointment: " + appointmentDate;
    }
}
