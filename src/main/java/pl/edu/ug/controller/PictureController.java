package pl.edu.ug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.ug.model.Album;
import pl.edu.ug.model.Picture;
import pl.edu.ug.service.AlbumService;
import pl.edu.ug.service.PictureService;
import pl.edu.ug.service.UserService;
import pl.edu.ug.validator.PictureValidator;

import java.io.File;
import java.io.IOException;

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
                           @ModelAttribute("photoForm") Picture photoForm, BindingResult bindingResult) throws IOException {
        pictureValidator.validate(photoForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "Album/Photo/addPhoto";
        }

        photoForm.setAlbum(albumService.get(albumID));
        System.out.println("Uploadowanie pliku " + photoForm.getTitle() + ", o rozmiarze " + photoForm.getPhotoFile().getSize());
        photoForm.setPhoto(savePhoto(photoForm.getPhotoFile(), albumID, photoForm.getTitle()));
        pictureService.add(photoForm);

        return "redirect:/" + username + "/" + albumID;
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
    public String getPhoto(){
        return "Album/Photo/view";
    }

    //Update Picture
    public String editPhoto(){
        //change name
        return "Album/Photo/view";
    }
    //Delete Picture
    public String deletePhoto(){
        //delete file
        //delete db record
        return "placeholder";
        //return "redirect:/" + username + "/" + albumID;
    }
}
