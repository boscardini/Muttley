package com.example.Muttley.aluno;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// Importante: verifique se você tem um CursoService ou remova se não for usar
// import com.example.Muttley.curso.CursoService; 

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

	@Autowired
	private AlunoService alunoService;

	@Autowired
	private AlunoMapper alunoMapper;

	// @Autowired
	// private CursoService cursoService; // Antigo MarcaService

	@GetMapping
	public String carregaPaginaFormulario(Model model) {
		// Adiciona a lista de alunos ao model para a página de listagem
		model.addAttribute("listaAlunos", alunoService.procurarTodos());
		return "aluno/listagem";
	}

	@GetMapping("/login")
	public String telaLogin() {
		return "aluno/login"; // Procura o arquivo login.html na pasta templates/aluno/
	}

	@GetMapping("/cadastro-aluno")
	public String mostrarFormulario(@RequestParam(required = false) Long id, Model model) {
		AtualizacaoAluno dto;
		if (id != null) {
			Aluno aluno = alunoService.procurarPorId(id)
					.orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
			dto = alunoMapper.toAtualizacaoDto(aluno);
		} else {

			dto = new AtualizacaoAluno(null, "", "", "", "", "", "");
		}
		model.addAttribute("aluno", dto);
		return "aluno/cadastro";
	}

	@GetMapping("/formulario/{id}")
	public String carregaPaginaFormulario(@PathVariable("id") Long id, Model model,
			RedirectAttributes redirectAttributes) {
		AtualizacaoAluno dto;
		try {
			if (id != null) {
				Aluno aluno = alunoService.procurarPorId(id)
						.orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
				// model.addAttribute("cursos", cursoService.procurarTodos());

				// Mapear aluno para AtualizacaoAluno
				dto = alunoMapper.toAtualizacaoDto(aluno);
				model.addAttribute("aluno", dto);
			}
			return "aluno/formulario=";
		} catch (EntityNotFoundException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/aluno/login";
		}
	}

	@PostMapping("/salvar")
	public String salvar(@ModelAttribute("aluno") @Valid AtualizacaoAluno dto, BindingResult result,
	        RedirectAttributes redirectAttributes) {
	    
	    if (result.hasErrors()) {
	        return "aluno/cadastro";
	    }

	    try {
	        alunoService.salvarOuAtualizar(dto);
	        
	        // Se foi uma edição, a mensagem é específica
	        String mensagem = (dto.id() != null) ? "Usuário Editado com Sucesso!" : "Cadastro realizado com sucesso!";
	        redirectAttributes.addFlashAttribute("message", mensagem);
	        
	        // Redireciona para a página de perfil (exibição dos dados)
	        return "redirect:/aluno"; 

	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "Erro ao processar: " + e.getMessage());
	        return "redirect:/aluno/cadastro-aluno";
	    }
	}

	@GetMapping("/delete/{id}")
	@Transactional
	public String deletarAluno(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
		try {
			alunoService.apagarPorId(id);
			redirectAttributes.addFlashAttribute("message", "O aluno " + id + " foi apagado!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/aluno/login";
	}
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Long id, Model model) {
	    Aluno aluno = alunoService.procurarPorId(id)
	        .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
	    
	    // Converte a Entity para o DTO que o formulário usa
	    AtualizacaoAluno dto = alunoMapper.toAtualizacaoDto(aluno);
	    
	    model.addAttribute("aluno", dto);
	    return "aluno/editar"; // Reutiliza o mesmo HTML de cadastro
	}
	
	@PostMapping("/login")
	public String logar(@RequestParam String email, @RequestParam String senha, RedirectAttributes attributes) {
	    Optional<Aluno> alunoLogado = alunoService.realizarLogin(email, senha);

	    if (alunoLogado.isPresent()) {
	        // No futuro, aqui você guardaria o aluno na "Sessão"
	        return "redirect:/aluno"; 
	    } else {
	        attributes.addFlashAttribute("error", "E-mail ou senha inválidos!");
	        return "redirect:/aluno/login";
	    }
	}
}