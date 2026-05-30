package com.example.Muttley.infra.security;

import com.example.Muttley.usuario.TipoUsuario;
import com.example.Muttley.usuario.Usuario;
import com.example.Muttley.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;

    // Injeta os valores definidos no application.properties
    @Value("${muttley.admin.default-user}")
    private String adminUser;

    @Value("${muttley.admin.default-password}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {
        if (!usuarioRepository.existsByTipo(TipoUsuario.ADMIN)) {
            Usuario primeiroAdmin = new Usuario();
            primeiroAdmin.setNome("Administrador Principal");
            primeiroAdmin.setEmail(adminUser);
            primeiroAdmin.setSenha(adminPassword);
            primeiroAdmin.setTipo(TipoUsuario.ADMIN);
            primeiroAdmin.setAprovado(true);

            usuarioRepository.save(primeiroAdmin);
            System.out.println(">>> [Muttley API] Primeiro Administrador criado com sucesso via DatabaseSeeder! <<<");
        }
    }
}