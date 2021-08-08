package br.com.zup.mercadolivre.desafiomercadolivre.opiniao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpiniaoRepository extends JpaRepository<Opiniao, Long> {
    List<Opiniao> findAllByProdutoId(Long produtoId);
}
