using System.Data;
using System.Net.Http.Json;
using System.Text.Json;
using Domain.DTOs;
using Domain.Models;
using HttpClients.ClientInterfaces;

namespace HttpClients.Implementations;

public class AppointmentHttpClient : IAppointmentService
{
    private readonly HttpClient client;
    
    private String url ="http://localhost:8989/"; 

    public AppointmentHttpClient(HttpClient client)
    {
        this.client = client;
    }

    public async Task<Appointment> CreateAsync(AppointmentCreationDto dto)
    {
        HttpResponseMessage response = await client.PostAsJsonAsync(url+"appointments", dto);
        string result = await response.Content.ReadAsStringAsync();
        if (!response.IsSuccessStatusCode)
        {
            throw new Exception(result);
        }

        Appointment appointment = JsonSerializer.Deserialize<Appointment>(result);
        return appointment;
    }

    public async Task<List<Appointment>> getAppointmentsByDateDoctor(DoctorViewAppointmentsDto dto)
    {
        HttpResponseMessage response = await client.PostAsJsonAsync(url + "Doctor/Appointments", dto);
        string result = await response.Content.ReadAsStringAsync();
        if (!response.IsSuccessStatusCode)
        {
            throw new Exception(result);
        }

        List<Appointment> appointments = JsonSerializer.Deserialize<List<Appointment>>(result, new JsonSerializerOptions
        {
            PropertyNameCaseInsensitive = true
        });
        return appointments;
    }

    public async Task<ICollection<Appointment>> GetAsync(int appointmentId, int patientId, int doctorId, string diagnostic, bool status, string date, string time)
    {
	    string query = ConstructQuery(appointmentId, patientId, doctorId, diagnostic, status,date, time);

	    HttpResponseMessage response = await client.GetAsync("/appointments"+query);
	    string content = await response.Content.ReadAsStringAsync();
	    if (!response.IsSuccessStatusCode)
	    {
		    throw new Exception(content);
	    }

	    ICollection<Appointment> appointments = JsonSerializer.Deserialize<ICollection<Appointment>>(content, new JsonSerializerOptions
	    {
		    PropertyNameCaseInsensitive = true
	    })!;
	    return appointments;
    }

    private static string ConstructQuery(int appointmentId, int patientId, int doctorId, string diagnostic, bool status, string date, string time)
    {
	    string query = "";
	    if (appointmentId != null)
	    {
		    query += $"?appointmentId={appointmentId}";
	    }
	    if (patientId != null)
	    {
		    query += $"?patientId={patientId}";
	    }
	    if (doctorId != null)
	    {
		    query += $"?doctorId={doctorId}";
	    }
	    if (string.IsNullOrEmpty(diagnostic))
	    {
		    query += $"?diagnostic={diagnostic}";
	    }
	    if (status != null)
	    {
		    query += $"?=status{status}";
	    }
	    if (string.IsNullOrEmpty(date))
	    {
		    query += $"?date={date}";
	    }
	    if (string.IsNullOrEmpty(time))
	    {
		    query += $"?time={time}";
	    }

	    return query;
    }
}
