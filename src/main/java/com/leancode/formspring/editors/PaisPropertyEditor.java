package com.leancode.formspring.editors;

import java.beans.PropertyEditorSupport;

import com.leancode.formspring.services.interfaces.PaisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaisPropertyEditor extends PropertyEditorSupport {

    @Autowired
    private PaisService paisService;

    @Override
    public void setAsText(String id) throws IllegalArgumentException {
        for (var pais : paisService.listar()) {
            var paisId = pais.getId().toString();
            if (paisId.equals(id)) {
                this.setValue(paisService.obtenerPorId(Integer.parseInt(id)));
            } else {
                continue;
            }
        }
    }
    
}
