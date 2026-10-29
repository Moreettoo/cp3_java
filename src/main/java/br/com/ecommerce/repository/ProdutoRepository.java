package br.com.ecommerce.repository;

import br.com.ecommerce.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByCategoria(String categoria);
    List<Produto> findByPrecoLessThan(Double preco);
    List<Produto> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT p FROM Produto p WHERE p.estoque < :minimo")
    List<Produto> buscarEstoqueBaixo(@Param("minimo") Integer minimo);
}
