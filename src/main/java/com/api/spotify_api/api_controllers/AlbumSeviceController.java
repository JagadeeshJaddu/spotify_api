package com.api.spotify_api.api_controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.spotify.playlistmanager.dtos.AddAlbumRequestDTO;
import com.spotify.playlistmanager.dtos.AddSongToAlbumRequestDTO;
import com.spotify.playlistmanager.dtos.EntityType;
import com.spotify.playlistmanager.dtos.ResponseDTO;
import com.spotify.playlistmanager.dtos.ResponseType;
import com.spotify.playlistmanager.models.Album;
import com.spotify.playlistmanager.services.AlbumService;

@RestController
public class AlbumSeviceController {
    private AlbumService albumService;
    
    public AlbumSeviceController(AlbumService albumService)
    {
        this.albumService = albumService;
    }

    @RequestMapping(value = "/spotify/album" , method = RequestMethod.POST)
    public ResponseEntity<Object> addAlbum(@RequestBody AddAlbumRequestDTO addAlbumRequestDTO)
    {
        Album album;
        String name = addAlbumRequestDTO.getName();
        Long artistId = addAlbumRequestDTO.getArtistId();
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setEntityType(EntityType.Album);
        responseDTO.setResponseType(ResponseType.Addition);
        try{
            album = albumService.addAlbum(name, artistId);
            responseDTO.setEntityId(album.getId());
            responseDTO.setStatus("SUCCESS");
        }
        catch(Exception e){
            responseDTO.setStatus("FAILURE");
            responseDTO.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(responseDTO,HttpStatus.CREATED);
    }

    @RequestMapping(value = "/spotify/album/song" , method = RequestMethod.POST)
    public ResponseEntity<Object> addSongToAlbum(@RequestBody AddSongToAlbumRequestDTO addSongToAlbumRequestDTO)
    {
        Album album;
        Long albumId = addSongToAlbumRequestDTO.getAlbumId();
        Long songId = addSongToAlbumRequestDTO.getSongId();
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setEntityType(EntityType.Album);
        responseDTO.setResponseType(ResponseType.Addition);
        try{
            album = albumService.addSongToAlbum(albumId, songId);
            responseDTO.setEntityId(album.getId());
            responseDTO.setStatus("SUCCESS");
        }
        catch(Exception e){
            responseDTO.setStatus("FAILURE");
            responseDTO.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }
}
