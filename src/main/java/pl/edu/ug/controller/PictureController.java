package pl.edu.ug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.edu.ug.model.Picture;
import pl.edu.ug.model.User;
import pl.edu.ug.service.AlbumService;
import pl.edu.ug.service.PictureService;
import pl.edu.ug.service.UserService;
import pl.edu.ug.validator.PictureValidator;

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

    @Autowired
    private MessageSource messageSource;

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

        String successMsg = messageSource.getMessage("messages.picture.create.success",null, LocaleContextHolder.getLocale());
        redirectAttributes.addAttribute("username", username).addAttribute("albumID", albumID);
        redirectAttributes.addFlashAttribute("success", successMsg);
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
        String errorMsg = messageSource.getMessage("messages.picture.notFound",null, LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("error", errorMsg);
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

                        String successMsg = messageSource.getMessage("messages.picture.edit.success",null, LocaleContextHolder.getLocale());
                        redirectAttributes.addAttribute("username", username).addAttribute("albumID", albumID);
                        redirectAttributes.addFlashAttribute("success", successMsg);
                        return "redirect:/{username}/{albumID}";
                    }
                }
            }
        }
        String errorMsg = messageSource.getMessage("messages.picture.notFound",null, LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("error", errorMsg);
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

                        String successMsg = messageSource.getMessage("messages.picture.delete.success",null, LocaleContextHolder.getLocale());
                        redirectAttributes.addAttribute("username", username).addAttribute("albumID", albumID);
                        redirectAttributes.addFlashAttribute("success", successMsg);
                        return "redirect:/{username}/{albumID}";
                    }
                }
            }
        }
        String errorMsg = messageSource.getMessage("messages.picture.notFound",null, LocaleContextHolder.getLocale());
        redirectAttributes.addFlashAttribute("error", errorMsg);
        return "redirect:/";
    }
}
