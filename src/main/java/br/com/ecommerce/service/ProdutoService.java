package br.com.ecommerce.service;

import br.com.ecommerce.model.Produto;
import br.com.ecommerce.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public List<Produto> listarTodos() {
        return repository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Produto não encontrado: " + id));
    }

    public List<Produto> buscarPorCategoria(String categoria) {
        return repository.findByCategoria(categoria);
    }

    public Produto criar(Produto produto) {
        produto.setId(null); // novo registro: ID gerado pelo banco
        validarProduto(produto);
        return repository.save(produto);
    }

    public Produto atualizar(Long id, Produto dados) {
        Produto atual = buscarPorId(id); // 404 se não existir
        validarProduto(dados);
        atual.setNome(dados.getNome());
        atual.setPreco(dados.getPreco());
        atual.setCategoria(dados.getCategoria());
        atual.setEstoque(dados.getEstoque());
        return repository.save(atual);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Produto não encontrado: " + id);
        }
        repository.deleteById(id);
    }

    private void validarProduto(Produto produto) {
        if (produto.getNome() == null || produto.getNome().isBlank())
            throw new IllegalArgumentException("Nome é obrigatório");
        if (produto.getPreco() == null || produto.getPreco() < 0)
            throw new IllegalArgumentException("Preço inválido");
        if (produto.getEstoque() == null || produto.getEstoque() < 0)
            throw new IllegalArgumentException("Estoque inválido");
    }
}
