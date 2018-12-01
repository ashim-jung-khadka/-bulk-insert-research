package com.ashim.fxdeals.controller;

import com.ashim.fxdeals.exception.StorageFileNotFoundException;
import com.ashim.fxdeals.service.DealCountService;
import com.ashim.fxdeals.service.FxDealService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

/**
 * @author ashimjk on 12/1/2018
 */
@Controller
public class DealController {

    private static final Logger logger = LoggerFactory.getLogger(DealController.class);

    @Autowired private DealCountService dealCountService;
    @Autowired private FxDealService fxDealService;

    @GetMapping("/")
    public String listUploadedFiles(Model model) {

        model.addAttribute("dealCounts", this.dealCountService.getAllDealCount());
        model.addAttribute("invalidDealCounts", this.fxDealService.getTotalInvalidDealCount());
        return "index";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = this.fxDealService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes rdAttr) {
        try {
            boolean status = this.fxDealService.saveDeal(file);

            if (status) {
                rdAttr.addFlashAttribute("message", "Successfully uploaded " + file.getOriginalFilename() + "!");
            } else {
                rdAttr.addFlashAttribute("message", "Already uploaded " + file.getOriginalFilename() + "!");
            }

        } catch (IOException ex) {
            logger.error("Error while uploading file : {}", ex);
            rdAttr.addFlashAttribute("message", "Failed to upload " + file.getOriginalFilename() + "!");
        }

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}