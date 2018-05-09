package com.pharmacy.healthcare.controller;

import com.pharmacy.healthcare.domain.Medicine;
import com.pharmacy.healthcare.services.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.Collection;

@RestController
@RequestMapping("/medicines")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllMedicines()
    {
        try {
            Collection<Medicine> medicines = medicineService.getAllMedicines();
            if (medicines.isEmpty()){
                throw new Exception("No medicines available");
            }
            return new ResponseEntity<>(medicines, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("error, message: " + e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "{pharmacy_id}")
    public ResponseEntity<?> addMedicine( @PathVariable long pharmacy_id, @RequestBody Medicine medicine)
    {
        try{
            medicineService.addMedicine(pharmacy_id, medicine);
            return ResponseEntity.ok("Medicine successfully added to the system");
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Error with processing your request please try again");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "{medicine_id}")
    public ResponseEntity<?> getMedicine(@PathVariable long medicine_id)
    {
        try {
            Medicine medicine = medicineService.getMedicine(medicine_id);
            if (medicine.getId() == null)
            {
                throw new Exception("No medicine found");
            }
            return new ResponseEntity<>(medicine, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("error, message: " + e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{medicine_id}")
    public ResponseEntity<?> deleteMedicine(@PathVariable long medicine_id)
    {
        try{
            medicineService.deleteMedicine(medicine_id);
            return ResponseEntity.ok("Medicine succesfully deleted");
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Error with processing your request please try again");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/order/{medicine_id}")
    public ResponseEntity<?> orderMedicine(@PathVariable long medicine_id, @RequestParam long patient_id, @RequestParam long quantity)
    {
        try {
            return new ResponseEntity<>(medicineService.orderMedicine(medicine_id, quantity, patient_id), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Failed to order medicines");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update/{medicine_id}")
    public ResponseEntity<?> updateMedicine(@PathVariable long medicine_id, @RequestParam long quantity)
    {
        try
        {
            return new ResponseEntity<>(medicineService.updateStock(medicine_id, quantity), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Failed to update medicines");
        }
    }
}
