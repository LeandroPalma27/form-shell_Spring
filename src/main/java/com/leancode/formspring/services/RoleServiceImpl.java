package com.leancode.formspring.services;

import java.util.ArrayList;
import java.util.List;

import com.leancode.formspring.models.domain.Role;
import com.leancode.formspring.services.interfaces.RoleService;

import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

    private List<Role> roles;

    // LLamando a la clase automaticamente nos genera la lista de roles:
    public RoleServiceImpl() {
        this.roles = new ArrayList<>();
        this.roles.add(new Role(1, "ROLE_ADMIN", "Administrador"));
        this.roles.add(new Role(2, "ROLE_USER", "Usuario"));
        this.roles.add(new Role(3, "ROLE_MODERATOR", "Moderador"));
    }

    @Override
    public List<Role> listar() {
        return roles;
    }

    @Override
    public Role obtenerPorId(Integer id) {
        Role rol = null;
        for (var role: roles) {
            if (role.getId().equals(id)) {
                rol = role;
                break;
            }
        }
        return rol;
    }
    
}
