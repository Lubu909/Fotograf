package pl.edu.ug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.edu.ug.model.Album;
import pl.edu.ug.model.Picture;
import pl.edu.ug.model.User;
import pl.edu.ug.service.AlbumService;
import pl.edu.ug.service.PictureService;
import pl.edu.ug.service.UserService;
import pl.edu.ug.validator.PictureValidator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
public class PictureController {

    private static final String MIME_IMAGE_JPEG        = "image/jpeg";
    private static final String MIME_IMAGE_PNG         = "image/png";

    @Autowired
    private PictureService pictureService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private UserService userService;

    @Autowired
    private PictureValidator pictureValidator;

    //Create Picture
    @RequestMapping(value = "/{username}/{albumID}/add", method = RequestMethod.GET)
    public String addPhoto(@PathVariable String username, @PathVariable Long albumID, Model model){
        model.addAttribute("albumID", albumID);
        model.addAttribute("photoForm", new Picture());
        return "Album/Photo/addPhoto";
    }

    @RequestMapping(value = "/{username}/{albumID}/add", method = RequestMethod.POST)
    public String addPhoto(@PathVariable String username,@PathVariable Long albumID,
                           @ModelAttribute("photoForm") Picture photoForm, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) throws IOException {
        pictureValidator.validate(photoForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "Album/Photo/addPhoto";
        }

        photoForm.setAlbum(albumService.get(albumID));
        System.out.println("Uploadowanie pliku " + photoForm.getTitle() + ", o rozmiarze " + photoForm.getPhotoFile().getSize());
        photoForm.setPhoto(savePhoto(photoForm.getPhotoFile(), albumID, photoForm.getTitle()));
        pictureService.add(photoForm);

        redirectAttributes.addAttribute("username", username).addAttribute("albumID", albumID);
        redirectAttributes.addFlashAttribute("success", "Successfully added photo to album");
        return "redirect:/{username}/{albumID}";
    }

    private String savePhoto(MultipartFile file, Long albumID, String nazwa) throws IOException {
        String ext;
        if(file.getContentType().equals(MIME_IMAGE_JPEG)) ext = ".jpg";
        else ext = ".png";
        File dir = new File("/uploads/" + albumID);
        dir.mkdirs();
        File out = new File(dir, nazwa + ext);
        String path = out.getAbsolutePath();
        System.out.println("Scieżka do pliku: " + path);
        file.transferTo(out);
        return path;
    }
    
    //Read Picture
    @RequestMapping(value = "/{username}/{albumID}/photo{pictureID}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getPhoto(@PathVariable String username, @PathVariable Long albumID, @PathVariable Long pictureID){
        ResponseEntity<byte[]> responseEntity = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        try {
            Picture pic = pictureService.get(pictureID);
            File file = new File(pic.getPhoto());

            responseEntity = new ResponseEntity<>(Files.readAllBytes(file.toPath()), headers, HttpStatus.OK);
        } catch (IOException e) {
            System.out.println("Nie udało się załadować zdjęcia o ID " + pictureID);
            responseEntity = new ResponseEntity<>(null, headers, HttpStatus.NO_CONTENT);
        }
        return responseEntity;
    }

    //Update Picture
    @RequestMapping(value = "/{username}/{albumID}/{pictureID}/edit", method = RequestMethod.GET)
    public String editPhoto(@PathVariable String username,@PathVariable Long albumID, @PathVariable Long pictureID,
                            Model model, RedirectAttributes redirectAttributes){
        User user = userService.findByUsername(username);
        if (user != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                if (auth.isAuthenticated()) {
                    if (auth.getName().equals(username)) {
                        Picture picture = pictureService.get(pictureID);
                        if (picture != null) {
                            if (picture.getAlbum().getId() == albumID) {
                                model.addAttribute("photoForm", picture);
                                return "Album/Photo/edit";
                            }
                        }
                    }
                }
            }
        }
        redirectAttributes.addFlashAttribute("error", "Cannot edit photo - not found");
        return "redirect:/";
    }

    @RequestMapping(value = "/{username}/{albumID}/{pictureID}/edit", method = RequestMethod.POST)
    public String editPhoto(@PathVariable String username,@PathVariable Long albumID, @PathVariable Long pictureID,
                            @ModelAttribute("photoForm") Picture photoForm, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes){
        User user = userService.findByUsername(username);
        if (user != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                if (auth.isAuthenticated()) {
                    if (auth.getName().equals(username)) {
                        pictureValidator.validateEdit(photoForm, bindingResult);

                        if (bindingResult.hasErrors()) {
                            return "Album/Photo/edit";
                        }

                        Picture picture = pictureService.get(pictureID);
                        picture.setTitle(photoForm.getTitle());
                        pictureService.add(picture);

                        redirectAttributes.addAttribute("username", username).addAttribute("albumID", albumID);
                        redirectAttributes.addFlashAttribute("success", "Successfully edited photo");
                        return "redirect:/{username}/{albumID}";
                    }
                }
            }
        }
        redirectAttributes.addFlashAttribute("error", "Cannot edit photo - not found");
        return "redirect:/";
    }

    //Delete Picture
    @RequestMapping(value = "/{username}/{albumID}/{pictureID}/delete", method = RequestMethod.POST)
    public String deletePhoto(@PathVariable String username,@PathVariable Long albumID, @PathVariable Long pictureID,
                              RedirectAttributes redirectAttributes) {
        User user = userService.findByUsername(username);
        if (user != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                if (auth.isAuthenticated()) {
                    if (auth.getName().equals(username)) {
                        //delete file
                        Picture pic = pictureService.get(pictureID);
                        File file = new File(pic.getPhoto());
                        if (file.exists()) file.delete();
                        //delete db record
                        pictureService.delete(pic);

                        redirectAttributes.addAttribute("username", username).addAttribute("albumID", albumID);
                        redirectAttributes.addFlashAttribute("success", "Successfully removed photo");
                        return "redirect:/{username}/{albumID}";
                    }
                }
            }
        }
        redirectAttributes.addFlashAttribute("error", "Cannot delete photo");
        return "redirect:/";
    }
}
