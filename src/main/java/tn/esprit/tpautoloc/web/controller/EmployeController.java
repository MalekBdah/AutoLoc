package tn.esprit.tpautoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.tpautoloc.domain.Employe;
import tn.esprit.tpautoloc.service.IEmployeService;

import java.util.List;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final IEmployeService employeService;

    @GetMapping
    public List<Employe> findAll() {
        return employeService.findAll();
    }

    @GetMapping("/{id}")
    public Employe findById(@PathVariable Long id) {
        return employeService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employe create(@RequestBody Employe employe) {
        return employeService.create(employe);
    }

    @PutMapping("/{id}")
    public Employe update(
            @PathVariable Long id,
            @RequestBody Employe employe) {

        return employeService.update(id, employe);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        employeService.deleteById(id);
    }
}