package com.leancode.formspring.services.interfaces;

import java.util.List;

import com.leancode.formspring.models.domain.Role;

public interface RoleService {

    public List<Role> listar();
    public Role obtenerPorId(Integer id);

}
