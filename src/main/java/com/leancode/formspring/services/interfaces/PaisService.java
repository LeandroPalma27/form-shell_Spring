package com.leancode.formspring.services.interfaces;

import java.util.List;

import com.leancode.formspring.models.domain.Pais;

public interface PaisService {
    
    public List<Pais> listar();
    public Pais obtenerPorId(Integer id);

}
