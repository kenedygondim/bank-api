package com.project.bank.config;

import com.project.bank.entity.dto.TransactionDto;
import com.project.bank.entity.model.Transaction;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig
{
    @Bean
    public ModelMapper modelMapper()
    {
        var modelMapper = new ModelMapper();
        /*modelMapper.addMappings(new PropertyMap<Transaction, TransactionDto>()
        {
            @Override
            protected void configure(){
                skip(destination.transactionPassword());
            }
        });*/

        return modelMapper;
    }
}
