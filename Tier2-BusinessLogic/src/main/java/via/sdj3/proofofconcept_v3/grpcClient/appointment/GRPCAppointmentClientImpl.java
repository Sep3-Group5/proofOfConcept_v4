package via.sdj3.proofofconcept_v3.grpcClient.appointment;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Service;
import via.sdj3.proofofconcept_v3.GrpcService.appointment.AppointmentGrpc;
import via.sdj3.proofofconcept_v3.GrpcService.appointment.AppointmentObj;
import via.sdj3.proofofconcept_v3.GrpcService.patient.PatientGrpc;
import via.sdj3.proofofconcept_v3.GrpcService.patient.PatientObj;
import via.sdj3.proofofconcept_v3.entity.Appointment;
import via.sdj3.proofofconcept_v3.entity.Patient;

@Service
public class GRPCAppointmentClientImpl implements AppointmentClient {

	private AppointmentGrpc.AppointmentBlockingStub appointmentBlockingStub;

	public AppointmentGrpc.AppointmentBlockingStub getAppointmentBlockingStub() {
		if (appointmentBlockingStub == null) {
			ManagedChannel managedChannel = ManagedChannelBuilder
				.forAddress("localhost", 5021)
				.usePlaintext()
				.build();
			appointmentBlockingStub = AppointmentGrpc.newBlockingStub(managedChannel);
		}
		return appointmentBlockingStub;
	}

	@Override
	public Appointment addAppointment(Appointment appointment) {
		AppointmentObj appointmentObj = AppointmentObj.newBuilder()
			.setId(appointment.getAppointmentId())
			.setDoctorId(appointment.getDoctorId())
			.setPatientId(appointment.getPatientId())
			.setDate(appointment.getDate())
			.setTime(appointment.getTime())
			.setDiagnosis(appointment.getDiagnosis())
			//.setStatus(appointment.getStatus)
			.build();

		AppointmentObj appointmentObjFromServer;
		try{
			appointmentObjFromServer = getAppointmentBlockingStub().createAsync(appointmentObj);
		}
		catch (StatusRuntimeException e) {
			System.out.println(e.getStatus().getDescription());
			throw new RuntimeException(e.getStatus().getDescription());
		}
		finally {
			patientBlockingStub = null;
		}

		System.out.println(patientObjFromServer.getName());

		Patient realObj = getPatient(patientObjFromServer);
		System.out.println(realObj.getFullName());
		return realObj;


	}

	private Appointment getAppointment(AppointmentObj appointmentObjFromServer) {
		Appointment returnedAppointment = new Appointment();
		returnedAppointment.setAppointmentId(appointmentObjFromServer.getId());
		returnedAppointment.set(appointmentObjFromServer.getName());
		return returnedPatient;
	}
}