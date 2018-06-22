package noname.service;

import noname.model.Album;

import java.util.List;

public interface AlbumClient {
    List<Album> getAlbums();
    Album getAlbumById();
}
