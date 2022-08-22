package com.leancode.formspring.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.leancode.formspring.models.domain.Pais;
import com.leancode.formspring.services.interfaces.PaisService;

import org.springframework.stereotype.Service;

@Service
public class PaisServiceImpl implements PaisService{

    private List<Pais> paises;

    public PaisServiceImpl() {
        this.paises = new ArrayList<Pais>();
        this.paises.add(new Pais(1, "PER", "Peru"));
        this.paises.add(new Pais(2, "CHI", "Chile"));
        this.paises.add(new Pais(3, "COL", "Colombia"));
    }

    @Override
    public List<Pais> listar() {
        System.out.println("hola");
        for (var pais : paises) {
            /* System.out.println(pais.getNombre()); */
        }
        return paises;
    }

    @Override
    public Pais obtenerPorId(Integer id) {
        Pais _pais = null;
        for (var pais: paises) {
            if (pais.getId() == id) {
                _pais = pais;   
                break;
            }
        } 
        return _pais;
    }
    
}
