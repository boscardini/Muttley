package com.example.Muttley.infra.security;

import com.example.Muttley.participante.Participante;
import com.example.Muttley.participante.ParticipanteRepository;
import com.example.Muttley.usuario.Usuario;
import com.example.Muttley.usuario.UsuarioRepository;
import com.example.Muttley.usuario.UsuarioRequestDTO;
import com.example.Muttley.usuario.UsuarioResponseDTO;
import com.example.Muttley.usuario.UsuarioService;

import jakarta.validation.Valid;

import com.example.Muttley.infra.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
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
    private final UsuarioService usuarioService;

    @PostMapping("/cadastro/gestor")
    public ResponseEntity<UsuarioResponseDTO> cadastrarGestor(@RequestBody @Valid UsuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvar(dto));
    }

    @PostMapping("/login/participante")
    public ResponseEntity<Map<String, Object>> loginParticipante(@RequestBody LoginParticipanteDTO dto) {
        Participante participante = participanteRepository.findByCpf(dto.cpf())
                .orElseThrow(() -> new RegraDeNegocioException("CPF não cadastrado no sistema."));

        if (!participante.getDataNascimento().equals(dto.dataNascimento())) {
            throw new RegraDeNegocioException("Data de nascimento incorreta.");
        }

        String token = tokenService.gerarToken(participante.getId(), participante.getCpf(), "PARTICIPANTE");

        return ResponseEntity.ok(Map.of(
                "token", token,
                "nome", participante.getNome(),
                "role", "PARTICIPANTE"
        ));
    }

    @PostMapping("/login/gerencial")
    public ResponseEntity<Map<String, Object>> loginGerencial(@RequestBody LoginGerencialDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RegraDeNegocioException("Usuário ou senha inválidos."));

        if (!usuario.getSenha().equals(dto.senha())) {
            throw new RegraDeNegocioException("Usuário ou senha inválidos.");
        }

        if (usuario.getTipo() == com.example.Muttley.usuario.TipoUsuario.GESTOR && !usuario.isAprovado()) {
            throw new RegraDeNegocioException("Seu cadastro de Gestor ainda está pendente de aprovação pelo Administrador.");
        }

        String token = tokenService.gerarToken(usuario.getId(), usuario.getEmail(), usuario.getTipo().name());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "email", usuario.getEmail(),
                "nome", usuario.getNome(),
                "role", usuario.getTipo().name()
        ));
    }
}

record LoginGerencialDTO(String email, String senha) {}
record LoginParticipanteDTO(String cpf, LocalDate dataNascimento) {}