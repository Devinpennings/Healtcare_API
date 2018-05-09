package com.pharmacy.healthcare.services;

import com.pharmacy.healthcare.domain.Medicine;
import com.pharmacy.healthcare.domain.Patient;
import com.pharmacy.healthcare.domain.Pharmacy;
import com.pharmacy.healthcare.domain.Prescription;
import com.pharmacy.healthcare.repository.MedicineRepository;
import com.pharmacy.healthcare.repository.PatientRepository;
import com.pharmacy.healthcare.repository.PharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service("medicineService")
public class MedicineService {

    @Autowired
    MedicineRepository medicineRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PharmacyRepository pharmacyRepository;

    public void addMedicine(long pharmacy_id, Medicine medicine)
    {
        Pharmacy pharmacy = pharmacyRepository.findOne(pharmacy_id);
        pharmacy.addMedicine(medicine);
        pharmacyRepository.save(pharmacy);
    }

    public void deleteMedicine(long medicine_id)
    {
        medicineRepository.delete(medicine_id);
    }

    public Collection<Medicine> getAllMedicines()
    {
        return medicineRepository.findAll();
    }

    public Long orderMedicine(long medicine_id, long quantity, long patient_id) {
        Medicine medicine = medicineRepository.findOne(medicine_id);
        Patient patient = patientRepository.findOne(patient_id);
        Prescription prescription = new Prescription();
        prescription.setMedicine(medicine, quantity);
        prescription.setPatient(patient);
        return medicine.getStock();
    }

    public Medicine getMedicine(long medicine_id)
    {
        return medicineRepository.findOne(medicine_id);
    }

    public Long updateStock(long medicine_id, long quantity)
    {
        Medicine medicine = medicineRepository.findOne(medicine_id);
        long stock = medicine.updateStock(quantity);
        medicineRepository.save(medicine);
        return stock;
    }
}
