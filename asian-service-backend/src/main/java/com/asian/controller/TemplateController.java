package com.asian.controller;

import com.asian.entity.Template;
import com.asian.entity.TemplateDownload;
import com.asian.repository.TemplateRepository;
import com.asian.repository.TemplateDownloadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/templates")
public class TemplateController {
    
    @Autowired
    private TemplateRepository templateRepository;
    
    @Autowired
    private TemplateDownloadRepository downloadRepository;
    
    @GetMapping
    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Template getTemplate(@PathVariable Long id) {
        return templateRepository.findById(id).orElse(null);
    }
    
    @PostMapping
    public Template createTemplate(@RequestBody Template template) {
        template.setDownloadCount(0);
        template.setCreatedAt(LocalDateTime.now());
        return templateRepository.save(template);
    }
    
    @PutMapping("/{id}")
    public Template updateTemplate(@PathVariable Long id, @RequestBody Template template) {
        template.setId(id);
        return templateRepository.save(template);
    }
    
    @DeleteMapping("/{id}")
    public void deleteTemplate(@PathVariable Long id) {
        templateRepository.deleteById(id);
    }
    
    @PostMapping("/{id}/download")
    public Map<String, Object> downloadTemplate(@PathVariable Long id, @RequestBody Map<String, String> data) {
        // Increment download count
        Template template = templateRepository.findById(id).orElse(null);
        if (template != null) {
            template.setDownloadCount(template.getDownloadCount() + 1);
            templateRepository.save(template);
            
            // Record download info
            TemplateDownload download = new TemplateDownload();
            download.setTemplateId(id);
            download.setName(data.get("name"));
            download.setPhone(data.get("phone"));
            download.setWechat(data.get("wechat"));
            download.setFilePurpose(data.get("purpose"));
            download.setDownloadedAt(LocalDateTime.now());
            downloadRepository.save(download);
            
            return Map.of("success", true, "fileUrl", template.getFileUrl());
        }
        return Map.of("success", false);
    }
    
    @GetMapping("/downloads")
    public List<TemplateDownload> getAllDownloads() {
        return downloadRepository.findAll();
    }
}
