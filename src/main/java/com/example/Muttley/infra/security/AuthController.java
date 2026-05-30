package com.example.Muttley.infra.security;

import com.example.Muttley.participante.Participante;
import com.example.Muttley.participante.ParticipanteRepository;
import com.example.Muttley.usuario.Usuario;
import com.example.Muttley.usuario.UsuarioRepository;
import com.example.Muttley.infra.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ParticipanteRepository participanteRepository;
    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;

    @PostMapping("/login/aluno")
    public ResponseEntity<Map<String, Object>> loginAluno(@RequestBody LoginAlunoDTO dto) {
        Participante participante = participanteRepository.findByCpf(dto.cpf())
                .orElseThrow(() -> new RegraDeNegocioException("CPF não cadastrado no sistema."));

        if (!participante.getDataNascimento().equals(dto.dataNascimento())) {
            throw new RegraDeNegocioException("Data de nascimento incorreta.");
        }

        String token = tokenService.gerarToken(participante.getId(), participante.getCpf(), "ALUNO");

        return ResponseEntity.ok(Map.of(
                "token", token,
                "nome", participante.getNome(),
                "role", "ALUNO"
        ));
    }

    @PostMapping("/login/gerencial")
    public ResponseEntity<Map<String, Object>> loginGerencial(@RequestBody LoginGerencialDTO dto) {
        Usuario usuario = usuarioRepository.findByLogin(dto.login())
                .orElseThrow(() -> new RegraDeNegocioException("Usuário ou senha inválidos."));

        if (!usuario.getSenha().equals(dto.senha())) {
            throw new RegraDeNegocioException("Usuário ou senha inválidos.");
        }

        // Regra de Negócio: Se for um Gestor, ele precisa ter sido aprovado pelo Administrador antes
        if (usuario.getTipo() == com.example.Muttley.usuario.TipoUsuario.GESTOR && !usuario.isAprovado()) {
            throw new RegraDeNegocioException("Seu cadastro de Gestor ainda está pendente de aprovação pelo Administrador.");
        }

        // Emite o token contendo o papel real recuperado do banco (ADMIN ou GESTOR)
        String token = tokenService.gerarToken(usuario.getId(), usuario.getLogin(), usuario.getTipo().name());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "login", usuario.getLogin(),
                "role", usuario.getTipo().name()
        ));
    }
}

record LoginAlunoDTO(String cpf, LocalDate dataNascimento) {}
record LoginGerencialDTO(String login, String senha) {}