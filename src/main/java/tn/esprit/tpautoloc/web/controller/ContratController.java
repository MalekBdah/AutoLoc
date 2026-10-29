package tn.esprit.tpautoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import tn.esprit.tpautoloc.domain.Contrat;
import tn.esprit.tpautoloc.service.IContratService;

import java.util.List;

@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
public class ContratController {

    private final IContratService contratService;

    // GET : Afficher tous les contrats
    @GetMapping
    public List<Contrat> findAll() {
        return contratService.findAll();
    }

    // GET : Afficher un contrat par ID
    @GetMapping("/{id}")
    public Contrat findById(@PathVariable Long id) {
        return contratService.findById(id);
    }

    // POST : Ajouter un contrat
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contrat create(@RequestBody Contrat contrat) {
        return contratService.create(contrat);
    }

    // PUT : Modifier un contrat
    @PutMapping("/{id}")
    public Contrat update(
            @PathVariable Long id,
            @RequestBody Contrat contrat) {

        return contratService.update(id, contrat);
    }

    // DELETE : Supprimer un contrat
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        contratService.deleteById(id);
    }
}