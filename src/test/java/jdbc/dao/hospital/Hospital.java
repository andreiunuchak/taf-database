package jdbc.dao.hospital;

public class Hospital {
    public static final String DB_SCHEME = "hospital";
    public final AdmissionDAO admission;
    public final DoctorDAO doctor;
    public final PatientDAO patient;
    public final ProvinceNameDAO provinceName;

    public Hospital() {
        this.admission = new AdmissionDAO(DB_SCHEME);
        this.doctor = new DoctorDAO(DB_SCHEME);
        this.patient = new PatientDAO(DB_SCHEME);
        this.provinceName = new ProvinceNameDAO(DB_SCHEME);
    }
}
