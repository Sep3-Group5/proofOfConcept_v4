﻿using System.Collections;
using Domain.DTOs;
using Domain.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.ChangeTracking;

namespace EFC.DAOs;

public class PatientDao : IPatientDao
{
    private readonly Context context;

    public PatientDao(Context context)
    {
        this.context = context;
    }
    
    public async Task<Patient> CreateAsync(Patient patient)
    {
        Console.Write(patient.Name);
        EntityEntry<Patient> newPatient = await context.Patients.AddAsync(patient);
        await context.SaveChangesAsync();
        return newPatient.Entity;
    }

    public async Task<IEnumerable<Patient>> GetAsync()
    {
        IQueryable<Patient> patientsQuery = context.Patients.AsQueryable();
        List<Patient> patients = await patientsQuery.ToListAsync();
        return patients;
    }

    public async Task<IEnumerable<Patient>> GetAsync(PatientSearchDto dto)
    {
        IQueryable<Patient> patientsQuery = context.Patients.AsQueryable(); 
        if (dto.NameContains != null)
        {
            patientsQuery = patientsQuery.Where(p => p.Name.ToLower().Contains(dto.NameContains.ToLower()));
        }
        IEnumerable<Patient> result = await patientsQuery.ToListAsync();
        return result;
    }

    public async Task<Patient?> GetAsync(int id)
    {
        Patient? patient = await context.Patients.FindAsync(id);
        if (patient == null)
        {
            throw new Exception($"No Patient with id: {id}");
        }
        return patient;
    }

    public async Task UpdateAsync(Patient patient)
    {
        Patient? existing = await GetAsync(patient.Id);
        if (existing == null)
        {
            throw new Exception($"No Patient with id: {patient.Id}");
        }
        context.Patients.Update(patient);
        await context.SaveChangesAsync();
    }

    public async Task DeleteAsync(int id)
    {
        Patient? existing = await GetAsync(id);
        if (existing == null)
        {
            throw new Exception($"No Patient with id: {id}");
        }
        context.Patients.Remove(existing);
        await context.SaveChangesAsync();
    }

    public Task<Patient?> LoginAsPatient(string username, string paswd)
    {
        throw new NotImplementedException();
        /*
        Patient? patient = await context.Patients.FirstOrDefaultAsync(patient =>
            patient.Name.Equals(username) && patient.Password.Equals(paswd));

        if (patient == null)
        {
            throw new Exception("Username or password incorrect");
        }
        
        return patient;
        */
    }
}