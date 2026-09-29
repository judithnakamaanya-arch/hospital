# Hospital Patient Records System: Java Access Modifiers

**Course:** BIT2115 Applied Object-Oriented Programming (Year 2, Sem 1)
**Problem:** Java Access Modifiers

## 1. The problem

A private hospital needs a Java application to manage patient records, with three access levels:

| Role | Allowed |
|---|---|
| Doctor | View **and update** medical information |
| Receptionist | View **basic details only** (names, appointment dates) |
| Administration department | **Only** they may access financial records |

## 2. Package structure (requirement 1)

```
src/hospital/
├── staff/      Staff (abstract), Doctor, Receptionist   → who the users are
├── records/    Patient, MedicalRecordService            → clinical data + its gateway
├── admin/      Administrator, FinancialRecord*, FinanceLedger*   → finance (*package-private)
└── app/        HospitalApp                              → entry point (main)
```

Each package is one department or responsibility. Because Java access control works at **package boundaries**, putting sensitive data in its own package lets the compiler enforce who can reach it.

## 3. The four access levels used

| Modifier | Visible to | Where it is used here |
|---|---|---|
| `private` | The same class only | Every field (patient data, licence number, ledger); internal helpers (`findPatient`, `verifyLicence`, `nextInvoiceNo`) |
| *(default)* package-private | The same package only | `Patient.getDiagnosis()` and other medical accessors; the **whole** `FinancialRecord` and `FinanceLedger` classes |
| `protected` | The same package + subclasses in any package | `Staff` constructor and `Staff.logAccess()` |
| `public` | Everyone | Basic patient getters; the service's gateway methods; staff actions |

## 4. How each requirement is enforced (requirement 2)

**Doctors can view and update medical data.**
`MedicalRecordService.viewMedicalRecord(Doctor, …)`, `updateDiagnosis(Doctor, …)` and `prescribe(Doctor, …)` are public, but the parameter type is `Doctor`. Passing a Receptionist or an Administrator is a **compile-time error**. `Doctor` is `final`, so nobody can create a subclass that inherits doctor privileges.

**Receptionists see only basic details.**
`Patient.getFullName()` and `getAppointmentDate()` are `public`. `getDiagnosis()`, `getPrescriptions()` and `getClinicalNotes()` are **package-private**, so only classes inside `hospital.records` can call them. `Receptionist` is in `hospital.staff`, so it cannot call them. `Patient.toString()` deliberately excludes medical data so that printing a patient does not leak it.

**Financial records are accessible only within administration.**
`FinancialRecord` and `FinanceLedger` are declared with **no modifier**, which makes the classes themselves invisible outside `hospital.admin`. Other packages cannot even declare a variable of that type. `Administrator`'s public methods return only `String`/primitive values, so no financial object ever leaves the package.

## 5. Justification of every access choice (requirement 3)

| Member | Modifier | Why |
|---|---|---|
| `Staff.staffId`, `name` | `private final` | Identity must not change after hiring; read-only through getters |
| `Staff(…)` constructor | `protected` | Only subclasses may create staff; `Administrator` (a different package) still reaches it through inheritance |
| `Staff.logAccess()` | `protected` | Every role must write audit entries, but outside code must not forge entries in someone's name |
| `Staff.getName()`, `getStaffId()` | `public` | Not sensitive; needed for display and audit |
| `Doctor.licenceNumber` | `private` | Personal professional data |
| `Doctor.verifyLicence()` | `private` | Internal check that runs before every clinical action |
| `Doctor` / `Receptionist` classes | `public final` | Usable everywhere but cannot be subclassed to impersonate a role |
| `Patient` fields (all) | `private` | No direct reading or writing of patient data from anywhere |
| `Patient.getFullName()`, `getAppointmentDate()`, `getPatientId()` | `public` | Basic details that receptionists need |
| `Patient.getDiagnosis()`, `setDiagnosis()`, prescriptions, notes | package-private | Reachable only through `MedicalRecordService`, which demands a `Doctor` |
| `Patient.setAppointmentDate()` | package-private | Changes must go through the service so they are logged |
| `Patient.getPrescriptions()` return value | unmodifiable list | Even a legitimate caller cannot alter the list it receives |
| `MedicalRecordService.patients` | `private` | No way around the gateway |
| `MedicalRecordService.findPatient()` | `private` | Would hand out whole `Patient` objects if exposed |
| `FinancialRecord` (class) | package-private | Finance is invisible outside `hospital.admin` |
| `FinancialRecord` fields | `private final` | A posted invoice cannot be edited, only offset by a new entry |
| `FinanceLedger` (class) | package-private | Internal to administration |
| `Administrator.ledger` | `private` | Cannot be reached through an Administrator reference |

## 6. Proof that the restrictions work

`AccessViolations.txt` holds a test class placed in `hospital.app` that attempts six forbidden operations. `javac` rejects all six:

```
error: incompatible types: Receptionist cannot be converted to Doctor
error: incompatible types: Administrator cannot be converted to Doctor
error: getDiagnosis() is not public in Patient; cannot be accessed from outside package
error: FinancialRecord is not public in hospital.admin; cannot be accessed from outside package
error: logAccess(String) has protected access in Staff
error: licenceNumber has private access in Doctor
```

Because these are compiler errors rather than run-time checks, insecure code cannot be built at all.

## 7. How to compile and run

From the project folder:

```bash
javac -d out $(find src -name "*.java")      # Windows: see below
java -cp out hospital.app.HospitalApp
```

Windows (Command Prompt):

```
dir /s /b src\*.java > sources.txt
javac -d out @sources.txt
java -cp out hospital.app.HospitalApp
```

In IntelliJ/Eclipse/NetBeans: mark `src` as the source root and run `hospital.app.HospitalApp`.
Expected output is in `sample_output.txt`.
