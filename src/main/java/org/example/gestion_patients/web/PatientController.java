package org.example.gestion_patients.web;


import jakarta.validation.Valid;
import org.example.gestion_patients.entities.Patient;
import org.example.gestion_patients.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;

    // if the params with the same name we can get rid of the @RequestParam annotation
    @GetMapping("/user/index")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "0") int page,
                        @RequestParam(name = "size",defaultValue = "5")int size,
                        @RequestParam(name="keyword",defaultValue = "") String kw,
                        @RequestParam(name="keyword",defaultValue = "") String kw1
    )
    {
        Page<Patient> pagePatients = patientRepository.findPatientByNomContainsIgnoreCaseOrPrenomContainsIgnoreCase(kw,kw1, PageRequest.of(page, size));
        model.addAttribute("listPatients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", kw);
        return "patients";
    }

//    @GetMapping("/admin/delete")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String deletePatient(@RequestParam("id") Long id,
//                                @RequestParam("keyword") String keyword,
//                                @RequestParam("page") int page){
//        patientRepository.deleteById(id);
//
//        return "redirect:/user/index?page="+page+"&keyword="+keyword;
//    }

    @GetMapping("/admin/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deletePatient(@RequestParam("id") Long id,
                                                @RequestParam("keyword") String keyword,
                                                @RequestParam("page") int page){
        patientRepository.deleteById(id);

        return ResponseEntity.ok("Patient deleted successfully");
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }

    @GetMapping("/admin/formPatients")
    public String formPatients(Model model){
        model.addAttribute("patient", new Patient());
        return "formPatients";
    }

    @PostMapping("/admin/savePatient")
    public String savePatient(@Valid Patient patient, BindingResult bindingResult){
        if(!bindingResult.hasErrors()) {
            patientRepository.save(patient);
            return "redirect:/user/index?keyword="+patient.getNom();
        }
        return "formPatients";

    }

    @GetMapping("/admin/editPatients")
    public String editPatient(Model model, Long id){
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) return "redirect:/user/index";

        model.addAttribute("patient", patient.get());
        return "editPatients";
    }


}
