package no.kristianped.recipe.controllers;

import no.kristianped.recipe.commands.RecipeCommand;
import no.kristianped.recipe.services.ImageService;
import no.kristianped.recipe.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;

    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("/recipe/{recipeId}/image")
    public String getForm(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findByCommandById(Long.valueOf(recipeId)));

        return "recipe/imageuploadform";
    }

    @PostMapping("recipe/{id}/image")
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile")MultipartFile file) {
        imageService.saveImageFile(Long.valueOf(id), file);

        return "redirect:/recipe/" + id + "/show";
    }

    @GetMapping("/recipe/{id}/recipeimage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        if (id == null)
            return;

        RecipeCommand recipeCommand = recipeService.findByCommandById(Long.valueOf(id));
        byte[] bytes = new byte[recipeCommand.getImage().length];
        int i = 0;

        for (Byte wrappedByte : recipeCommand.getImage())
            bytes[i++] = wrappedByte;

        response.setContentType("image/jpeg");
        InputStream is = new ByteArrayInputStream(bytes);
        IOUtils.copy(is, response.getOutputStream());
    }
}
