package hospital.staff;

import hospital.records.MedicalRecordService;

public final class Doctor extends Staff {
    String vaccine;
    String tablets;

    private final String licenceNumber;
    private final String specialty;

    public Doctor(String staffId, String name, String licenceNumber, String specialty) {
        super(staffId, name);
        this.licenceNumber = licenceNumber;
        this.specialty = specialty;
    }

    @Override
    public String getRole() {
        return "Doctor";
    }

    public String getSpecialty() {
        return specialty;
    }

    public String readMedicalRecord(MedicalRecordService records, String patientId) {
        verifyLicence();
        logAccess("viewed medical record of " + patientId);
        return records.viewMedicalRecord(this, patientId);
    }

    public void diagnose(MedicalRecordService records, String patientId, String diagnosis) {
        verifyLicence();
        records.updateDiagnosis(this, patientId, diagnosis);
        logAccess("updated diagnosis of " + patientId);
    }

    public void prescribe(MedicalRecordService records, String patientId, String drug) {
        verifyLicence();
        records.prescribe(this, patientId, drug);
        logAccess("prescribed '" + drug + "' to " + patientId);
    }

    private void verifyLicence() {
        if (licenceNumber == null || licenceNumber.isBlank()) {
            throw new IllegalStateException("Doctor " + getName() + " has no valid licence");
        }
    }
}
