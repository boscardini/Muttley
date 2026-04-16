package com.example.Muttley.Palestrante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/palestrante")
public class PalestranteController {

	@Autowired
	private PalestranteService service;

	@Autowired
	private PalestranteMapper mapper;


	// LISTAGEM
	@GetMapping
	public String listar(Model model) {
		model.addAttribute("listaPalestrantes", service.procurarTodos());
		return "palestrante/listagem";
	}

	// FORMULÁRIO
	@GetMapping("/formulario")
	public String formulario(@RequestParam(required = false) Long id, Model model) {

    // LISTAGEM (igual ao aluno)
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("listaPalestrantes", service.procurarTodos());
        return "palestrante/listagem";
    }

    // CADASTRO (igual ao /cadastro-aluno)
    @GetMapping("/cadastro-palestrante")
    public String mostrarFormulario(@RequestParam(required = false) Long id, Model model) {


		AtualizacaoPalestrante dto;


		if (id != null) {
			Palestrante p = service.procurarPorId(id)
					.orElseThrow(() -> new EntityNotFoundException("Palestrante não encontrado"));

        if (id != null) {
            Palestrante p = service.procurarPorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Palestrante não encontrado"));


			dto = mapper.toDTO(p);
		} else {
			dto = new AtualizacaoPalestrante(null, "", "", "");
		}


		model.addAttribute("palestrante", dto);
		return "palestrante/formulario";
	}

	// SALVAR
	@PostMapping("/salvar")
	public String salvar(@ModelAttribute("palestrante") @Valid AtualizacaoPalestrante dto, BindingResult result) {

        model.addAttribute("palestrante", dto);
        return "palestrante/cadastro"; // 🔥 igual aluno
    }

    // FORMULÁRIO POR ID (igual aluno/formulario/{id})
    @GetMapping("/formulario/{id}")
    public String carregarFormulario(@PathVariable("id") Long id,
                                     Model model,
                                     RedirectAttributes redirectAttributes) {

        try {
            Palestrante p = service.procurarPorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Palestrante não encontrado"));

            AtualizacaoPalestrante dto = mapper.toDTO(p);
            model.addAttribute("palestrante", dto);

            return "palestrante/formulario";

        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/palestrante";
        }
    }

    // SALVAR (igual aluno)
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("palestrante") @Valid AtualizacaoPalestrante dto,
                         BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Model model) {

<<<<<<< HEAD
        // 🔴 erro de validação
        if (result.hasErrors()) {
            return dto.id() == null 
                ? "palestrante/cadastro" 
                : "palestrante/formulario";
        }
=======

		if (result.hasErrors()) {
			return "palestrante/formulario";
		}


		service.salvarOuAtualizar(dto);
		return "redirect:/palestrante";
	}

	// DELETE
	@GetMapping("/delete/{id}")
	public String deletar(@PathVariable Long id) {
		service.apagarPorId(id);
		return "redirect:/palestrante";
	}
>>>>>>> 95a0cf7548a321539d9403468a459187e22cd9db

        try {
            Palestrante salvo = service.salvarOuAtualizar(dto);

            String mensagem = dto.id() != null
                    ? "Palestrante " + salvo.getNome() + "' atualizado com sucesso!"
                    : "Palestrante " + salvo.getNome()+ "' criado com sucesso!";

            redirectAttributes.addFlashAttribute("message", mensagem);
            return "redirect:/palestrante";

        } catch (Exception e) {
            // 🔥 MOSTRA ERRO REAL (repository/banco)
            model.addAttribute("error", e.getMessage());
            model.addAttribute("palestrante", dto);

            return dto.id() == null 
                ? "palestrante/cadastro" 
                : "palestrante/formulario";
        }
    }

    // DELETE (igual aluno)
    @GetMapping("/delete/{id}")
    @Transactional
    public String deletar(@PathVariable("id") Long id,
                          RedirectAttributes redirectAttributes) {

        try {
            service.apagarPorId(id);
            redirectAttributes.addFlashAttribute("message",
                    "O palestrante " + id + " foi apagado!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/palestrante";
    }

}