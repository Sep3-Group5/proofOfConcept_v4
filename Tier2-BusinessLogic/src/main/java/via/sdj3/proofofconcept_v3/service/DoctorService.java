package via.sdj3.proofofconcept_v3.service;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import via.sdj3.proofofconcept_v3.entity.Doctor;
import via.sdj3.proofofconcept_v3.grpcClient.doctor.DoctorClient;
import via.sdj3.proofofconcept_v3.jwtUtil.JwtUtil;
import via.sdj3.proofofconcept_v3.repository.DoctorRepository;

import javax.annotation.Nullable;
import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService implements DoctorServiceInterface{
    private DoctorRepository doctorRepository;

    private JwtUtil jwtUtil;

    private final DoctorClient client;

    public DoctorService(DoctorClient doctorClient,JwtUtil jwtUtil) {
        this.client = doctorClient;
        this.jwtUtil = jwtUtil;
    }

    // METHODS //

    public Doctor addDoctor(Doctor doctor){
        //other checks and validation can be done here
        return client.addDoctor(doctor);
    }

    public List<Doctor> getAllDoctors(){
        List<Doctor> doctors = new ArrayList<>();
        doctorRepository.findAll().forEach(doctor -> doctors.add(doctor));
        return doctors;
    }

    public Optional<Doctor> getDoctorById(int id){
        return doctorRepository.findById(id);
    }

    public String authenticateDoctor(String username, String password) {
        // Implement your logic to authenticate the user
        try
        {
            Doctor doctor = client.authenticateDoctor(username, password);

            if (doctor != null) {
                String token = jwtUtil.generateDoctorToken(doctor);
                return token;
            } else {
                return null;
            }

        }
        catch (Exception e)
        {
            throw e;
        }


    }

    @Override
    public Doctor registerDoctor(String name, String username, String specialization, String password) {
        // check username/password constraints

        // check if already exists


        Doctor doctor = new Doctor(name,username,specialization,password,false);


        try
        {
            // Send doctor to database
            client.addDoctor(doctor);
            return doctor;
        }
        catch (Exception e)
        {
          // return exception
        }
        return null;
    }
}


