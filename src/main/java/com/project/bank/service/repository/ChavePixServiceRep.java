package com.project.bank.service.repository;

import com.project.bank.entity.form.ChavePixForm;
import com.project.bank.entity.model.ChavePix;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChavePixServiceRep
{
   ChavePix cadastrarChavePix(ChavePixForm chavePix);
   List<ChavePix> listarChavesPixConta(long id);
   void excluirChavePix(long id);
}
