package com.leancode.formspring.editors;

import java.beans.PropertyEditorSupport;

import com.leancode.formspring.services.interfaces.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RolePropertyEditor extends PropertyEditorSupport{

    @Autowired
    private RoleService roleService;

    @Override
    public void setAsText(String id) throws IllegalArgumentException {
        for (var rol : roleService.listar()) {
            var rolId = rol.getId().toString();
            if (rolId.equals(id)) {
                this.setValue(roleService.obtenerPorId(Integer.parseInt(id)));
            } else {
                continue;
            }
        }
    }
    
}
