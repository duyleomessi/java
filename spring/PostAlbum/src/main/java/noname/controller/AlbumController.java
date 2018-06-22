package noname.controller;

import noname.model.Album;
import noname.service.AlbumClientImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlbumController {
    @Autowired
    private AlbumClientImpl albumClient = new AlbumClientImpl();

    @GetMapping("/albums")
    public List<Album> getAlbums() {
        return albumClient.getAlbums();
    }

}
