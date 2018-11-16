package com.ctbc.skatingfont.api;


import com.ctbc.skatingfont.dto.Linker;
import com.ctbc.skatingfont.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by JieChen on 2018/11/14.
 */
@Controller
public class FileUploadController {
    private final StorageService storageService;
    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }
    @GetMapping("/FileUpload")
    public String listUploadedFiles(Model model)throws IOException {
        List<Linker> linkers = storageService.loadAll().map(
                path -> new Linker(MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString(),
                        path.getFileName().toString())
        ).collect(Collectors.toList());
        model.addAttribute("linkers", linkers);
        return "uploadForm";
    }
    @GetMapping("/files/{filename:. }")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\""+   file.getFilename()   +"\"").body(file);
    }
    @PostMapping("/FileUpload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded "+   file.getOriginalFilename()   +"!");
        return "redirect:/";
    }

}
