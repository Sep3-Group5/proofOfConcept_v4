package via.sdj3.proofofconcept_v3.service;

import org.springframework.stereotype.Service;
import via.sdj3.proofofconcept_v3.entity.Doctor;
import java.util.List;
import java.util.Optional;

@Service
public interface DoctorServiceInterface {

    Doctor addDoctor(Doctor doctor) throws Exception;

    String authenticateDoctor(String name, String password);
    Doctor registerDoctor(String name, String username, String specialization, String password);
    List<Doctor> getAllDoctors();
	Optional<Doctor> getDoctorById(int id);

}
