package com.project.bank;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientDtoTeste
{
    @Test
    void idadeDeveSer19Anos()
    {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("07/07/2004", formatador);
        Period period = Period.between(date, LocalDate.now());

        assertEquals(19, period.getYears());
    }
}
