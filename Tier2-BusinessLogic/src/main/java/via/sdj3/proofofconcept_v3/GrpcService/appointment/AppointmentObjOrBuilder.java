// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: appointment.proto

package via.sdj3.proofofconcept_v3.GrpcService.appointment;

public interface AppointmentObjOrBuilder extends
    // @@protoc_insertion_point(interface_extends:appointment.AppointmentObj)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>int32 id = 1;</code>
   * @return The id.
   */
  int getId();

  /**
   * <code>int32 patient_id = 2;</code>
   * @return The patientId.
   */
  int getPatientId();

  /**
   * <code>int32 doctor_id = 3;</code>
   * @return The doctorId.
   */
  int getDoctorId();

  /**
   * <code>string date = 4;</code>
   * @return The date.
   */
  java.lang.String getDate();
  /**
   * <code>string date = 4;</code>
   * @return The bytes for date.
   */
  com.google.protobuf.ByteString
      getDateBytes();

  /**
   * <code>string time = 5;</code>
   * @return The time.
   */
  java.lang.String getTime();
  /**
   * <code>string time = 5;</code>
   * @return The bytes for time.
   */
  com.google.protobuf.ByteString
      getTimeBytes();

  /**
   * <code>string diagnosis = 6;</code>
   * @return The diagnosis.
   */
  java.lang.String getDiagnosis();
  /**
   * <code>string diagnosis = 6;</code>
   * @return The bytes for diagnosis.
   */
  com.google.protobuf.ByteString
      getDiagnosisBytes();
}
