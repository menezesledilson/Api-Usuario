package com.apiusuario.demousuario.Service;

import Usuario.User;
import com.apiusuario.demousuario.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User create(User usuario) {

        // Verifica se o CPF é válido
        if (!validarCPF(usuario.getCpf())) {
            throw new IllegalArgumentException("CPF inválido.");
        }

        // Verifica se o CPF já existe no banco de dados
        if (userRepository.existsByCpf(usuario.getCpf())) {
            throw new IllegalArgumentException("CPF já existe.");
        }

        return this.userRepository.insert(usuario);
    }

    public List<User> getAll() {
        return this.userRepository.findAll();
    }

    public User delete(String id) {
        User user = this.userRepository.findById(id).orElse(null);
        if (user != null)
            this.userRepository.deleteById(id);
        return user;
    }

    private boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("\\D", "");

        // Verifica se o CPF possui 11 dígitos
        if (cpf.length() != 11)
            return false;

        // Verifica se todos os dígitos são iguais, o que tornaria o CPF inválido
        if (cpf.matches("(\\d)\\1{10}"))
            return false;

        // Calcula o primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int resto = soma % 11;
        int digitoVerificador1 = (resto < 2) ? 0 : (11 - resto);

        // Calcula o segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        resto = soma % 11;
        int digitoVerificador2 = (resto < 2) ? 0 : (11 - resto);

        // Verifica se os dígitos verificadores calculados são iguais aos dígitos do CPF
        return (digitoVerificador1 == Character.getNumericValue(cpf.charAt(9))
                && digitoVerificador2 == Character.getNumericValue(cpf.charAt(10)));
    }
}

