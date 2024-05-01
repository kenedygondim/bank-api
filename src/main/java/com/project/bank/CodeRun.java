package com.project.bank;

import com.project.bank.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CodeRun implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CodeRun(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        String cliente1 = "INSERT INTO tb_clientes (primeiro_nome,sobrenome, cpf, data_nascimento, email, numero_telefone) VALUES (\"Carissea\", \"Alves Gomes\", \"01775426327\", \"07/07/2004\", \"cari@gmail.com\", \"11965803585\")";
        String cliente2 = "INSERT INTO tb_clientes (primeiro_nome,sobrenome, cpf, data_nascimento, email, numero_telefone) VALUES (\"Kenedy\", \"Alves Gondim\", \"09743328360\", \"07/07/2004\", \"kenedyal478@gmail.com\", \"11965803584\") ;\n";
        String conta1 = "INSERT INTO tb_contas (status_conta, tipo_conta, agencia, conta, saldo, cliente_id, data_criacao) VALUES (0, 0, \"0001\", \"45339-4\", 100.0, 1, NOW());";
        String conta2 = "INSERT INTO tb_contas (status_conta, tipo_conta, agencia, conta, saldo, cliente_id, data_criacao) VALUES (0, 0, \"0001\", \"45339-5\", 10.0, 2, NOW());";
        String endereco1 = "INSERT INTO tb_enderecos (cep,uf,cliente_id,complemento,numero,bairro,cidade,logradouro) VALUES (\"09971360\", \"sp\", 1, \"a\", \"134\", \"eldorado\", \"diadema\",\"av.afranio peixoto\");";
        String transf1 = "INSERT INTO tb_transferencias (valor, data_transferencia, remetente_id, destinatario_id) VALUES (10.0, NOW(), 1, 2);";
        String transf2 = "INSERT INTO tb_transferencias (valor, data_transferencia, remetente_id, destinatario_id) VALUES (10.0, NOW(), 2, 1);";
        String senha1 = "INSERT INTO tb_senhas_transacoes (conta_id, senha) values (2, \"654321\")";
        String senha2 = "INSERT INTO tb_senhas_transacoes (conta_id, senha) values (1, \"123456\")";

        jdbcTemplate.update(cliente1);
        jdbcTemplate.update(cliente2);
        jdbcTemplate.update(conta1);
        jdbcTemplate.update(conta2);
        jdbcTemplate.update(endereco1);
        jdbcTemplate.update(transf1);
        jdbcTemplate.update(transf2);
        jdbcTemplate.update(senha1);
        jdbcTemplate.update(senha2);
    }
}
