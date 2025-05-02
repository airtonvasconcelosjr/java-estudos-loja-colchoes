package com.colchoesapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

import com.colchoesapi.model.ImagemColchao;
import com.colchoesapi.model.Colchao;
import com.colchoesapi.repository.ImagemColchaoRepository;
import com.colchoesapi.repository.ColchaoRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImagemColchaoService {

    @Autowired
    private ImagemColchaoRepository imagemColchaoRepository;
    
    @Autowired
    private ColchaoRepository colchaoRepository;
    
    @Cacheable(value = "imagensByColchao", key = "#colchaoId")
    public List<ImagemColchao> findByColchaoId(Long colchaoId) {
        return imagemColchaoRepository.findByColchaoId(colchaoId);
    }
    
    @Cacheable(value = "imagemById", key = "#id")
    public Optional<ImagemColchao> findById(Long id) {
        return imagemColchaoRepository.findById(id);
    }
    
    @CacheEvict(value = {"imagensByColchao", "imagemById"}, allEntries = true)
    public ImagemColchao saveImagem(Long colchaoId, MultipartFile arquivo) throws IOException {
        Optional<Colchao> colchaoOpt = colchaoRepository.findById(colchaoId);
        
        if (colchaoOpt.isPresent()) {
            Colchao colchao = colchaoOpt.get();
            
            String nomeArquivo = StringUtils.cleanPath(arquivo.getOriginalFilename());
            ImagemColchao imagem = new ImagemColchao(
                nomeArquivo,
                arquivo.getContentType(),
                arquivo.getBytes()
            );
            
            colchao.adicionarImagem(imagem);
            colchaoRepository.save(colchao);
            
            return imagem;
        } else {
            throw new RuntimeException("Colchão não encontrado com ID: " + colchaoId);
        }
    }
    
    @CacheEvict(value = {"imagensByColchao", "imagemById"}, allEntries = true)
    public void deleteImagem(Long id) {
        imagemColchaoRepository.deleteById(id);
    }
}