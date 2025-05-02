// 5. Implementar o controller para manipular as imagens
package com.colchoesapi.controller;

import com.colchoesapi.model.ImagemColchao;
import com.colchoesapi.service.ImagemColchaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/colchoes")
public class ImagemColchaoController {

    @Autowired
    private ImagemColchaoService imagemColchaoService;
    
    // Upload de imagem para um colchão específico
    @PostMapping("/{colchaoId}/imagens")
    public ResponseEntity<?> uploadImagem(@PathVariable Long colchaoId, 
                                         @RequestParam("imagem") MultipartFile arquivo) {
        try {
            ImagemColchao imagemSalva = imagemColchaoService.saveImagem(colchaoId, arquivo);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ImagemResponseDTO(
                            imagemSalva.getId(), 
                            imagemSalva.getNome(), 
                            imagemSalva.getTipo(),
                            "/colchoes/" + colchaoId + "/imagens/" + imagemSalva.getId()
                    ));
                    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao fazer upload da imagem: " + e.getMessage());
        }
    }
    
    // Listar todas as imagens de um colchão
    @GetMapping("/{colchaoId}/imagens")
    public ResponseEntity<List<ImagemResponseDTO>> getImagensByColchaoId(@PathVariable Long colchaoId) {
        List<ImagemColchao> imagens = imagemColchaoService.findByColchaoId(colchaoId);
        
        List<ImagemResponseDTO> responseImagens = imagens.stream()
                .map(imagem -> new ImagemResponseDTO(
                        imagem.getId(),
                        imagem.getNome(),
                        imagem.getTipo(),
                        "/colchoes/" + colchaoId + "/imagens/" + imagem.getId()
                ))
                .collect(Collectors.toList());
                
        return ResponseEntity.ok(responseImagens);
    }
    
    // Obter uma imagem específica
    @GetMapping("/{colchaoId}/imagens/{imagemId}")
    public ResponseEntity<byte[]> getImagem(@PathVariable Long colchaoId, @PathVariable Long imagemId) {
        Optional<ImagemColchao> imagemOpt = imagemColchaoService.findById(imagemId);
        
        if (imagemOpt.isPresent()) {
            ImagemColchao imagem = imagemOpt.get();
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(imagem.getTipo()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imagem.getNome() + "\"")
                    .body(imagem.getDados());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Excluir uma imagem
    @DeleteMapping("/{colchaoId}/imagens/{imagemId}")
    public ResponseEntity<?> deleteImagem(@PathVariable Long colchaoId, @PathVariable Long imagemId) {
        try {
            imagemColchaoService.deleteImagem(imagemId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao excluir imagem: " + e.getMessage());
        }
    }
    
    // DTO para retornar informações sobre imagem sem os bytes
    class ImagemResponseDTO {
        private Long id;
        private String nome;
        private String tipo;
        private String url;
        
        public ImagemResponseDTO(Long id, String nome, String tipo, String url) {
            this.id = id;
            this.nome = nome;
            this.tipo = tipo;
            this.url = url;
        }
        
        public Long getId() {
            return id;
        }
        
        public String getNome() {
            return nome;
        }
        
        public String getTipo() {
            return tipo;
        }
        
        public String getUrl() {
            return url;
        }
    }
}