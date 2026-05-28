package br.com.sias.api.service;

import br.com.sias.api.dto.UsuarioRequest;
import br.com.sias.api.dto.UsuarioResponse;
import br.com.sias.api.exception.NotFoundException;
import br.com.sias.api.model.Usuario;
import br.com.sias.api.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository repository;

    public UsuarioService(
            PasswordEncoder passwordEncoder,
            UsuarioRepository repository
    ) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    private UsuarioResponse converterParaResponse(Usuario usuario) {

        UsuarioResponse response = new UsuarioResponse();

        response.setId(usuario.getId());
        response.setLogin(usuario.getLogin());
        response.setNome(usuario.getNome());
        response.setPerfil(usuario.getPerfil());
        response.setEmail(usuario.getEmail());

        return response;
    }

    public UsuarioResponse salvar(UsuarioRequest dados) {

        if (repository.existsByEmail(dados.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        if (repository.existsByLogin(dados.getLogin())) {
            throw new RuntimeException("Login já cadastrado");
        }

        if (dados.getPerfil() == null) {
            throw new RuntimeException("Perfil deve ser selecionado");
        }

        Usuario usuario = new Usuario();

        usuario.setNome(dados.getNome());
        usuario.setLogin(dados.getLogin());
        usuario.setEmail(dados.getEmail());
        usuario.setPerfil(dados.getPerfil());

        usuario.setSenha(
                passwordEncoder.encode(dados.getSenha())
        );

        Usuario salvo = repository.save(usuario);

        return converterParaResponse(salvo);
    }

    public UsuarioResponse atualizar(Long id, UsuarioRequest dados) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Usuário não encontrado"));

        usuario.setNome(dados.getNome());
        usuario.setEmail(dados.getEmail());
        usuario.setLogin(dados.getLogin());
        usuario.setPerfil(dados.getPerfil());

        if (dados.getSenha() != null &&
                !dados.getSenha().isBlank()) {

            usuario.setSenha(
                    passwordEncoder.encode(dados.getSenha())
            );
        }

        Usuario atualizado = repository.save(usuario);

        return converterParaResponse(atualizado);
    }

    public UsuarioResponse deletar(Long id) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Usuário não encontrado"));

        repository.delete(usuario);

        return converterParaResponse(usuario);
    }

    public List<UsuarioResponse> listar() {

        return repository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public UsuarioResponse buscarPorId(Long id) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Usuário não encontrado"));

        return converterParaResponse(usuario);
    }
}