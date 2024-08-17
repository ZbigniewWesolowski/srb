package pl.srb.srb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.srb.srb.model.Role;
import pl.srb.srb.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // Endpoint do pobierania wszystkich ról
    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    // Endpoint do pobierania roli według ID
    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    // Endpoint do pobierania roli według nazwy
    @GetMapping("/name/{name}")
    public Role getRoleByName(@PathVariable String name) {
        return roleService.getRoleByName(name);
    }

    // Endpoint do tworzenia nowej roli
    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.saveRole(role);
    }

    // Endpoint do usunięcia roli według ID
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }
}