package com.project.bank.service.repository;

import com.project.bank.entity.form.ChavePixForm;
import com.project.bank.entity.model.ChavePix;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface ChavePixServiceRep
{
   ChavePix cadastrarChavePix(ChavePixForm chavePix, String cpf);
   List<ChavePix> listarChavesPixConta(String cpf);
   String excluirChavePix(ChavePixForm chavePixForm, String cpf);
}
